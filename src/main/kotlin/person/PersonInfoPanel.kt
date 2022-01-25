package person

import kotlinext.js.jso
import react.FC
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import sparql.*

external interface PersonInfoPanelProps : SparqlQueryConsumerProps<GetPersonInfoResponse> {
    var personUri: String
}

val personInfoPanel = FC<PersonInfoPanelProps> { props ->
    val personInfo = props.queryResult
    div {
        className = "attribute-person-div"
        personHeader {
            queryResult = personInfo
        }
        hr { }
        personDetails {
            queryResult = personInfo
            personUri = props.personUri
        }
    }
}

private val personHeader = FC<SparqlQueryConsumerProps<GetPersonInfoResponse>> { props ->
    val personInfo = props.queryResult
    div {
        className = "general-info"
        span {
            personInfo.labelen.whenNotBlank { +it }
        }
        span {
            personInfo.domain.whenNotBlank { +it } orElse {
                personInfo.titles.whenNotBlank { +it }
            } orElse {
                personInfo.titlelab.whenNotBlank { +it }
            }
        }
        span {
            personInfo.descriptionfr.whenNotBlank { +it } orElse {
                personInfo.descriptionen.whenNotBlank { +it }
            } placeholder {
                +"Aucune description"
            }
        }
    }
}

private val personDetails = FC<PersonInfoPanelProps> { props ->
    val personInfo = props.queryResult
    div {
        className = "icons-text-person"
        div {
            className = "icon-text-person"
            i {
                className = "fas fa-fw fa-globe"
            }
            span {
                +"Site web : "
            }
            span {
                personInfo.wiki.whenNotBlank {
                    a {
                        +it
                        href = it
                        target = AnchorTarget._blank
                    }
                } placeholder { +"NC" }
            }
        }
        div {
            className = "icon-text-person"

            i {
                className = "fas fa-fw fa-briefcase"
            }
            span {
                +"Titre : "
            }
            span {
                personInfo.titles.whenNotBlank { +it } orElse {
                    personInfo.titlelab.whenNotBlank { +it }
                } orElse {
                    personInfo.domain.whenNotBlank { +it }
                } placeholder { +"NC" }
            }
        }
        div {
            className = "icon-text-person"
            i {
                className = "fas fa-fw fa-map-marker-alt"
            }
            span {
                +"Lieu de naissance : "
            }
            span {
                personInfo.cityconcat.whenNotBlank { +it.replace("Franca,", "") } orElse {
                    personInfo.cityen.whenNotBlank { +it.replace("Franca,", "") }
                } placeholder { +"NC" }
            }
        }
        div {
            className = "icon-text-person"
            div {
                i {
                    className = "fas fa-fw fa-user-graduate"
                }
                span {
                    +"Parcours universitaire : "
                }
            }
            div {
                sparqlQueryLoaderMultiple(getPersonUniversities, jso { uri = props.personUri }) {
                    personUniversitiesPanel {
                        this.personUri = props.personUri
                    }
                }
            }
        }
    }
}
