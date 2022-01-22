package tools.sparql

import kotlinext.js.jso
import react.*
import tools.useProgressBar

private fun <V : SparqlVariables, R : SparqlResponse> useSparqlQuery(
    sparqlQuery: SparqlQuery<V, R>,
    variables: V
): Array<R>? {
    val (response, setResponse) = useState<Array<R>?>(null)
    val isMounted = useRef(true)

    useEffect(sparqlQuery, variables) {
        sparqlQuery.execute(variables, setResponse, isMounted)
    }

    useEffectOnce {
        cleanup {
            isMounted.current = false
        }
    }

    return response
}

fun <V : SparqlVariables, R : SparqlResponse> useSparqlMultipleResults(
    sparqlQuery: SparqlMultipleResults<V, R>,
    variables: V
): Array<R>? {
    return useSparqlQuery(sparqlQuery, variables)
}

fun <V : SparqlVariables, R : SparqlResponse> useSparqlSingleResult(
    sparqlQuery: SparqlSingleResult<V, R>,
    variables: V
): R? {
    return useSparqlQuery(sparqlQuery, variables)?.getOrNull(0)
}

external interface SparqlQueryConsumerProps<R : SparqlResponse> : Props {
    var queryResult: R
}

fun <V : SparqlVariables, R : SparqlResponse> ChildrenBuilder.sparqlQueryLoaderSingle(
    sparqlQuery: SparqlSingleResult<V, R>,
    variables: V,
    showLoading: Boolean = false,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoaderSingle {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlSingleResult<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        this.displayLoading = showLoading
        block()
    }
}

private external interface SparqlQueryLoaderSingleProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlSingleResult<V, R>
    var variables: V

    /**
     * Default False.
     */
    var displayLoading: Boolean?
}

private val sparqlQueryLoaderSingle = FC<SparqlQueryLoaderSingleProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlSingleResult(props.sparqlQuery, props.variables)
    useProgressBar(queryResult == null, props.displayLoading)
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
    showLoading: Boolean = false,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoaderMultiple {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlMultipleResults<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        this.displayLoading = showLoading
        block()
    }
}

private external interface SparqlQueryLoaderMultipleProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlMultipleResults<V, R>
    var variables: V

    /**
     * Default False.
     */
    var displayLoading: Boolean?
}

private val sparqlQueryLoaderMultiple = FC<SparqlQueryLoaderMultipleProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlMultipleResults(props.sparqlQuery, props.variables)
    useProgressBar(queryResult == null, props.displayLoading)
    if (queryResult != null) {
        val element = cloneElement(
            Children.only(props.children),
            jso<SparqlQueryArrayConsumerProps<SparqlResponse>> { this.queryResult = queryResult })
        child(element)
    }
}

