package sparql

import search.encodeURIComponent
import kotlinext.js.getOwnPropertyNames
import org.w3c.xhr.XMLHttpRequest
import react.MutableRefObject
import react.StateSetter


class SparqlSingleResult<V : SparqlVariables, R : SparqlResponse>(queryName: String) : SparqlQuery<V, R>(queryName)

class SparqlMultipleResults<V : SparqlVariables, R : SparqlResponse>(queryName: String) : SparqlQuery<V, R>(queryName)

abstract class SparqlQuery<V : SparqlVariables, R : SparqlResponse>(private val queryName: String) {

    fun execute(variables: V, saveState: StateSetter<Array<R>?>, isMounted: MutableRefObject<Boolean>) {
        retrieveSparqlQuery {
            var query = it

            variables.getOwnPropertyNames().forEach { property ->
                query = query.replace("\$$property\$", variables.asDynamic()[property].toString())
            }

            val xhr = XMLHttpRequest()
            xhr.onreadystatechange = {
                if (xhr.readyState == 4.toShort()) {
                    if (xhr.status == 200.toShort() && isMounted.current == true) {
                        saveState(JSON.parse<RawSparqlResponse<R>>(xhr.responseText).results.bindings)
                    }
                }
            }
            xhr.open("GET", "https://dbpedia.org/sparql?query=${encodeURIComponent(query)}&format=json")
            xhr.send()
        }
    }

    private fun retrieveSparqlQuery(operation: (query: String) -> Unit) {
        val xhr = XMLHttpRequest()
        xhr.onreadystatechange = {
            if (xhr.readyState == 4.toShort()) {
                if (xhr.status == 200.toShort()) {
                    operation(xhr.responseText)
                }
            }
        }
        xhr.open("GET", "/queries/$queryName.sparql")
        xhr.send()
    }
}

external interface SparqlVariables

external interface SparqlResponse

private external interface RawSparqlResponse<R : SparqlResponse> {
    var results: RawResult<R>
}

private external interface RawResult<R : SparqlResponse> {
    var bindings: Array<R>
}

external interface SparqlValue<V> {
    var value: V
}

class Placeholder(val showPlaceholder: Boolean)

fun <V : String?> SparqlValue<V>?.whenNotBlank(block: (String) -> Unit): Placeholder {
    return if (this != null && !value.isNullOrBlank()) {
        block(value!!)
        return Placeholder(false)
    } else Placeholder(true)
}

infix fun Placeholder.placeholder(block: () -> Unit) {
    if (showPlaceholder) block()
}

infix fun Placeholder.orElse(block: () -> Placeholder) = if (showPlaceholder) block() else this