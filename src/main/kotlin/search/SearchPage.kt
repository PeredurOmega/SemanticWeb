package search

import Suggestion
import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.router.Navigate
import react.router.useLocation
import tools.requireSCSS
import tools.sparql.ProgressBarContext
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
    val (suggestions, resetSuggestions) = useLookup(searchText, false)
    val showProgressBar = useContext(ProgressBarContext)

    useEffect(suggestions) {
        if (suggestions == null) showProgressBar(true)
        else if (suggestions.isEmpty()) showProgressBar(false)
    }

    useEffect(searchText) {
        resetSuggestions(false)
    }

    div {
        className = "search-page"
        if (suggestions != null) {
            if (suggestions.isEmpty()) {
                h1 {
                    +"Aucun résultat pour votre recherche"
                }
            } else {
                searchPageResults {
                    this.suggestions = suggestions
                }
            }
        }
    }
}

private external interface SearchPageResultsProps : Props {
    var suggestions: List<Suggestion>
}

private val searchPageResults = FC<SearchPageResultsProps> { props ->
    mapCoordinatesContextProvider {
        div {
            className = "card-results"
            props.suggestions.forEach {
                sparqlQueryLoaderSingle(getSearchResult, jso { uri = it.uri }, true) {
                    cardResult {
                        uri = it.uri
                    }
                }
            }
        }
        mapResult {
            expectedCount = props.suggestions.size
        }
    }
}