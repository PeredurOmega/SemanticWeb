package cityPage

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.router.Navigate
import react.router.useLocation
import search.mapCoordinatesContextProvider
import search.mapResult
import tools.requireSCSS
import tools.sparql.*

external interface CityPageLocationState : State {
    var cityUri : String?
}

val cityPage = FC<Props> {
    requireSCSS("city-page")

    val location = useLocation()
    val cityUri = location.state.unsafeCast<CityPageLocationState?>()?.cityUri ?: return@FC Navigate {
        to = "/"
        replace = true
    }
    mapCoordinatesContextProvider {
        div {
            className = "city-container"
            div {
                className = "left-panel"
                sparqlQueryLoaderSingle(getCityInfo, jso { uri = cityUri }, true) {
                    cityResult {
                        this.cityUri = cityUri
                    }
                }
                hr { }
                cityBirthDeathPlacePersonsPanel {
                    this.cityUri = cityUri
                }
            }
            div {
                className = "right-panel"
                div {
                    className = "media-container"
                    sparqlQueryLoaderSingle(getCityImage, jso { uri = cityUri }){
                        cityImagePanel{
                            this.cityUri = cityUri
                        }
                    }
                    mapResult {
                        expectedCount = 1
                    }
                }
            }
        }
    }
}
