package search

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span

external interface CardResultProps : Props {
    var logoURL: String
    var title: String
    var description: String
    var city: String
    var country: String
}

val cardResult = FC<CardResultProps> { props ->
    div {
        className = "card-result"
        div {
            div {
                span {
                    +props.title
                }
                span {
                    +"${props.city}, ${props.country}"
                }
            }
            img {
                src = props.logoURL
                alt = "Image de ${props.title}"
            }
        }
        p {
            +props.description
        }
    }
}