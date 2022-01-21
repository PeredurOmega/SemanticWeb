package person

import kotlinext.js.jso
import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.router.Navigate
import react.router.useLocation
import tools.basicSVG
import tools.requireSCSS
import tools.sparql.getPersonImage
import tools.sparql.getPersonInfo
import tools.sparql.sparqlQueryLoaderSingle

external interface PersonPageLocationState : State {
    var personUri : String?
}

val personPage = FC<Props> {
    requireSCSS("person-page")
    val location = useLocation()
    val personUri = location.state.unsafeCast<PersonPageLocationState?>()?.personUri?: return@FC Navigate {
        to = "/"
        replace = true
    }
    div{
        className = "main-person-div"
        sparqlQueryLoaderSingle(getPersonInfo, jso { uri = personUri }){
            personInfoPanel {
                this.personUri = personUri
            }
        }
        sparqlQueryLoaderSingle(getPersonImage, jso { uri = personUri }){
            personImagePanel{
                this.personUri = personUri
            }
        }
    }
}