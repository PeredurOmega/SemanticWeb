package schoolPage

import cityPage.CityPageLocationState
import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import tools.cleanPageName
import sparql.GetSchoolInfoResponse
import kotlin.math.pow

external interface DetailedInfoPanelProps : Props {
    var schoolInfo: GetSchoolInfoResponse
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
                    if (!schoolInfo.website?.value.isNullOrBlank()) {
                        +schoolInfo.website?.value!!
                        href = schoolInfo.website?.value!!
                    } else if (!schoolInfo.homepage?.value.isNullOrBlank()) {
                        +schoolInfo.homepage?.value!!
                        href = schoolInfo.homepage?.value!!
                    } else +"NC"
                    target = AnchorTarget._blank
                }
            }
        }
        div {
            div {
                if (!schoolInfo.cityName?.value.isNullOrBlank()) {
                    Link {
                        this.to = "/city"
                        this.state = jso<CityPageLocationState> { cityUri = schoolInfo.cityUrl?.value!! }
                        div {
                            className = "detailled-info"
                            i {
                                className = "fas fa-fw fa-map-marked-alt"
                            }
                            span {
                                +"Localisation : "
                            }
                            span {
                                if (!schoolInfo.cityName?.value.isNullOrBlank()) +(cleanPageName(schoolInfo.cityName?.value!!,listOf("http://dbpedia.org/resource/")) + ", ")
                                if (!schoolInfo.countryName?.value.isNullOrBlank()) +cleanPageName(schoolInfo.countryName?.value!!, listOf("http://dbpedia.org/resource/"))
                                else +"France"
                            }
                        }
                    }
                } else {
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-map-marked-alt"
                        }
                        span {
                            +"Localisation :"
                        }
                        span {
                            +"France"
                        }
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
                        if (!schoolInfo.endowment?.value.isNullOrBlank()) +(scientificStrToIntStr(schoolInfo.endowment?.value!!) + " €")
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
                        if (!schoolInfo.president?.value.isNullOrBlank()) +cleanPageName(schoolInfo.president?.value!!, listOf("http://dbpedia.org/resource/"))
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

private fun scientificStrToIntStr(strNumber: String): String {
    val parts = strNumber.split("E", ignoreCase = true)
    return if (parts.size != 2) {
        strNumber
    } else {
        val str = (parts[0].toDouble() * (10.0.pow(parts[1].toDouble()))).toInt().toString()
        str.reversed().chunked(3).joinToString(separator = " ").reversed()
    }
}
