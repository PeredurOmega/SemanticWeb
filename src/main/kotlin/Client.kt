import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.FC
import react.Fragment
import react.Props
import react.create
import tools.requireSCSS

fun main() {
    println("main")
    window.onload = {
        render(Fragment.create { mainApp {} }, document.getElementById("root")!!)
    }
}

val mainApp = FC<Props> {
//    helloWorld {
//        name = "Everyone"
//    }
    requireSCSS("app")
    mainPage {

    }
}