package person

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import tools.sparql.GetPersonImageResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.sparql.placeholder
import tools.sparql.whenNotBlank
import tools.wikipediaPhoto

external interface PersonImagePanelProps : SparqlQueryConsumerProps<GetPersonImageResponse> {
    var personUri: String
}

val personImagePanel = FC<PersonImagePanelProps> { props ->
    div {
        className = "img-person"
        props.queryResult.imageURL.whenNotBlank {
            img {
                src = props.queryResult.imageURL!!.value
            }
        } placeholder {
            wikipediaPhoto {
                this.uri = props.personUri
                this.type = "person"
            }
        }
    }
}