package tools.sparql

import kotlinext.js.jso
import react.*

private fun <V : SparqlVariables, R : SparqlResponse> useSparqlQuery(sparqlQuery: SparqlQuery<V, R>, variables: V): Array<R>? {
    val (response, setResponse) = useState<Array<R>?>(null)

    useEffectOnce {
        sparqlQuery.execute(variables, setResponse)
    }

    return response
}

fun <V : SparqlVariables, R : SparqlResponse> useSparqlMultipleResults(sparqlQuery: SparqlMultipleResults<V, R>, variables: V): Array<R>? {
    return useSparqlQuery(sparqlQuery, variables)
}

fun <V : SparqlVariables, R : SparqlResponse> useSparqlSingleResult(sparqlQuery: SparqlSingleResult<V, R>, variables: V): R? {
    return useSparqlQuery(sparqlQuery, variables)?.getOrNull(0)
}

external interface SparqlQueryConsumerProps<R : SparqlResponse> : Props {
    var queryResult: R
}

fun <V : SparqlVariables, R : SparqlResponse> ChildrenBuilder.sparqlQueryLoader(
    sparqlQuery: SparqlSingleResult<V, R>,
    variables: V,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoader {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlSingleResult<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        block()
    }
}

private external interface SparqlQueryLoaderProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlSingleResult<V, R>
    var variables: V
}

private val sparqlQueryLoader = FC<SparqlQueryLoaderProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlSingleResult(props.sparqlQuery, props.variables)
    if (queryResult != null) {
        val element = cloneElement(
            Children.only(props.children),
            jso<SparqlQueryConsumerProps<SparqlResponse>> { this.queryResult = queryResult })
        child(element)
    }
}

