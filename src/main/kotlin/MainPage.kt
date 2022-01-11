import org.w3c.dom.events.Event
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import tools.basicSVG
import tools.requireSCSS

val mainPage = FC<Props> {
    requireSCSS("main_page")
    div {
        className = "main-background"
        div {
            className = "search-elements"
            mainLogo { }
            mainSearchBar { }
        }
    }
}

fun search(event: Event) {
    //TODO
}

val mainSearchBar = FC<Props> {
    div {
        className = "main-search-bar"
        basicSVG("MainSearchIcon", "Rechercher", "search-icon", ::search)
        input {
            type = InputType.search
            placeholder = "Recherchez votre future université ..."
        }
    }
}

val mainLogo = FC<Props> {
    basicSVG("GesearchLogo", "Logo de Gesearch", "main-logo")
}
