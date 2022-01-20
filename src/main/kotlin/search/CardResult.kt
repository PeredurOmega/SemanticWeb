package search

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useContext
import react.useEffectOnce
import tools.sparql.GetSearchResultResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.useWikipediaScrapper

external interface CardResultProps : SparqlQueryConsumerProps<GetSearchResultResponse> {
    var uri : String
}

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
                    +(searchResult.name.value?.replace('-', '‑') ?:searchResult.label.value).replace('-', '‑')
                }
                span {
                    +"${searchResult.cityName.value}, ${searchResult.countryName.value}"
                }
            }
            schoolLogo {
                uri = props.uri
                alt = "Logo de ${searchResult.name.value?:searchResult.label.value}"
            }
        }
        p {
            if (!searchResult.abstract.value.isNullOrBlank())  +searchResult.abstract.value!!
            else if (!searchResult.comment.value.isNullOrBlank()) +searchResult.comment.value!!
            // TODO wrap text
        }
    }
}

external interface LogoUrlProps : Props {
    var uri : String
    var alt : String
}

private val schoolLogo = FC<LogoUrlProps> { props ->
    val logoUri = useWikipediaScrapper(props.uri, 1, "logo")
    if (logoUri.isNotEmpty()) {
        img {
            src = logoUri[0]
            alt = props.alt
        }
    }
}