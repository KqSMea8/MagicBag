package org.github.helixcs.ktor

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.response.readText
import io.ktor.content.OutgoingContent
import io.ktor.content.TextContent
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.uri
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.filter
import kotlinx.coroutines.experimental.io.ByteWriteChannel
import kotlinx.coroutines.experimental.io.copyAndClose
import org.slf4j.LoggerFactory

/**
 * Main entry point of the application. This application starts a webserver at port 8080 based on Netty.
 * It intercepts all the requests, reverse-proxying them to the wikipedia.
 *
 * In the case of HTML it is completely loaded in memory and preprocessed to change URLs to our own local domain.
 * In the case of other files, the file is streamed from the HTTP client to the HTTP server response.
 */
val LOGGER = LoggerFactory.getLogger("Proxy")

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)
    install(ForwardedHeaderSupport)
    // enable HSTS
    install(HSTS) {
        includeSubDomains = true
    }

    // Creates a new HttpClient
    val client = HttpClient()
    val wikipediaLang = "en"

    // Let's intercept all the requests at the [ApplicationCallPipeline.Call] phase.
    intercept(ApplicationCallPipeline.Call) {
        // We create a GET request to the wikipedia domain and return the call (with the request and the unprocessed response).
        val uri = call.request.uri
        println("==> $uri")
        //val result = client.call("https://$wikipediaLang.wikipedia.org$uri")
        val result = client.call("https://google.com$uri")

        // Get the relevant headers of the client response.
        val proxiedHeaders = result.response.headers
        val location = proxiedHeaders[HttpHeaders.Location]
        val contentType = proxiedHeaders[HttpHeaders.ContentType]
        val contentLength = proxiedHeaders[HttpHeaders.ContentLength]

        // Extension method to process all the served HTML documents
        fun String.stripWikipediaDomain() = this.replace(Regex("(https?:)?//\\w+\\.wikipedia\\.org"), "")

        // Propagates location header, removing the wikipedia domain from it
        if (location != null) {
            call.response.header(HttpHeaders.Location, location.stripWikipediaDomain())
        }

        // Depending on the ContentType, we process the request one way or another.
        when {
        // In the case of HTML we download the whole content and process it as a string replacing
        // wikipedia links.
            contentType?.startsWith("text/html") == true -> {
                val text = result.response.readText()
                val filteredText = text.stripWikipediaDomain()
//                    LOGGER.info("==> $filteredText")
                call.respond(
                        TextContent(
                                filteredText,
                                ContentType.Text.Html.withCharset(Charsets.UTF_8),
                                result.response.status
                        )
                )
            }
            else -> {
                // In the case of other content, we simply pipe it. We return a [OutgoingContent.WriteChannelContent]
                // propagating the contentLength, the contentType and other headers, and simply we copy
                // the ByteReadChannel from the HTTP client response, to the HTTP server ByteWriteChannel response.
                call.respond(object : OutgoingContent.WriteChannelContent() {
                    override val contentLength: Long? = contentLength?.toLong()
                    override val contentType: ContentType? = contentType?.let { ContentType.parse(it) }
                    override val headers: Headers = Headers.build {
                        appendAll(proxiedHeaders.filter { key, _ -> !key.equals(HttpHeaders.ContentType, ignoreCase = true) && !key.equals(HttpHeaders.ContentLength, ignoreCase = true) })
                    }
                    override val status: HttpStatusCode? = result.response.status
                    override suspend fun writeTo(channel: ByteWriteChannel) {
                        result.response.content.copyAndClose(channel)
                    }
                })
            }
        }
    }

}

fun main(args: Array<String>) {
    val port:Int = if(System.getenv("PORT").isNullOrBlank()) 8085 else Integer.valueOf(System.getenv("PORT"))
    // Creates a Netty server
    val server = embeddedServer(Netty, port = 8080, watchPaths = listOf("ReverseProxyApplication"),module = Application::module).start()
}
