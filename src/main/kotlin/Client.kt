import kotlinx.browser.document
import kotlinx.browser.window
import react.*
import react.dom.render
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
    BrowserRouter {
        Routes {
            Route {
                path = "/"
                element = mainPage.create()
            }
            Route {
                path = "*"
                element = app.create()
            }

        }
    }
}

val app = FC<Props> {
    navBar {}
    Routes {
        Route{
            path = "person"
            element = personPage.create()
        }
    }
}

val mainApp = FC<Props> {
    requireSCSS("app")
    mainRouter {

    }
}