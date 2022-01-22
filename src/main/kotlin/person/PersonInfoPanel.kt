package person

import kotlinext.js.jso
import react.FC
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import tools.cleanPageName
import tools.sparql.GetPersonInfoResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.sparql.getPersonUniversities
import tools.sparql.sparqlQueryLoaderMultiple

external interface PersonInfoPanelProps : SparqlQueryConsumerProps<GetPersonInfoResponse> {
    var personUri: String
}

val personInfoPanel = FC<PersonInfoPanelProps> { props ->
    div {
        className = "attribute-person-div"
        div {
            className = "general-info"
            span {
                if (!props.queryResult.labelen?.value.isNullOrBlank()) +props.queryResult.labelen?.value!!
            }
            span {
                if (!props.queryResult.domain?.value.isNullOrBlank()) +props.queryResult.domain?.value!!
                else if (!props.queryResult.titles?.value.isNullOrBlank()) +props.queryResult.titles?.value!!
                else if (!props.queryResult.titlelab?.value.isNullOrBlank()) +props.queryResult.titlelab?.value!!
                else +""
            }
            span {
                if (!props.queryResult.descriptionfr?.value.isNullOrBlank()) +props.queryResult.descriptionfr?.value!!
                else if (!props.queryResult.descriptionen?.value.isNullOrBlank()) +props.queryResult.descriptionen?.value!!
                else +"Aucune description"
            }
        }
        hr { }
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
                    if (!props.queryResult.wiki?.value.isNullOrBlank()) {
                        a {
                            +props.queryResult.wiki?.value!!
                            href = props.queryResult.wiki?.value!!
                            target = AnchorTarget._blank
                        }
                    } else +"NC"
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
                    if (!props.queryResult.titles?.value.isNullOrBlank()) +props.queryResult.titles?.value!!
                    else if (!props.queryResult.titlelab?.value.isNullOrBlank()) +props.queryResult.titlelab?.value!!
                    else if (!props.queryResult.domain?.value.isNullOrBlank()) +props.queryResult.domain?.value!!
                    else +"NC"
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
                    if (!props.queryResult.cityconcat?.value.isNullOrBlank()) +props.queryResult.cityconcat?.value!!.removePrefix("Franca, ")
                    else if (!props.queryResult.cityen?.value.isNullOrBlank()) +props.queryResult.cityen?.value!!.removePrefix("Franca, ")
                    else +"NC"
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
}