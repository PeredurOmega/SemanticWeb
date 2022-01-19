package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

external interface ImageDisplayProps : Props {
    var schoolImageUri : String
}


val imageDisplay = FC<ImageDisplayProps> { props ->
    div {
        img {
            src = props.schoolImageUri
        }
    }
}