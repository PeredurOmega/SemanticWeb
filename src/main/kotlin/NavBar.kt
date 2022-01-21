import react.FC
import react.Props
import react.dom.html.ReactHTML.nav
import react.router.dom.Link
import tools.basicSVG
import tools.requireSCSS

val navBar = FC<Props> {
    requireSCSS("navbar")
    nav {
        Link {
            to = "/"
            basicSVG("GesearchLogoSmall", "Logo de Gesearch")
        }
        searchBar {
            this.isSmall = true
        }
    }
}