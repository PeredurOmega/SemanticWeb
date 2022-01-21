import org.w3c.dom.events.Event
import react.*
import react.dom.html.ReactHTML.div
import tools.basicSVG
import tools.requireSCSS


val mainPage = FC<Props> {
    requireSCSS("main-page")
    div {
        className = "main-background"
        div {
            className = "search-elements"
            mainLogo { }
            searchBar { this.isSmall = false }
        }
    }
}

fun search(event: Event) {
    //TODO
}



val mainLogo = FC<Props> {
    basicSVG("GesearchLogo", "Logo de Gesearch", "main-logo")
}
