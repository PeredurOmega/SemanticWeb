package search

import kotlinext.js.jso
import navBar
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import tools.requireSCSS
import tools.sparql.getSearchResult
import tools.sparql.sparqlQueryLoaderSingle

val searchPage = FC<Props> {
    requireSCSS("search-page")

    div {
        className = "search-page"
        mapCoordinatesContextProvider {
            div {
                className = "card-results"
                repeat(5) {
                    sparqlQueryLoaderSingle(
                        getSearchResult,
                        jso {
                            uri = "http://dbpedia.org/resource/École_des_mines_d'Alès"
                        }) {
                        cardResult {
                            uri = "http://dbpedia.org/resource/École_des_mines_d'Alès"
                        }
                    }
                }
            }
            mapResult { }
        }
    }
}