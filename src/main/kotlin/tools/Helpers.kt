package tools

fun requireSCSS(fileName: String) {
    kotlinext.js.require("css/${fileName}.scss")
}