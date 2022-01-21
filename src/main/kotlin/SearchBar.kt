import kotlinext.js.jso
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.router.NavigateFunction
import react.router.dom.Link
import react.router.useNavigate
import schoolPage.SchoolPageLocationState
import search.SearchPageLocationState
import tools.basicSVG

external interface SearchBarProps : Props {
    var isSmall: Boolean?
}

val searchBar = FC<SearchBarProps> { props ->
    val (searchText, setSearchText) = useState("")
    val (selectedSuggestion, setSelectedSuggestion) = useState<Int?>(null)
    val suggestions = useLookup(searchText)
    val navigate = useNavigate()
    div {
        className = "search-bar " + if (props.isSmall == true) "small-bar" else "main-bar"
        basicSVG("MainSearchIcon", "Rechercher", "search-icon") {
            search(searchText, navigate)
        }
        input {
            value = searchText
            id = "search"
            onChange = {
                setSearchText(it.currentTarget.value)
            }
            type = InputType.search
            placeholder = "Recherchez votre future université ..."
            onKeyDown = {
                if (it.key == "Enter" || it.key == "Return") {
                    if (selectedSuggestion != null) {
                        navigate(
                            "/school",
                            jso {
                                state = jso<SchoolPageLocationState> { schoolUri = suggestions[selectedSuggestion].uri }
                            })
                    } else search(searchText, navigate)
                } else if (it.key == "ArrowDown") {
                    setSelectedSuggestion(((selectedSuggestion ?: -1) + 1).coerceIn(0, suggestions.size - 1))
                    it.preventDefault()
                } else if (it.key == "ArrowUp") {
                    if (selectedSuggestion == 0) setSelectedSuggestion(null)
                    else if (selectedSuggestion != null) setSelectedSuggestion(
                        (selectedSuggestion - 1).coerceIn(
                            0,
                            suggestions.size - 1
                        )
                    )
                    it.preventDefault()
                }
            }
        }
    }
    autocompletionPanel {
        this.searchText = searchText
        this.selectedSuggestion = selectedSuggestion
        this.suggestions = suggestions
    }
}

fun search(searchText: String, navigate: NavigateFunction) {
    navigate("/search?$searchText", jso { state = jso<SearchPageLocationState> { this.searchText = searchText } })
}

external interface AutocompletionPanelProps : Props {
    var searchText: String
    var selectedSuggestion: Int?
    var suggestions: List<Suggestion>
}

val autocompletionPanel = FC<AutocompletionPanelProps> { props ->

    div {
        className = "autocompletion"
        props.suggestions.forEachIndexed { i, suggestion ->
            Link {
                this.to = "/school"
                this.state = jso<SchoolPageLocationState> { schoolUri = suggestion.component2() }
                if (props.selectedSuggestion == i) className = "selected"
                val labelSplit = Regex("(.*)(${props.searchText})(.*)", RegexOption.IGNORE_CASE).find(suggestion.label)
                if (labelSplit != null) {
                    ReactHTML.b {
                        +labelSplit.groupValues[1]
                    }
                    +labelSplit.groupValues[2]
                    ReactHTML.b {
                        +labelSplit.groupValues[3]
                    }
                } else +suggestion.label
            }
        }
    }
}


fun useLookup(searchText: String, isMainPage: Boolean = true): List<Suggestion> {
    val (suggestions, setSuggestions) = useState<List<Suggestion>>(listOf())
    val queryRef = useRef(0)
    useEffect(searchText) {
        if (searchText.isNotBlank()) {
            dbpediaLookup(searchText, setSuggestions, queryRef)
        }
    }
    if (isMainPage) {
        useEffect(suggestions.isEmpty()) {
            val input = document.getElementById("search")!!
            if (suggestions.isEmpty()) input.removeClass("with-suggestions")
            else input.addClass("with-suggestions")
        }
    }
    return suggestions
}

fun dbpediaLookup(searchText: String, setSuggestions: StateSetter<List<Suggestion>>, queryRef: MutableRefObject<Int>) {
    queryRef.current = queryRef.current?.plus(1)
    val currentQueryRef = queryRef.current
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort() && currentQueryRef == queryRef.current) {
            if (xhr.status == 200.toShort()) {
                val result: LookupResult = JSON.parse(xhr.responseText)
                val filteredResult = result.docs.filter {
                    it.category?.any { category ->
                        category == "http://dbpedia.org/resource/Category:Grandes_écoles" || category == "http://dbpedia.org/resource/Category:Technical_universities_and_colleges_in_France"
                    } ?: false
                }.take(5)
                val suggestions = filteredResult.map {
                    val labels = (it.label ?: arrayOf()) + (it.redirectlabel ?: arrayOf())
                    val firstLabel = (labels.firstOrNull { label ->
                        label.replace(Regex("<[^>]*>"), "").contains(searchText, true)
                    } ?: labels.first()).replace(Regex("<[^>]*>"), "")
                    Suggestion(firstLabel, it.resource.first())
                }
                setSuggestions(suggestions)
            } else {
                //TODO Gérer les cas d'erreurs
            }
        }
    }
    xhr.open(
        "GET",
        "https://lookup.dbpedia.org/api/search?query=${encodeURIComponent(searchText)}&type=Grande_école&format=json"
    )
    xhr.send()
}

data class Suggestion(val label: String, val uri: String)

external fun encodeURIComponent(str: String): String

external interface LookupResult {
    var docs: Array<Result>

    interface Result {
        var label: Array<String>?
        var redirectlabel: Array<String>?
        var category: Array<String>?
        var resource: Array<String>
    }
}