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
import school.SchoolPageLocationState
import sparql.*
import tools.concatenate
import tools.useWikipediaScrapper

external interface CardResultProps : SparqlQueryConsumerProps<GetSearchResultResponse> {
    var uri: String
}

val cardResult = FC<CardResultProps> { props ->
    useCoordinatesPlacer(props)
    Link {
        this.to = "/school"
        this.state = jso<SchoolPageLocationState> { schoolUri = props.uri }

        schoolCardDetail {
            queryResult = props.queryResult
            uri = props.uri
        }
    }
}

private val schoolCardDetail = FC<CardResultProps> { props ->
    val searchResult = props.queryResult
    div {
        className = "card-result"
        div {
            div {
                span {
                    searchResult.name.whenNotBlank {
                        +it.replace('-', '‑')
                    } placeholder {
                        +searchResult.label.value.replace('-', '‑')
                    }
                }
                span {
                    searchResult.cityName.whenNotBlank { city ->
                        searchResult.countryName.whenNotBlank { country ->
                            +"$city, $country"
                        }
                    }
                }
            }
            schoolLogo {
                sameFr = searchResult.sameFr.value
                uri = props.uri
                alt = "Logo de ${searchResult.name?.value ?: searchResult.label.value}"
            }
        }
        p {
            searchResult.comment.whenNotBlank { +it } orElse { searchResult.abstract.whenNotBlank { +it } }
        }
    }
}

private fun useCoordinatesPlacer(props: CardResultProps) {
    val setCoordinates = useContext(MapCoordinatesSetterContext)
    useEffectOnce {
        setCoordinates!!.invoke {
            it.add(coordinates(props.queryResult, props.uri))
            mutableListOf(*it.toTypedArray())
        }
    }
}

private fun coordinates(searchResult: GetSearchResultResponse, uri: String): Coordinates {
    return Coordinates(
        searchResult.coordinates?.value,
        (searchResult.name?.value ?: searchResult.label.value),
        uri,
        concatenate(searchResult.cityName, searchResult.countryName),
        searchResult.cityUrl?.value
    )
}

private external interface LogoUrlProps : Props {
    var uri: String
    var sameFr: String
    var alt: String
}

private val schoolLogo = FC<LogoUrlProps> { props ->
    val logoUri = useWikipediaScrapper(props.uri, props.sameFr, 1) {
        it.contains("logo", ignoreCase = true) || it.contains("signature", ignoreCase = true)
    }

    if (logoUri.isNotEmpty()) {
        img {
            src = logoUri[0]
            alt = props.alt
        }
    }
}