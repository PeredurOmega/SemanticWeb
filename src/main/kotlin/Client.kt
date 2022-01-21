import kotlinx.browser.document
import kotlinx.browser.window
import locationPage.locationDetailsPage
import person.personPage
import react.*
import react.dom.render
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import schoolPage.schoolPage
import search.searchPage
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
        Route{
            path = "school"
            element = schoolPage.create()
        }
        Route{
            path = "search"
            element = searchPage.create()
        }
        Route{
            path = "city"
            element = locationDetailsPage.create()
        }
    }
}

val mainApp = FC<Props> {
    requireSCSS("app")
    mainRouter {

    }
}