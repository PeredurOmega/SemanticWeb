package search

import kotlinext.js.jso
import navBar
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import tools.map.marker
import tools.map.popup
import tools.requireSCSS
import tools.sparql.getSearchResult
import tools.sparql.useSparqlQuery
import kotlin.js.json

val searchPage = FC<Props> {
    requireSCSS("search-page")

    val searchResult = useSparqlQuery(getSearchResult, jso{
        uri = "http://dbpedia.org/resource/École_Polytechnique"
    })

    navBar { }

    if (searchResult != null) {
        div {
            className = "search-page"
            div {
                className = "card-results"
                repeat(5) {
                    cardResult {
                        schoolInfos = searchResult
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
}