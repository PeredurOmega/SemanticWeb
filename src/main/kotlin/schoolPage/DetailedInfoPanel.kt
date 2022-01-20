package schoolPage

import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import tools.basicSVG
import tools.sparql.GetSchoolInfoResponse

external interface DetailedInfoPanelProps : Props {
    var schoolInfo : GetSchoolInfoResponse
}

val detailedInfoPanel = FC<DetailedInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
    div {
        className = "detailled-info-panel"
        div {
            className = "detailled-info"
            i {
                className = "fas fa-fw fa-globe"
            }
            span {
                +"Site web : "
            }
            span {
                a {
                    if (!schoolInfo.website?.value.isNullOrBlank())  {
                        +schoolInfo.website?.value!!
                        href = schoolInfo.website?.value!!
                    }
                    else if (!schoolInfo.homepage?.value.isNullOrBlank()) {
                        +schoolInfo.homepage?.value!!
                        href = schoolInfo.homepage?.value!!
                    }
                    target = AnchorTarget._blank

                }
            }
        }
        div {
            div {
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-map-marked-alt"
                    }
                    span {
                        +"Localisation : "
                    }
                    span {
                        if (!schoolInfo.citylabel?.value.isNullOrBlank()) +schoolInfo.citylabel?.value!!
                        else if (!schoolInfo.location?.value.isNullOrBlank()) +schoolInfo.location?.value!!
                        +", "
                        if (!schoolInfo.state?.value.isNullOrBlank()) +schoolInfo.state?.value!!
                        else if (!schoolInfo.countrylabel?.value.isNullOrBlank()) +schoolInfo.countrylabel?.value!!
                        else +"France"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-calendar-day"
                    }
                    span {
                        +"Date de création : "
                    }
                    span {
                        if (!schoolInfo.established?.value.isNullOrBlank()) +schoolInfo.established?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-hand-holding-usd"
                    }
                    span {
                        +"Dotation : "
                    }
                    span {
                        if (!schoolInfo.endowment?.value.isNullOrBlank()) +schoolInfo.endowment?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-file-alt"
                    }
                    span {
                        +"Personnel administratif : "
                    }
                    span {
                        if (!schoolInfo.administrativeStaff?.value.isNullOrBlank()) +schoolInfo.administrativeStaff?.value!!
                        else +"NC"
                    }
                }
            }
            div {
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-briefcase"
                    }
                    span {
                        +"Directeur : "
                    }
                    span {
                        if (!schoolInfo.president?.value.isNullOrBlank()) +schoolInfo.president?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-chalkboard-teacher"
                    }
                    span {
                        +"Nombre d'enseignants : "
                    }
                    span {
                        if (!schoolInfo.academicStaff?.value.isNullOrBlank()) +schoolInfo.academicStaff?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-user"
                    }
                    span {
                        +"Nombre d'étudiants : "
                    }
                    span {
                        if (!schoolInfo.students?.value.isNullOrBlank()) +schoolInfo.students?.value!!
                        else if (!schoolInfo.numberOfStudents?.value.isNullOrBlank()) +schoolInfo.numberOfStudents?.value!!
                        else if (!schoolInfo.undergrad?.value.isNullOrBlank()) +schoolInfo.undergrad?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "detailled-info"
                    i {
                        className = "fas fa-fw fa-user-graduate"
                    }
                    span {
                        +"Nombre de doctorants : "
                    }
                    span {
                        if (!schoolInfo.doctoral?.value.isNullOrBlank()) +schoolInfo.doctoral?.value!!
                        else if (!schoolInfo.numberOfDoctoralStudents?.value.isNullOrBlank()) +schoolInfo.numberOfDoctoralStudents?.value!!
                        else +"NC"
                    }
                }
            }
        }
    }
}