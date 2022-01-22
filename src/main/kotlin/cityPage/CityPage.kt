package cityPage

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.useLocation
import tools.requireSCSS
import tools.sparql.*

external interface CityPageLocationState : State {
    var cityName : String?
}

val cityPage = FC<Props> {
    requireSCSS("city-page")

    val location = useLocation()
    val cityName = "http://dbpedia.org/resource/Villeurbanne";
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
                    uri = cityName
                }, true) {
                cityResult {
                }
            }
        }
    }
}
