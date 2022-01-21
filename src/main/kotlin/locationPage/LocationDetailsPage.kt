package locationPage

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.router.useLocation
import tools.requireSCSS
import tools.sparql.*

external interface LocationDetailsPageLocationState : State {
    var locationDetailsName : String?
}

val locationDetailsPage = FC<Props> {
    requireSCSS("location-details-page")

    val location = useLocation()
    val locationDetailsName = "http://dbpedia.org/resource/Villeurbanne";
  //  location.state.unsafeCast<LocationDetailsPageLocationState?>()?.locationDetailsName ?: return@FC Navigate {
    //    to = "/"
      //  replace = true
    //}

    div {
        className = ""
        div {
            className = "location-details-results"
            sparqlQueryLoaderSingle(
                getLocationDetailsResult,
                jso {
                    uri = locationDetailsName
                }) {
                locationDetailsResult {
                }
            }
        }
    }
}

fun convertThumbnailToPath(thumbnail: String) : String {
    val delimPath = "wiki-commons:Special:FilePath/"
    val pathWithArgs = thumbnail.split(delimPath)[1]
    console.log("pathWithArgs=", pathWithArgs)
    val delimArgs = '?'
    val path = pathWithArgs?.split(delimArgs)[0]
    console.log("path=", "http://en.wikipedia.org/wiki/Special:FilePath/$path")


    return "http://en.wikipedia.org/wiki/Special:FilePath/$path";
}
