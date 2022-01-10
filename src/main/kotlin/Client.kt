import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.FC
import react.Fragment
import react.Props
import react.create

fun main() {
    window.onload = {
        render(Fragment.create { mainApp {} }, document.getElementById("root")!!)
    }
}

val mainApp = FC<Props> {
    helloWorld {
        name = "Everyone"
    }
}