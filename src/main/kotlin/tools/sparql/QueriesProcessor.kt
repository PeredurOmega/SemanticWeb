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

fun <V : SparqlVariables, R : SparqlResponse> ChildrenBuilder.sparqlQueryLoaderSingle(
    sparqlQuery: SparqlSingleResult<V, R>,
    variables: V,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoaderSingle {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlSingleResult<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        block()
    }
}

private external interface SparqlQueryLoaderSingleProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlSingleResult<V, R>
    var variables: V
}

private val sparqlQueryLoaderSingle = FC<SparqlQueryLoaderSingleProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlSingleResult(props.sparqlQuery, props.variables)
    if (queryResult != null) {
        val element = cloneElement(
            Children.only(props.children),
            jso<SparqlQueryConsumerProps<SparqlResponse>> { this.queryResult = queryResult })
        child(element)
    }
}

external interface SparqlQueryArrayConsumerProps<R : SparqlResponse> : Props {
    var queryResult: Array<R>
}

fun <V : SparqlVariables, R : SparqlResponse> ChildrenBuilder.sparqlQueryLoaderMultiple(
    sparqlQuery: SparqlMultipleResults<V, R>,
    variables: V,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoaderMultiple {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlMultipleResults<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        block()
    }
}

private external interface SparqlQueryLoaderMultipleProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlMultipleResults<V, R>
    var variables: V
}

private val sparqlQueryLoaderMultiple = FC<SparqlQueryLoaderMultipleProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlMultipleResults(props.sparqlQuery, props.variables)
    if (queryResult != null) {
        val element = cloneElement(
            Children.only(props.children),
            jso<SparqlQueryArrayConsumerProps<SparqlResponse>> { this.queryResult = queryResult })
        child(element)
    }
}

