package search

import kotlinext.js.jso
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.events.KeyboardEvent
import react.dom.events.KeyboardEventHandler
import react.dom.html.AutoComplete
import react.dom.html.InputType
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.router.NavigateFunction
import react.router.dom.Link
import react.router.useNavigate
import schoolPage.SchoolPageLocationState
import tools.basicSVG

external interface SearchBarProps : Props {
    var isSmall: Boolean?
}

val searchBar = FC<SearchBarProps> { props ->
    val (searchText, setSearchText) = useState("")
    val navigate = useNavigate()
    val (selectedSuggestion, suggestions, resetSuggestions, handleKeyDown) = useSuggestions(searchText, navigate)
    div {
        className = if (props.isSmall == true) "search.search-bar-container-small" else "search.search-bar-container"
        div {
            className = "search.search-bar " + if (props.isSmall == true) "small-bar" else "main-bar"
            basicSVG("MainSearchIcon", "Rechercher", "search.search-icon") {
                search(searchText, navigate, resetSuggestions)
            }
            input {
                value = searchText
                id = "search"
                onChange = {
                    setSearchText(it.currentTarget.value)
                }
                type = InputType.search
                autoComplete = AutoComplete.off
                placeholder = "Recherchez les meilleurs grandes écoles..."
                onKeyDown = handleKeyDown
            }
        }
        autocompletionPanel {
            this.searchText = searchText
            this.selectedSuggestion = selectedSuggestion
            this.suggestions = suggestions
        }
    }
}

data class SuggestionDataHolder(
    val selectedSuggestion: Int?,
    val suggestions: List<Suggestion>?,
    val resetSuggestions: (immediateDiscard: Boolean) -> Unit,
    val handleKeyDown: KeyboardEventHandler<HTMLInputElement>
)

private fun useSuggestions(searchText: String, navigate: NavigateFunction): SuggestionDataHolder {
    val (selectedSuggestion, setSelectedSuggestion) = useState<Int?>(null)
    val (suggestions, resetSuggestions) = useLookup(searchText)
    val handleKeyDown =
        useCallback(suggestions, selectedSuggestion, navigate, resetSuggestions) { k: KeyboardEvent<HTMLInputElement> ->
            if (suggestions != null) {
                k.onKeyDownWithSuggestions(
                    suggestions,
                    selectedSuggestion,
                    navigate,
                    setSelectedSuggestion,
                    resetSuggestions
                )
            } else if (k.key == "Enter" || k.key == "Return") search(k.currentTarget.value, navigate, resetSuggestions)
        }
    return SuggestionDataHolder(selectedSuggestion, suggestions, resetSuggestions, handleKeyDown)
}

private fun KeyboardEvent<HTMLInputElement>.onKeyDownWithSuggestions(
    suggestions: List<Suggestion>,
    selectedSuggestion: Int?,
    navigate: NavigateFunction,
    setSelectedSuggestion: StateSetter<Int?>,
    resetSuggestions: (immediateDiscard: Boolean) -> Unit
) {
    when (key) {
        "Enter", "Return" -> {
            if (selectedSuggestion != null) {
                navigate("/school", jso {
                    state = jso<SchoolPageLocationState> {
                        schoolUri = suggestions[selectedSuggestion].uri
                    }
                })
            } else search(currentTarget.value, navigate, resetSuggestions)
            preventDefault()
        }
        "ArrowDown" -> {
            setSelectedSuggestion(((selectedSuggestion ?: -1) + 1).coerceIn(0, suggestions.size - 1))
            preventDefault()
        }
        "ArrowUp" -> {
            if (selectedSuggestion == 0) setSelectedSuggestion(null)
            else if (selectedSuggestion != null) setSelectedSuggestion(
                (selectedSuggestion - 1).coerceIn(
                    0,
                    suggestions.size - 1
                )
            )
            preventDefault()
        }
    }
}

private fun search(
    searchText: String,
    navigate: NavigateFunction,
    resetSuggestions: (immediateDiscard: Boolean) -> Unit
) {
    navigate(
        "/search.search?$searchText",
        jso { state = jso<SearchPageLocationState> { this.searchText = searchText } })
    resetSuggestions(true)

}

private external interface AutocompletionPanelProps : Props {
    var searchText: String
    var selectedSuggestion: Int?
    var suggestions: List<Suggestion>?
}

private val autocompletionPanel = FC<AutocompletionPanelProps> { props ->
    div {
        className = "autocompletion"
        props.suggestions?.forEachIndexed { i, suggestion ->
            Link {
                this.to = "/school"
                this.state = jso<SchoolPageLocationState> { schoolUri = suggestion.component2() }
                if (props.selectedSuggestion == i) className = "selected"
                val labelSplit = Regex("(.*)(${props.searchText})(.*)", RegexOption.IGNORE_CASE).find(suggestion.label)
                if (labelSplit != null) {
                    b {
                        +labelSplit.groupValues[1]
                    }
                    +labelSplit.groupValues[2]
                    b {
                        +labelSplit.groupValues[3]
                    }
                } else +suggestion.label
            }
        }
    }
}
