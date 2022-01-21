package search

import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import react.useContext
import react.useEffectOnce
import schoolPage.SchoolPageLocationState
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
        searchResult.coordinates?.value?.let { coordinates ->
            if (setCoordinates != null) {
                setCoordinates {
                    it.add(Coordinates(coordinates, (searchResult.name?.value?:searchResult.label.value), searchResult.cityName?.value, searchResult.countryName?.value))
                    mutableListOf(*it.toTypedArray())
                }
            }
        }
    }
    Link {
        this.to = "/school"
        this.state = jso<SchoolPageLocationState> { schoolUri = props.uri}
        div {
            className = "card-result"
            div {
                div {
                    span {
                        +(searchResult.name?.value?.replace('-', '‑') ?: searchResult.label.value).replace('-', '‑')
                    }
                    span {
                        if (!searchResult.cityName?.value.isNullOrBlank() && !searchResult.countryName?.value.isNullOrBlank())
                            +"${searchResult.cityName?.value}, ${searchResult.countryName?.value}"
                    }
                }
                schoolLogo {
                    sameFr = searchResult.sameFr.value
                    uri = props.uri
                    alt = "Logo de ${searchResult.name?.value ?: searchResult.label.value}"
                }
            }
            p {
                if (!searchResult.comment?.value.isNullOrBlank()) +searchResult.comment?.value!!
                else if (!searchResult.abstract?.value.isNullOrBlank()) +searchResult.abstract?.value!!
            }
        }
    }
}

external interface LogoUrlProps : Props {
    var uri : String
    var sameFr : String
    var alt : String
}


private val schoolLogo = FC<LogoUrlProps> { props ->
    val logoUri = useWikipediaScrapper(props.uri, props.sameFr, 1) { it.contains("logo", ignoreCase = true) || it.contains("signature", ignoreCase = true) }


    if (logoUri.isNotEmpty()) {
        img {
            src = logoUri[0]
            alt = props.alt
        }
    }
}