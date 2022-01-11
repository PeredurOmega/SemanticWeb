import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import tools.requireSCSS

external interface HelloWorldProps : Props {
    var name: String
}

val helloWorld = FC<HelloWorldProps> { props ->
    requireSCSS("app")
    h1 {
        +"Hello world !"
    }
    div {
        h2 {
            +"And welcome ${props.name}"
        }
    }
}

