package cityPage

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.router.useLocation
import tools.requireSCSS
import tools.sparql.*

external interface CityPageLocationState : State {
    var cityUri : String?
}

val cityPage = FC<Props> {
    requireSCSS("city-page")

    val location = useLocation()
    val cityUri = "http://dbpedia.org/resource/Villeurbanne";
  //  location.state.unsafeCast<CityPageLocationState?>()?.cityName ?: return@FC Navigate {
    //    to = "/"
      //  replace = true
    //}

    div {
        className = ""
        div {
            className = "city-results"
            sparqlQueryLoaderSingle(
                getCityInfo,
                jso {
                    uri = cityUri
                }, true) {
                cityResult {
                }
            }
        }
        cityBirthDeathPlacePersonsPanel {
            this.cityUri = cityUri
        }
    }
}
