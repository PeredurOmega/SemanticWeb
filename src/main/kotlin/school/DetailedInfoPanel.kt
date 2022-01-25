package school

import city.CityPageLocationState
import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import sparql.*
import tools.cleanPageName
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
                    schoolInfo.website.whenNotBlank {
                        href = it
                        +it
                    } orElse {
                        schoolInfo.homepage.whenNotBlank {
                            href = it
                            +it
                        }
                    } placeholder { +"NC" }
                    target = AnchorTarget._blank
                }
            }
        }
        div {
            schoolDetails1 {
                this.schoolInfo = schoolInfo
            }
            schoolDetails2 {
                this.schoolInfo = schoolInfo
            }
        }
    }
}

private external interface SchoolLocationProps : Props {
    var cityName: SparqlValue<String>?
    var cityUri: SparqlValue<String>?
    var countryName: SparqlValue<String>?
}

private val schoolLocation = FC<SchoolLocationProps> { props ->
    div {
        className = "detailled-info"
        i {
            className = "fas fa-fw fa-map-marked-alt"
        }
        span {
            +"Localisation : "
        }
        props.cityUri.whenNotBlank { cityUri ->
            Link {
                this.to = "/city"
                this.state = jso<CityPageLocationState> { this.cityUri = cityUri }
                span {
                    props.cityName.whenNotBlank { cityName ->
                        +cleanPageName(cityName, listOf("http://dbpedia.org/resource/"), true)
                        props.countryName.whenNotBlank { country ->
                            +", "
                            +cleanPageName(country, listOf("http://dbpedia.org/resource/"), true)
                        }
                    } placeholder { +"France" }
                }
            }
        } placeholder {
            span {
                +"France"
            }
        }
    }
}

private val schoolDetails1 = FC<DetailedInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
    div {
        schoolLocation {
            cityName = schoolInfo.cityName
            countryName = schoolInfo.countryName
            cityUri = schoolInfo.cityUrl
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
                schoolInfo.established.whenNotBlank { +it } placeholder { +"NC" }
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
                schoolInfo.endowment.whenNotBlank { +"${scientificStrToIntStr(it)} €" } placeholder { +"NC" }
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
                schoolInfo.administrativeStaff.whenNotBlank { +scientificStrToIntStr(it) } placeholder { +"NC" }
            }
        }
    }
}

private val schoolDetails2 = FC<DetailedInfoPanelProps> { props ->
    val schoolInfo = props.schoolInfo
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
                schoolInfo.president.whenNotBlank {
                    +cleanPageName(it, listOf("http://dbpedia.org/resource/"), true)
                } placeholder { +"NC" }
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
                schoolInfo.academicStaff.whenNotBlank { +it } placeholder { +"NC" }
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
                schoolInfo.students.whenNotBlank { +it } orElse {
                    schoolInfo.numberOfStudents.whenNotBlank { +it }
                } orElse {
                    schoolInfo.undergrad.whenNotBlank { +it }
                } placeholder {
                    +"NC"
                }
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
                schoolInfo.doctoral.whenNotBlank { +it } orElse {
                    schoolInfo.numberOfDoctoralStudents.whenNotBlank { +it }
                } placeholder {
                    +"NC"
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
