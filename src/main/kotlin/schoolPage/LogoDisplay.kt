package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

external interface LogoDisplayProps : Props {
    var schoolLogoUri : String
}

val logoDisplay = FC<LogoDisplayProps> { props ->
    div {
       img {
            src = props.schoolLogoUri
       }
    }
}