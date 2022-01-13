package search

import navBar
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import tools.requireSCSS

val searchPage = FC<Props> {
    requireSCSS("search-page")

    navBar { }

    div {
        className = "search-page"
        div {
            className = "card-results"
            repeat(5) {
                cardResult {
                    logoURL = "https://www.insa-lyon.fr/sites/www.insa-lyon.fr/files/logo-coul.jpg"
                    title = "INSA Lyon"
                    city = "Villeurbanne"
                    country = "France"
                    description = "L’institut national des sciences appliquées de Lyon ou INSA Lyon est une Grande école faisant " +
                            "partie des 204 écoles d'ingénieurs françaises accréditées au 1er septembre 2020 à délivrer un " +
                            "diplôme d'ingénieur. Il a été créé par Jean Capelle, alors recteur de l'université de Dakar " +
                            "et par le philosophe Gaston Berger, en application de la loi de création du 18 mars 1957, et ouvert " +
                            "le 12 novembre 1957. L'école se situe sur le campus LyonTech - La Doua à Villeurbanne."
                }
            }
        }
        div {
            className = "map-results"
        }
    }
}