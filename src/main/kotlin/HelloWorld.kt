import react.*
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2

external interface HelloWorldProps : Props {
    var name: String
}

val helloWorld = FC<HelloWorldProps> { props ->
    h1 {
        +"Hello world !"
    }
    h2 {
        +"And welcome ${props.name}"
    }
}
