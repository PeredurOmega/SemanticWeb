package tools.sparql

import react.*
import react.dom.html.ReactHTML.div

val ProgressBarContext = createContext<StateSetter<Boolean>>()

val progressBarContextProvider = FC<PropsWithChildren> { props ->
    val (showProgressBar, setShowProgressBar) = useState(false)
    ProgressBarContext.Provider(setShowProgressBar) {
        props.children()
    }
    if (showProgressBar) progressBar { }
}

fun useProgressBar(inProgress: Boolean, use: Boolean? = true) {
    val displayProgressBar = useContext(ProgressBarContext)
    useEffect(inProgress, use) {
        if (use == true) {
            displayProgressBar(inProgress)
            cleanup {
                displayProgressBar(false)
            }
        }
    }
}

private val progressBar = FC<Props> {
    div {
        className = "progress-bar"
        div {
            className = "line"
        }
        div {
            className = "subline inc"
        }
        div {
            className = "subline dex"
        }
    }
}

