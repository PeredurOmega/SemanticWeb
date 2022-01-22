package tools

import react.ChildrenBuilder
import react.router.Navigate

fun requireSCSS(fileName: String) {
    kotlinext.js.require("css/${fileName}.scss")
}

fun ChildrenBuilder.redirectToHome() {
    Navigate {
        to = "/"
        replace = true
    }
}