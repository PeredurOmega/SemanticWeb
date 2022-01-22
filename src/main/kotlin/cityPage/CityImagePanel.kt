package cityPage

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import sparql.GetPersonImageResponse
import sparql.SparqlQueryConsumerProps
import tools.wikipediaPhoto

external interface CityImagePanelProps : SparqlQueryConsumerProps<GetPersonImageResponse> {
    var cityUri: String
}

val cityImagePanel = FC<CityImagePanelProps> { props ->
    div {
        className = "city-img"
        if (!props.queryResult.imageURL?.value.isNullOrBlank()) {
            img {
                src = props.queryResult.imageURL!!.value
            }
        } else wikipediaPhoto {
            this.uri = props.cityUri
            this.type = "city"
        }
    }
}
