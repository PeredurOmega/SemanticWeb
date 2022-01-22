package person

import kotlinext.js.jso
import react.FC
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.dom.Link
import schoolPage.SchoolPageLocationState
import tools.sparql.GetPersonUniversitiesResponse
import tools.sparql.SparqlQueryArrayConsumerProps

external interface PersonUniversitiesPanelProps : SparqlQueryArrayConsumerProps<GetPersonUniversitiesResponse>
{
    var personUri: String
}

val personUniversitiesPanel = FC<PersonUniversitiesPanelProps> { props ->
    ul {
        if (props.queryResult.isNotEmpty()) {
            props.queryResult.distinctBy { it.universities?.value }.forEach {
                li {
                    if(it.ge?.value?.contains("Grande_école") == true)
                    {
                        Link{
                            this.to = "/school"
                            this.state = jso<SchoolPageLocationState> { schoolUri = it.resource?.value!! }
                            +(it.universities?.value?:"")
                        }
                    }
                    else +(it.universities?.value?:"")
                }
            }
        } else {
            +"Aucun parcours universitaire connu"
        }
    }
}