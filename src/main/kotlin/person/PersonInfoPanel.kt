package person

import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.useEffectOnce
import tools.basicSVG
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
        p {
            if (!props.queryResult.labelen?.value.isNullOrBlank()) +props.queryResult.labelen?.value!!
        }
        p {
            if (!props.queryResult.domain?.value.isNullOrBlank()) +props.queryResult.domain?.value!!
            else if (!props.queryResult.titles?.value.isNullOrBlank()) +props.queryResult.titles?.value!!
            else if (!props.queryResult.titlelab?.value.isNullOrBlank()) +props.queryResult.titlelab?.value!!
            else +""
        }
        p {
            if (!props.queryResult.descriptionfr?.value.isNullOrBlank()) +props.queryResult.descriptionfr?.value!!
            else if (!props.queryResult.descriptionen?.value.isNullOrBlank()) +props.queryResult.descriptionen?.value!!
            else +"Aucune description"
        }
        div {
            span {
                className = "icon-text-person"
                basicSVG("GlobeWeb", "Site", "person-icon")
                span {
                    +"Site web : "
                }
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
            span {
                className = "icon-text-person"
                basicSVG("Briefcase", "Fonction", "person-icon")
                span {
                    +"Titre : "
                }
                span{
                    if (!props.queryResult.titles?.value.isNullOrBlank()) +props.queryResult.titles?.value!!
                    else if (!props.queryResult.titlelab?.value.isNullOrBlank()) +props.queryResult.titlelab?.value!!
                    else if (!props.queryResult.domain?.value.isNullOrBlank()) +props.queryResult.domain?.value!!
                    else +"NC"
                }
            }
        }
        div {
            className = "icon-text-person"
            basicSVG("MapMarker", "Lieu", "person-icon")
            span {
                +"Lieu de naissance : "
            }
            span {
                if (!props.queryResult.cityconcat?.value.isNullOrBlank()) +props.queryResult.cityconcat?.value!!
                else if (!props.queryResult.cityen?.value.isNullOrBlank()) +props.queryResult.cityen?.value!!
                else +"NC"
            }
        }
        div {
            className = "icon-text-person"
            basicSVG("GraduateStudent", "Universités", "person-icon")
            span {
                +"Parcours universitaire : "
            }
            sparqlQueryLoaderMultiple(getPersonUniversities, jso { uri = props.personUri }) {
                personUniversitiesPanel {
                    this.personUri = props.personUri
                }
            }
        }
    }
}