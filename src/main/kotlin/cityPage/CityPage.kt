package cityPage

import kotlinext.js.jso
import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.router.useLocation
import tools.redirectToHome
import tools.requireSCSS
import tools.sparql.getCityImage
import tools.sparql.getCityInfo
import tools.sparql.sparqlQueryLoaderSingle

external interface CityPageLocationState : State {
    var cityUri: String?
}

val cityPage = FC<Props> {
    requireSCSS("city-page")

    val location = useLocation()
    val cityUri = location.state.unsafeCast<CityPageLocationState?>()?.cityUri ?: return@FC redirectToHome()

    div {
        className = ""
        div {
            className = "city-results"
            sparqlQueryLoaderSingle(getCityInfo, jso { uri = cityUri }, true) {
                cityResult {
                    this.cityUri = cityUri
                }
            }
        }
        sparqlQueryLoaderSingle(getCityImage, jso { uri = cityUri }) {
            cityImagePanel {
                this.cityUri = cityUri
            }
        }

        cityBirthDeathPlacePersonsPanel {
            this.cityUri = cityUri
        }
    }
}
