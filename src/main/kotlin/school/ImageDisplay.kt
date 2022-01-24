package school

import react.FC
import react.Props
import react.dom.html.ReactHTML.img

external interface ImageDisplayProps : Props {
    var schoolImageUri: String
    var className: String
}

val imageDisplay = FC<ImageDisplayProps> { props ->
    img {
        className = props.className
        src = props.schoolImageUri
    }
}