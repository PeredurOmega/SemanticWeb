package tools.sparql

import react.useEffectOnce
import react.useState

fun <V: SparqlVariables, R: SparqlResponse> useSparqlQuery(sparqlQuery : SparqlQuery<V, R>, variables: V): R? {
    val (response, setResponse) = useState<R?>(null)

    useEffectOnce {
        sparqlQuery.execute(variables, setResponse)
    }

    return response
}

