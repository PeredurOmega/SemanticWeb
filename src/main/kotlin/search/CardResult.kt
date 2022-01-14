package search

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import tools.sparql.GetSearchResultResponse

external interface CardResultProps : Props {
    var schoolInfos : GetSearchResultResponse
}

val cardResult = FC<CardResultProps> { props ->
    val searchResult = props.schoolInfos
    div {
        className = "card-result"
        div {
            div {
                span {
                    +searchResult.name.value
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