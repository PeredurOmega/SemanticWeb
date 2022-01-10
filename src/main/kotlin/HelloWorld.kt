import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2

external interface HelloWorldProps : Props {
    var name: String
}

val helloWorld = FC<HelloWorldProps> { props ->
    val (count, setCount) = useState("Test")
    val (count2, setCount2) = useState<Int?>(0)
    /*val (result, loaded, loadQuery) = useQuery(SchoolSearchQuery())
    if (!loaded) {
        loadQuery()
    } else {
        result.schoolName
    }*/
    h1 {
        +"Hello world !"
    }
    div {
        className = "main-view"
        h2 {
            +"And welcome ${props.name}"
        }
    }
    button {
        onClick = {
            setCount(count + 1)
        }
        +"Increment $count"
    }
    /*useEffect {
        if (count == "Test" && count2 == 3) {

        }
    }
    count2?.let {
        h2 {
            it
        }
    }
    val result = count2?.times(2)
    count2?.let {
        val result = it * 2
    }
    if (count2 == 0) {
        list {

        }
    }*/
}

val list = FC<Props> {
    val (elements, setElements) = useState(listOf("", "", ""))
}

/*
abstract class Query<V, R> {
    val query =
}

class SchoolQuery: Query<SchoolQueryVariables, SchoolQueryResponse>
*/