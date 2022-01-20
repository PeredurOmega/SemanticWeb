package schoolPage

import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.router.Navigate
import react.router.useLocation
import search.mapResult
import tools.requireSCSS

external interface SchoolPageLocationState : State {
    var schoolUri : String?
}


val schoolPage = FC<Props> {

    requireSCSS("school-page")
    val location = useLocation()
    val schoolUri = location.state.unsafeCast<SchoolPageLocationState?>()?.schoolUri?: return@FC Navigate {
        to = "/"
        replace = true
    }

    div {
        className = "school-page"
        div {
            className = "left-panel"
            schoolInfoPanel {
                this.schoolUri = schoolUri
            }
            hr { }
            relatedPersonPanel {
                this.schoolUri = schoolUri
            }
        }
        div {
            className = "right-panel"
            imagesPanel {
                this.schoolUri = schoolUri
            }
//            mapResult {
//
//            }
        }
    }

}