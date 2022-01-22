package search

import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.xhr.XMLHttpRequest
import react.*


fun useLookup(
    searchText: String,
    isMainPage: Boolean = true
): Pair<List<Suggestion>?, (immediateDiscard: Boolean) -> Unit> {
    val (suggestions, setSuggestions) = useState<List<Suggestion>?>(null)
    val queryRef = useRef(0)
    val reset = useCallback { immediateDiscard: Boolean ->
        if (immediateDiscard) queryRef.current = queryRef.current?.plus(100)
        setSuggestions(null)
    }
    useEffect(searchText) {
        if (searchText.isNotBlank()) {
            dbpediaLookup(searchText, setSuggestions, queryRef)
        }
    }
    if (isMainPage) {
        useEffect(suggestions?.isEmpty() != false) {
            val input = document.getElementById("search")!!
            if (suggestions?.isEmpty() != false) input.removeClass("with-suggestions")
            else input.addClass("with-suggestions")
        }
    }
    return suggestions to reset
}

private fun dbpediaLookup(
    searchText: String,
    setSuggestions: StateSetter<List<Suggestion>?>,
    queryRef: MutableRefObject<Int>
) {
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
            } else setSuggestions(listOf())
        }
    }
    xhr.open(
        "GET",
        "https://lookup.dbpedia.org/api/search?query=${encodeURIComponent(searchText)}&type=Grande_école&format=json"
    )
    xhr.send()
}

external fun encodeURIComponent(str: String): String

data class Suggestion(val label: String, val uri: String)

private external interface LookupResult {
    var docs: Array<Result>

    interface Result {
        var label: Array<String>?
        var redirectlabel: Array<String>?
        var category: Array<String>?
        var resource: Array<String>
    }
}