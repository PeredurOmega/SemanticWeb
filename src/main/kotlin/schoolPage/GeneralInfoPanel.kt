package schoolPage

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span


val generalInfoPanel = FC<SchoolInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
    div {
        div {
            className = "school-name"
            span {
                if (!schoolInfo.nameFoaf.value.isNullOrBlank()) +schoolInfo.nameFoaf.value!!
                else if (!schoolInfo.nameDbp.value.isNullOrBlank()) +schoolInfo.nameDbp.value!!
            }
        }
        div {
            className = "school-motto"
            span {
                if (!schoolInfo.motto.value.isNullOrBlank()) +schoolInfo.motto.value!!
            }
        }
        div {
            className = "school-description"
            span {
                if (!schoolInfo.abstract.value.isNullOrBlank()) +schoolInfo.abstract.value!!
                else if (!schoolInfo.comment.value.isNullOrBlank()) +schoolInfo.comment.value!!
            }
        }
    }
}