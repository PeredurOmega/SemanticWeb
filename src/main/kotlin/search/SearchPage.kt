package search

import kotlinext.js.jso
import react.FC
import react.Props
import react.State
import react.dom.html.ReactHTML.div
import react.router.Navigate
import react.router.useLocation
import tools.requireSCSS
import tools.sparql.getSearchResult
import tools.sparql.sparqlQueryLoaderSingle
import useLookup

external interface SearchPageLocationState : State {
    var searchText: String?
}

val searchPage = FC<Props> {
    requireSCSS("search-page")
    val location = useLocation()
    val searchText = location.state.unsafeCast<SearchPageLocationState?>()?.searchText ?: return@FC Navigate {
        to = "/"
        replace = true
    }
    val suggestions = useLookup(searchText, false)
    if (suggestions.isNotEmpty()) {
        div {
            className = "search-page"
            mapCoordinatesContextProvider {
                div {
                    className = "card-results"
                    suggestions.forEach {
                        sparqlQueryLoaderSingle(getSearchResult, jso { uri = it.uri }, true) {
                            cardResult {
                                uri = it.uri
                            }
                        }
                    }
                }
                mapResult { }
            }
        }
    }
}