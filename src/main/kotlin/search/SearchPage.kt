package search

import navBar
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import tools.map.marker
import tools.map.popup
import tools.requireSCSS
import kotlin.js.json

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
            mapResult {
                id = "map"
                center = json("lat" to "46.71", "lng" to "1.72")
                zoom = 6
                tileSize = 1
                attribution = "Grandes Ecoles"
                url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"

                repeat(5) {
                    marker {
                        position = json("lat" to "${kotlin.random.Random.Default.nextDouble(43.0, 50.0)}", "lng" to "${kotlin.random.Random.Default.nextDouble(-2.4,7.6)}")
                        popup {
                            p {
                                +"popup text"
                            }
                        }
                    }
                }
            }
        }
    }
}