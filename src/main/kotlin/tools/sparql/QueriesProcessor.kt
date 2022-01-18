package tools.sparql

import kotlinext.js.jso
import react.*

fun <V: SparqlVariables, R: SparqlResponse> useSparqlQuery(sparqlQuery : SparqlQuery<V, R>, variables: V): R? {
    val (response, setResponse) = useState<R?>(null)

    useEffectOnce {
        sparqlQuery.execute(variables, setResponse)
    }

    return response
}

external interface SparqlQueryLoaderProps : PropsWithChildren {
    var sparqlQuery : SparqlQuery<*,*>
    var variables : SparqlVariables
}

external interface SparqlQueryConsumerProps : Props {
    var queryResult : SparqlResponse
}

val sparqlQueryLoader = FC<SparqlQueryLoaderProps> { props ->
    val queryResult = useSparqlQuery(props.sparqlQuery, props.variables.asDynamic())
    if (queryResult != null ) {
        val element = cloneElement(Children.only(props.children), jso<SparqlQueryConsumerProps>{ this.queryResult = queryResult })
        child(element)
    }
}

