package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import tools.sparql.GetPersonGeneralInfoResponse
import tools.sparql.GetPersonInfoResponse
import tools.sparql.SparqlQueryArrayConsumerProps

external interface PersonPanelProps : SparqlQueryArrayConsumerProps<GetPersonGeneralInfoResponse> {
    var title : String
}

val personPanel = FC<PersonPanelProps> { props ->
    div {
        span {
            +props.title
        }

        props.queryResult.distinctBy{it.person.value}.take(5).forEach {
            SmallPersonCard {
               this.personInfo = it
            }
        }
    }
}