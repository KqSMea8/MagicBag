package org.github.helixcs.kotlin

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.InputStream
import java.nio.file.FileSystems


// reference at :https://grokonez.com/kotlin-tutorial

const val readFilePath = "D:\\open_code\\magicbag\\api-tester\\src\\main\\resources\\kt_file.txt"
const val writeFilePath = "D:\\open_code\\magicbag\\api-tester\\src\\main\\resources\\kt_file_w.txt"

fun readFile() {

    // read all lines
    val inputStream: InputStream = File(readFilePath).inputStream()
    val allLinesString = inputStream.bufferedReader().use { it.readText() }
    println("all lines is > $allLinesString")


    // read by line
    val inputStream2: InputStream = File(readFilePath).inputStream()
    val lineList = mutableListOf<String>()
    inputStream2.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }
    lineList.forEach { println("> $it") }

}

fun writeFile() {
    // printWriter
    val outString = "kotlin write words"
    File(writeFilePath).printWriter().use { out -> out.println(outString) }.also { println("file write finished.") }

    //bufferedWriter
    File(writeFilePath).bufferedWriter().use { out -> out.write("kotlin write words by bufferedWrite") }.also { println("file write finished by bufferedWrite.") }

    // extension functions
    val file = File(writeFilePath)
    file.writeText("\nkotlin write words by extension")
    file.appendText("\nappend words1")
    file.appendText("\nappend words2").also { println("file write extension finished.") }

}


fun zxingFile() {
    val qrCodeWriter = QRCodeWriter()
    val bitMatrix = qrCodeWriter.encode(
            "Kotlin zxing",
            BarcodeFormat.QR_CODE,
            600, 600
    )
    val path = FileSystems.getDefault().getPath("api-tester\\src\\main\\resources\\qrcode.png")
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
}

fun main(args: Array<String>) {

    readFile()
    writeFile()
    zxingFile()
}