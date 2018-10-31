/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.java.ktor

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.queryString
import io.ktor.request.uri
import io.ktor.request.userAgent
import io.ktor.response.header
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

/**
 * @Author: Helixcs
 * @Time:9/2/18
 * @sample :https://ktor.io/quickstart/index.html
 */

fun Application.mymodule() {
    install(DefaultHeaders)
    install(CallLogging)
    // gson
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }
    install(Routing) {
        get("/sin") {
            val uri = call.request.uri
            val queryParameters = call.request.queryParameters.entries()
            val queryString: String = call.request.queryString()
            val userAgent = call.request.userAgent()
            val cookies = call.request.cookies.rawCookies.entries
            var cookiesString = ""
            for ( cookie in cookies){
                cookiesString +="${cookie.key}=${cookie.value}&"
            }

//            call.request.
            call.respondText("SIN <br> uri:$uri " +
                    "<br> queryString : $queryString " +
                    "<br> userAgent: $userAgent" +
                    " <br> cookies: $cookiesString", ContentType.Text.Html)
        }

        // response samples
        get("/cos") {
            val response = call.response
            var content:String = ""
            // set cookies
            response.header("Set-Cookie","_custom_cookie=this cookie is from ktor")
            // custom headers
            response.header("X-Powered","helixcs")
            response.header("X-Powered","ktor")
            //send application.json
//            call.respondText("COS", ContentType.Application.Json)

            // send files
//            call.respondFile(File("C:\\Users\\wb-zj268791\\Downloads\\Go for Python Hackers - Greg Ward.mp4"))
        }
    }

////    routing {
////        get("/sin") {
////            call.respondText("Ok")
////        }
//    }

    intercept(ApplicationCallPipeline.Call) {
        if (call.request.uri == "/") {
            call.respondText("Test String")
        }
    }
}


fun main(args: Array<String>) {
    embeddedServer(Netty, 8085, watchPaths = listOf("HelloWorld"), module = Application::mymodule).start()
}

//fun main(args: Array<String>) {
//    var server = embeddedServer(Netty, port = 8085) {
//        routing {
//            get("/") {
//                call.respondText("Hello World!", ContentType.Text.Html);
//            }
//        }
//    }
//
//    server.start(wait = true)
//}