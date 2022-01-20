package schoolPage

import kotlinext.js.jso
import react.ConsumerProps
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import tools.sparql.GetSchoolInfoResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.sparql.getSchoolInfo
import tools.sparql.sparqlQueryLoaderSingle

external interface SchoolInfoPanelProps : Props {
    var schoolUri : String
}

val schoolInfoPanel = FC<SchoolInfoPanelProps> { props ->
    div {
        sparqlQueryLoaderSingle(getSchoolInfo, jso { uri = props.schoolUri }) {
            infoPanelWrapper {

            }
        }
    }
}

external interface InfoPanelWrapperProps : SparqlQueryConsumerProps<GetSchoolInfoResponse>

private val infoPanelWrapper = FC<InfoPanelWrapperProps> { props ->
    generalInfoPanel {
        this.schoolInfo = props.queryResult
    }
    hr { }
    detailedInfoPanel {
        this.schoolInfo = props.queryResult
    }
}