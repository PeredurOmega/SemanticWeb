import react.FC
import react.Props
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.nav
import tools.basicSVG
import tools.requireSCSS

val navBar = FC<Props> {
    requireSCSS("navbar")
    nav {
        basicSVG("GesearchLogo", "Logo de Gesearch")
        input {
            className = "search-input"
        }
    }
}