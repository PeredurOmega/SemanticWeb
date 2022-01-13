import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import tools.basicSVG
import tools.requireSCSS

val mainPage = FC<Props> {
    requireSCSS("main-page")
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
    val (searchText, setSearchText) = useState("")
    div {
        className = "main-search-bar"
        basicSVG("MainSearchIcon", "Rechercher", "search-icon", ::search)
        input {
            value = searchText
            onChange = {
                setSearchText(it.currentTarget.value)
            }
            type = InputType.search
            placeholder = "Recherchez votre future université ..."
        }
    }
    autocompletionPanel {
        this.searchText = searchText
    }
}

external interface AutocompletionPanelProps : Props {
    var searchText : String
}

val autocompletionPanel = FC<AutocompletionPanelProps> { props ->
    val suggestions = useLookup(props.searchText)
    div {
        className = "autocompletion"
        suggestions.forEach {
            div {
                +it.label
            }
        }
    }
}

fun useLookup(searchText: String) : List<Suggestion> {
    val (suggestions, setSuggestions) = useState<List<Suggestion>>(listOf())
    useEffect(searchText) {
        if (searchText.isNotBlank()) {
            dbpediaLookup(searchText, setSuggestions)
        }
    }
    return suggestions
}

fun dbpediaLookup(searchText: String, setSuggestions: StateSetter<List<Suggestion>>) {
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort()) {
            if (xhr.status == 200.toShort()) {
                val result : LookupResult = JSON.parse(xhr.responseText)
                val filteredResult = result.docs.filter {
                    it.category?.any { category ->
                        category == "http://dbpedia.org/resource/Category:Grandes_écoles" || category == "http://dbpedia.org/resource/Category:Technical_universities_and_colleges_in_France"
                    } ?: false
                }.take(5)
                val suggestions = filteredResult.map {
                    val labels = (it.label ?: arrayOf()) + (it.redirectlabel ?: arrayOf())
                    val firstLabel = (labels.firstOrNull { label ->
                        label.replace(Regex("<[^>]*>"), "").contains(searchText,true)
                    }?: labels.first()).replace(Regex("<[^>]*>"), "")
                    Suggestion(firstLabel, it.resource.first())
                }
                setSuggestions(suggestions)
            }
            else {
                //TODO Gérer les cas d'erreurs
            }
        }
    }
    xhr.open("GET", "https://lookup.dbpedia.org/api/search?query=${encodeURIComponent(searchText)}&type=Grande_école&format=json")
    xhr.send()
}

data class Suggestion (val label : String, val uri : String)

external fun encodeURIComponent(str : String) : String

external interface LookupResult {
    var docs: Array<Result>

    interface Result {
        var label: Array<String>?
        var redirectlabel: Array<String>?
        var category: Array<String>?
        var resource: Array<String>
    }
}

val mainLogo = FC<Props> {
    basicSVG("GesearchLogo", "Logo de Gesearch", "main-logo")
}
