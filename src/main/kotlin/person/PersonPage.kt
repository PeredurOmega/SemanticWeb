package person

import kotlinext.js.jso
import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.router.useLocation
import tools.redirectToHome
import tools.requireSCSS
import sparql.getPersonImage
import sparql.getPersonInfo
import sparql.sparqlQueryLoaderSingle

external interface PersonPageLocationState : State {
    var personUri: String?
}

val personPage = FC<Props> {
    requireSCSS("person-page")
    val location = useLocation()
    val personUri = location.state.unsafeCast<PersonPageLocationState?>()?.personUri ?: return@FC redirectToHome()

    div {
        className = "main-person-div"
        sparqlQueryLoaderSingle(getPersonInfo, jso { uri = personUri }, true) {
            personInfoPanel {
                this.personUri = personUri
            }
        }
        sparqlQueryLoaderSingle(getPersonImage, jso { uri = personUri }) {
            personImagePanel {
                this.personUri = personUri
            }
        }
    }
}