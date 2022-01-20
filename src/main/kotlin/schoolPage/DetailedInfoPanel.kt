package schoolPage

import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import tools.basicSVG
import tools.sparql.GetSchoolInfoResponse

external interface DetailedInfoPanelProps : Props {
    var schoolInfo : GetSchoolInfoResponse
}

val detailedInfoPanel = FC<DetailedInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
    div {
        div {
            basicSVG("GlobeWeb", "Site web", "info-icon")
            span {
                +"Site web : "
            }
            span {
                a {
                    if (!schoolInfo.website.value.isNullOrBlank())  {
                        +schoolInfo.website.value!!
                        href = schoolInfo.website.value!!
                    }
                    else if (!schoolInfo.homepage.value.isNullOrBlank()) {
                        +schoolInfo.homepage.value!!
                        href = schoolInfo.homepage.value!!
                    }
                    target = AnchorTarget._blank

                }
            }
        }
        div {
            basicSVG("MapMarker", "Localisation", "info-icon")
            span {
                +"Localisation : "
            }
            span {
                if (!schoolInfo.citylabel.value.isNullOrBlank()) +schoolInfo.citylabel.value!!
                +", "
                if (!schoolInfo.state.value.isNullOrBlank()) +schoolInfo.state.value!!
                else if (!schoolInfo.country.value.isNullOrBlank()) +schoolInfo.country.value!!
            }
        }
        div {
            basicSVG("CalendarEvent", "Date de création", "info-icon")
            span {
                +"Date de création : "
            }
            span {
                if (!schoolInfo.established.value.isNullOrBlank()) +schoolInfo.established.value!!
            }
        }
        div {
            basicSVG("EuroSymbol", "Dotation", "info-icon")
            span {
                +"Dotation : "
            }
            span {
                if (!schoolInfo.endowment.value.isNullOrBlank()) +schoolInfo.endowment.value!!
            }
        }
        div {
            basicSVG("AdministrativeDocument", "Personnel administratif", "info-icon")
            span {
                +"Personnel administratif : "
            }
            span {
                if (!schoolInfo.administrativeStaff.value.isNullOrBlank()) +schoolInfo.administrativeStaff.value!!
            }
        }
        div {
            basicSVG("Briefcase", "Fonction", "info-icon")
            span {
                +"Directeur : "
            }
            span {
                if (!schoolInfo.president.value.isNullOrBlank()) +schoolInfo.president.value!!
            }
        }
        div {
            basicSVG("Teacher", "Nombre d'enseignants", "info-icon")
            span {
                +"Nombre d'enseignants : "
            }
            span {
                if (!schoolInfo.academicStaff.value.isNullOrBlank()) +schoolInfo.academicStaff.value!!
            }
        }
        div {
            basicSVG("Student", "Nombre d'étudiants", "info-icon")
            span {
                +"Nombre d'étudiants : "
            }
            span {
                if (!schoolInfo.students.value.isNullOrBlank()) +schoolInfo.students.value!!
                else if (!schoolInfo.numberOfStudents.value.isNullOrBlank()) +schoolInfo.numberOfStudents.value!!
                else if (!schoolInfo.undergrad.value.isNullOrBlank()) +schoolInfo.undergrad.value!!
            }
        }
        div {
            basicSVG("GraduateStudent", "Nombre de doctorants", "info-icon")
            span {
                +"Nombre de doctorants : "
            }
            span {
                if (!schoolInfo.doctoral.value.isNullOrBlank()) +schoolInfo.doctoral.value!!
                else if (!schoolInfo.numberOfDoctoralStudents.value.isNullOrBlank()) +schoolInfo.numberOfDoctoralStudents.value!!
            }
        }
    }

}