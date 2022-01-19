package schoolPage

import kotlinext.js.jso
import react.FC
import react.Props
import tools.sparql.*

external interface RelatedPersonPanelProps : Props {
    var schoolUri : String
}

val relatedPersonPanel = FC<RelatedPersonPanelProps> { props ->
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