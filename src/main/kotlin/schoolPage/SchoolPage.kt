package schoolPage

import kotlinext.js.jso
import navBar
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState
import search.mapResult
import tools.getImagesFromWikipediaPage
import tools.requireSCSS
import tools.sparql.*

val schoolPage = FC<Props> {

    requireSCSS("school-page")

    val schoolUri = "http://dbpedia.org/resource/Institut_national_des_sciences_appliquées_de_Lyon"


    div {
        schoolInfoPanel {
            this.schoolUri = schoolUri
        }
        relatedPersonPanel {
            this.schoolUri = schoolUri
        }
    }
    div {
        imagesPanel {

        }
        mapResult {

        }
    }

}