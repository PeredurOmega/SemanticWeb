package schoolPage

import kotlinext.js.jso
import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.router.useLocation
import search.mapCoordinatesContextProvider
import search.mapResult
import sparql.getSchoolSameFr
import sparql.sparqlQueryLoaderSingle
import tools.redirectToHome
import tools.requireSCSS

external interface SchoolPageLocationState : State {
    var schoolUri: String?
}

val schoolPage = FC<Props> {
    requireSCSS("school-page")
    val location = useLocation()
    val schoolUri = location.state.unsafeCast<SchoolPageLocationState?>()?.schoolUri ?: return@FC redirectToHome()

    div {
        className = "school-page"
        mapCoordinatesContextProvider {
            div {
                className = "left-panel"
                schoolInfoPanel {
                    this.schoolUri = schoolUri
                }
                hr {
                    id = "special-hr"
                }
                relatedPersonPanel {
                    this.schoolUri = schoolUri
                }
            }
            div {
                className = "right-panel"
                sparqlQueryLoaderSingle(getSchoolSameFr, jso { uri = schoolUri }) {
                    imagesPanel {
                        this.uri = schoolUri
                    }
                }
                mapResult {
                    expectedCount = 1
                }
            }
        }
    }
}