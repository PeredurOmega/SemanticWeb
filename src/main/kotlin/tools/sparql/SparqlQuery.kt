package tools.sparql

import encodeURIComponent
import kotlinext.js.getOwnPropertyNames
import org.w3c.xhr.XMLHttpRequest
import react.StateSetter


class SparqlSingleResult <V: SparqlVariables, R: SparqlResponse> (queryName: String) : SparqlQuery<V, R>(queryName)

class SparqlMultipleResults <V: SparqlVariables, R: SparqlResponse> (queryName: String) : SparqlQuery<V, R>(queryName)

abstract class SparqlQuery<V: SparqlVariables, R: SparqlResponse> (private val queryName: String) {

    fun execute(variables: V, saveState: StateSetter<Array<R>?>) {
        retrieveSparqlQuery {
            var query = it

            variables.getOwnPropertyNames().forEach { property ->
                query = query.replace("\$$property\$", variables.asDynamic()[property].toString())
            }

            val xhr = XMLHttpRequest()
            xhr.onreadystatechange = {
                if (xhr.readyState == 4.toShort()) {
                    if (xhr.status == 200.toShort()) {
                        saveState(JSON.parse<RawSparqlResponse<R>>(xhr.responseText).results.bindings)
                    }
                    else {
                        //TODO Gérer les cas d'erreurs
                    }
                }
            }
            xhr.open("GET", "https://dbpedia.org/sparql?query=${encodeURIComponent(query)}&format=json")
            xhr.send()
        }
    }

    private fun retrieveSparqlQuery(operation : (query : String) -> Unit) {
        val xhr = XMLHttpRequest()
        xhr.onreadystatechange = {
            if (xhr.readyState == 4.toShort()) {
                if (xhr.status == 200.toShort()) {
                    operation(xhr.responseText)
                }
                else {
                    //TODO Gérer les cas d'erreurs
                }
            }
        }
        xhr.open("GET", "/queries/$queryName.sparql")
        xhr.send()
    }

}

external interface SparqlVariables

external interface SparqlResponse

private external interface RawSparqlResponse<R: SparqlResponse> {
    var results : RawResult<R>
}

private external interface RawResult<R: SparqlResponse> {
    var bindings : Array<R>
}

external interface SparqlValue<V> {
    var value : V
}
