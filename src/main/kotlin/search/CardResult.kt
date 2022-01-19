package search

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useContext
import react.useEffectOnce
import tools.sparql.GetSearchResultResponse
import tools.sparql.SparqlQueryConsumerProps

external interface CardResultProps : SparqlQueryConsumerProps<GetSearchResultResponse>

val cardResult = FC<CardResultProps> { props ->
    val searchResult = props.queryResult
    val setCoordinates = useContext(MapCoordinatesSetterContext)
    useEffectOnce {
        searchResult.coordinates.value?.let { coordinates ->
            if (setCoordinates != null) {
                setCoordinates {
                    it.add(Coordinates(coordinates, (searchResult.name.value?:searchResult.label.value), searchResult.cityName.value, searchResult.countryName.value))
                    mutableListOf(*it.toTypedArray())
                }
            }
        }
    }
    div {
        className = "card-result"
        div {
            div {
                span {
                    +(searchResult.name.value?:searchResult.label.value)
                }
                span {
                    +"${searchResult.cityName.value}, ${searchResult.countryName.value}"
                }
            }
            // TODO
//            img {
//                src = props.logoURL
//                alt = "Image de ${props.title}"
//            }
            img {
                src = "https://www.insa-lyon.fr/sites/www.insa-lyon.fr/files/logo-coul.jpg"
                alt = "INSA"
            }
        }
        p {
            if (!searchResult.abstract.value.isNullOrBlank())  +searchResult.abstract.value!!
            else if (!searchResult.comment.value.isNullOrBlank()) +searchResult.comment.value!!
            // TODO wrap text
        }
    }
}