package locationDetails

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import tools.sparql.GetLocationDetailsResponse
import tools.sparql.SparqlQueryConsumerProps

external interface LocationDetailsResultProps : SparqlQueryConsumerProps<GetLocationDetailsResponse>

val locationDetailsResult = FC<LocationDetailsResultProps> { props ->
    val searchResult = props.queryResult
    div {
        className = "card-result"
        div {
            div {
                div {
                    if (! searchResult.name?.value.isNullOrBlank() && ! searchResult.country?.value.isNullOrBlank()) {
                        +"${searchResult.name!!.value}, ${searchResult.country!!.value}"
                    }
                }
                div {
                    if (! searchResult.communeStatusLabel?.value.isNullOrBlank()) {
                        +searchResult.communeStatusLabel!!.value
                    }
                }
                div {
                    if (! searchResult.abstract?.value.isNullOrBlank()) {
                        +searchResult.abstract!!.value
                    }
                    img {
                        src =
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Place_lazare_goujon.jpg/300px-Place_lazare_goujon.jpg"
                    }
                }
            }
        }
    }
}