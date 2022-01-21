package person

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import schoolPage.wikipediaPhoto
import tools.sparql.GetPersonImageResponse
import tools.sparql.SparqlQueryConsumerProps

external interface PersonImagePanelProps : SparqlQueryConsumerProps<GetPersonImageResponse> {
    var personUri: String
}

val personImagePanel = FC<PersonImagePanelProps> { props ->
    div {
        className = "img-person"
        if (!props.queryResult.imageURL?.value.isNullOrBlank()) {
            img {
                src =  props.queryResult.imageURL!!.value
            }
        } else wikipediaPhoto {
            this.personUri = props.personUri
        }
    }
}