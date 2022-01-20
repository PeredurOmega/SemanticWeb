package schoolPage

import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
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
    println(schoolUri)

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
            this.schoolUri = schoolUri
        }
        mapResult {

        }
    }

}