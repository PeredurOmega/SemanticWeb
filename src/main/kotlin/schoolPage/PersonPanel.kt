package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import tools.sparql.GetPersonGeneralInfoResponse

external interface PersonPanelProps : Props {
    var personGeneralInfo : Array<GetPersonGeneralInfoResponse>
    var title : String
}

val personPanel = FC<PersonPanelProps> { props ->
    div {
        span {
            +props.title
        }
        repeat(5) {
            SmallPersonCard {

            }
        }
    }
}