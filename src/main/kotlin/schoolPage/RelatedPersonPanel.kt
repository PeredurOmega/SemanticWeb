package schoolPage

import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import tools.sparql.getPersonAlmaMaterInfo
import tools.sparql.getPersonEducationalInfo
import tools.sparql.sparqlQueryLoaderMultiple

external interface RelatedPersonPanelProps : Props {
    var schoolUri: String
}

val relatedPersonPanel = FC<RelatedPersonPanelProps> { props ->
    div {
        className = "related-person-panels"
        sparqlQueryLoaderMultiple(getPersonAlmaMaterInfo, jso { uri = props.schoolUri }) {
            personPanel {
                this.title = "Ils en sont diplômés :"
            }
        }
        sparqlQueryLoaderMultiple(getPersonEducationalInfo, jso { uri = props.schoolUri }) {
            personPanel {
                this.title = "Ils enseignent dans cette école :"
            }
        }
    }
}