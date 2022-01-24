package city

import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import school.personPanel
import sparql.getCityBirthPlacePersonsInfo
import sparql.getCityDeathPlacePersonsInfo
import sparql.sparqlQueryLoaderMultiple

external interface CityBirthDeathPlacePersonsPanelProps : Props {
    var cityUri: String
}

val cityBirthDeathPlacePersonsPanel = FC<CityBirthDeathPlacePersonsPanelProps> { props ->
    div {
        className = "birth-death-related-person-panels"
        sparqlQueryLoaderMultiple(getCityBirthPlacePersonsInfo, jso { uri = props.cityUri }) {
            personPanel {
                this.title = "Ils sont nés dans cette ville :"
            }
        }
        sparqlQueryLoaderMultiple(getCityDeathPlacePersonsInfo, jso { uri = props.cityUri }) {
            personPanel {
                this.title = "Ils sont morts dans cette ville :"
            }
        }
    }
}