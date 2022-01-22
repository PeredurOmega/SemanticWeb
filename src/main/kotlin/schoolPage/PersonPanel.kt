package schoolPage

import kotlinx.browser.document
import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.useEffectOnce
import tools.sparql.GetPersonGeneralInfoResponse
import tools.sparql.SparqlQueryArrayConsumerProps

external interface PersonPanelProps : SparqlQueryArrayConsumerProps<GetPersonGeneralInfoResponse> {
    var title: String
}

val personPanel = FC<PersonPanelProps> { props ->
    useEffectOnce {
        if (props.queryResult.isNotEmpty()) document.getElementById("special-hr")?.className += " with-panel2"
    }
    if (props.queryResult.isNotEmpty()) {
        div {
            className = "related-person-panel"
            span {
                +props.title
            }
            div {
                className = "person-panel"
                props.queryResult.distinctBy { it.person.value }.take(5).forEach {
                    SmallPersonCard {
                        this.personInfo = it
                    }
                }
            }
        }
    }
}