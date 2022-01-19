package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import tools.sparql.GetSchoolInfoResponse

external interface SchoolInfoPanelProps : Props {
    var schoolInfo : GetSchoolInfoResponse
}

val schoolInfoPanel = FC<SchoolInfoPanelProps> { props ->
    div {
        generalInfoPanel {
            this.schoolInfo = props.schoolInfo
        }
        detailedInfoPanel {
            this.schoolInfo = props.schoolInfo
        }
    }
}