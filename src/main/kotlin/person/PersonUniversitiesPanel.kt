package person

import kotlinext.js.getOwnPropertyNames
import kotlinext.js.jso
import react.FC
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.dom.Link
import school.SchoolPageLocationState
import sparql.GetPersonUniversitiesResponse
import sparql.SparqlQueryArrayConsumerProps

external interface PersonUniversitiesPanelProps : SparqlQueryArrayConsumerProps<GetPersonUniversitiesResponse> {
    var personUri: String
}

val personUniversitiesPanel = FC<PersonUniversitiesPanelProps> { props ->
    ul {
        if (props.queryResult.any { it.getOwnPropertyNames().isNotEmpty() }) {
            props.queryResult.distinctBy { it.universities?.value }.forEach {
                li {
                    if (it.ge?.value?.contains("Grande_école") == true) {
                        Link {
                            this.to = "/school"
                            this.state = jso<SchoolPageLocationState> { schoolUri = it.resource?.value!! }
                            +(it.universities?.value ?: "")
                        }
                    } else +(it.universities?.value ?: "")
                }
            }
        } else {
            +"Aucun parcours universitaire connu"
        }
    }
}