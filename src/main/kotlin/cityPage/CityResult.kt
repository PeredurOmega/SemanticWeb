package cityPage

import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import tools.sparql.GetCityResponse
import tools.sparql.SparqlQueryConsumerProps

external interface CityResultProps : SparqlQueryConsumerProps<GetCityResponse>

val cityResult = FC<CityResultProps> { props ->
    val searchResult = props.queryResult
    div {
        className = "card-result"
        div {
            div {
                div {
                    if (! searchResult.name?.value.isNullOrBlank() && ! searchResult.countryName?.value.isNullOrBlank()) {
                        +"${searchResult.name!!.value}, ${searchResult.countryName!!.value}"
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
                        if (! searchResult.thumbnail?.value.isNullOrBlank()) {
                            src = convertThumbnailToPath(searchResult.thumbnail!!.value)
                        }
                            //"https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Place_lazare_goujon.jpg/300px-Place_lazare_goujon.jpg"
                    }
                }
                div {
                    ul {
                        if (! searchResult.postalCode?.value.isNullOrBlank()) {
                            li {
                                +"Code postal: "
                                span {
                                    +searchResult.postalCode!!.value
                                }
                            }
                        }
                        if (! searchResult.inseeCode?.value.isNullOrBlank()) {
                            li {
                                +"Code commune INSEE: "
                                span {
                                    +searchResult.inseeCode!!.value
                                }
                            }
                        }
                        if (! searchResult.area?.value.isNullOrBlank()) {
                            li {
                                +"Superficie :"
                                span {
                                   +searchResult.area!!.value
                                }
                            }
                        }
                        if (! searchResult.altitudeMin?.value.isNullOrBlank()) {
                            li {
                                +"Altitude min.: "
                            }
                        }
                        if (! searchResult.altitudeMax?.value.isNullOrBlank()) {
                            li {
                                +"Altitude max.: "
                            }
                        }
                        if (! searchResult.mayor?.value.isNullOrBlank()) {
                            li {
                                +"Maire: "
                            }
                        }
                        if (! searchResult.politicalParty?.value.isNullOrBlank()) {
                            li {
                                +"Parti politique: "
                            }
                        }
                        if (! searchResult.district?.value.isNullOrBlank()) {
                            li {
                                +"Arrondissement: "
                            }
                        }
                    }
                }
            }
        }
    }
}

fun convertThumbnailToPath(thumbnail: String) : String {
    console.log(thumbnail)
    val delimPath = "Special:FilePath/"
    val pathWithArgs = thumbnail.split(delimPath)[1]
    console.log("pathWithArgs=", pathWithArgs)
    val delimArgs = '?'
    val path = pathWithArgs?.split(delimArgs)[0]
    console.log("path=", "Special:FilePath/$path")

    return "http://en.wikipedia.org/wiki/Special:FilePath/$path";
}