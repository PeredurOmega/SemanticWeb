package tools.sparql

import kotlinext.js.jso
import react.*

fun <V : SparqlVariables, R : SparqlResponse> useSparqlQuery(sparqlQuery: SparqlQuery<V, R>, variables: V): R? {
    val (response, setResponse) = useState<R?>(null)

    useEffectOnce {
        sparqlQuery.execute(variables, setResponse)
    }

    return response
}

external interface SparqlQueryConsumerProps : Props {
    var queryResult: SparqlResponse
}

fun <V : SparqlVariables, R : SparqlResponse> ChildrenBuilder.sparqlQueryLoader(
    sparqlQuery: SparqlQuery<V, R>,
    variables: V,
    block: ChildrenBuilder.() -> Unit
) {
    sparqlQueryLoader {
        this.sparqlQuery = sparqlQuery.unsafeCast<SparqlQuery<SparqlVariables, SparqlResponse>>()
        this.variables = variables
        block()
    }
}

private external interface SparqlQueryLoaderProps<V : SparqlVariables, R : SparqlResponse> : PropsWithChildren {
    var sparqlQuery: SparqlQuery<V, R>
    var variables: V
}

private val sparqlQueryLoader = FC<SparqlQueryLoaderProps<SparqlVariables, SparqlResponse>> { props ->
    val queryResult = useSparqlQuery(props.sparqlQuery, props.variables)
    if (queryResult != null) {
        val element = cloneElement(
            Children.only(props.children),
            jso<SparqlQueryConsumerProps> { this.queryResult = queryResult })
        child(element)
    }
}

