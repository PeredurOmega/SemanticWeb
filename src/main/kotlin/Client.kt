import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.FC
import react.Fragment
import react.Props
import react.create
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import tools.requireSCSS

fun main() {
    println("main")
    window.onload = {
        render(Fragment.create { mainApp {} }, document.getElementById("root")!!)
    }
}

val mainRouter = FC<Props> {
    BrowserRouter{
        Routes{
            Route{
                path = "/"
                element = mainPage.create()
                Route{
                    path = "person"
                    element = personPage.create()
                }
            }
        }
    }
}

val mainApp = FC<Props> {
//    helloWorld {
//        name = "Everyone"
//    }
    requireSCSS("app")
    mainRouter {

    }
}