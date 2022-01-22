import cityPage.cityPage
import kotlinx.browser.document
import kotlinx.browser.window
import person.personPage
import react.FC
import react.Fragment
import react.Props
import react.create
import react.dom.render
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import schoolPage.schoolPage
import search.searchPage
import tools.progressBarContextProvider
import tools.requireSCSS

fun main() {
    window.onload = {
        render(Fragment.create { mainApp {} }, document.getElementById("root")!!)
    }
}

val mainApp = FC<Props> {
    requireSCSS("app")
    mainRouter { }
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
    progressBarContextProvider {
        Routes {
            Route {
                path = "person"
                element = personPage.create()
            }
            Route {
                path = "school"
                element = schoolPage.create()
            }
            Route {
                path = "search"
                element = searchPage.create()
            }
            Route {
                path = "city"
                element = cityPage.create()
            }
        }
    }
}