package schoolPage

import react.FC
import react.Props
import react.useState
import search.mapResult
import tools.useWikipediaScrapper

external interface ImagesPanelProps : Props {
    var schoolUri : String
}

val imagesPanel = FC<ImagesPanelProps> { props ->
    val schoolImagesUri = useWikipediaScrapper(props.schoolUri)
    if (schoolImagesUri.isNotEmpty()) {
        val schoolLogoUri = schoolImagesUri[0]
        val schoolImageUri = schoolImagesUri[1]
        imageDisplay {
            this.schoolImageUri = schoolLogoUri
        }
        imageDisplay {
            this.schoolImageUri = schoolImageUri
        }
    }
}