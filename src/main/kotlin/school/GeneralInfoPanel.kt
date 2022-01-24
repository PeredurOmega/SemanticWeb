package school

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import sparql.GetSchoolInfoResponse
import sparql.orElse
import sparql.placeholder
import sparql.whenNotBlank
import tools.cleanPageName

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
                schoolInfo.nameFoaf.whenNotBlank { +cleanPageName(it, listOf("http://dbpedia.org/resource/")) } orElse {
                    schoolInfo.nameDbp.whenNotBlank { +cleanPageName(it, listOf("http://dbpedia.org/resource/")) }
                } placeholder { +schoolInfo.label.value }
            }
        }
        div {
            className = "school-motto"
            span {
                schoolInfo.motto.whenNotBlank { +it }
            }
        }
        div {
            className = "school-description"
            span {
                schoolInfo.comment.whenNotBlank { +it } orElse {
                    schoolInfo.abstract.whenNotBlank { +it }
                }
            }
        }
    }
}