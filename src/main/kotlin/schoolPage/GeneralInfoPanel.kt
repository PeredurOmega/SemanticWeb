package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import tools.cleanPageName
import tools.sparql.GetSchoolInfoResponse

external interface GeneralInfoPanelProps : Props {
    var schoolInfo: GetSchoolInfoResponse
}

val generalInfoPanel = FC<GeneralInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
    div {
        className = "general-info"
        div {
            className = "school-name"
            span {
                if (!schoolInfo.nameFoaf?.value.isNullOrBlank()) +cleanPageName(schoolInfo.nameFoaf?.value!!, listOf("http://dbpedia.org/resource/"))
                else if (!schoolInfo.nameDbp?.value.isNullOrBlank()) +cleanPageName(schoolInfo.nameDbp?.value!!, listOf("http://dbpedia.org/resource/"))
                else +schoolInfo.label.value
            }
        }
        div {
            className = "school-motto"
            span {
                if (!schoolInfo.motto?.value.isNullOrBlank()) +schoolInfo.motto?.value!!
            }
        }
        div {
            className = "school-description"
            span {
                if (!schoolInfo.comment?.value.isNullOrBlank()) +schoolInfo.comment?.value!!
                else if (!schoolInfo.abstract?.value.isNullOrBlank()) +schoolInfo.abstract?.value!!
            }
        }
    }
}