package schoolPage

import kotlinext.js.jso
import person.PersonPageLocationState
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import sparql.GetPersonGeneralInfoResponse
import sparql.orElse
import sparql.placeholder
import sparql.whenNotBlank
import tools.wikipediaPhoto

external interface SmallPersonCardPersonProps : Props {
    var personInfo: GetPersonGeneralInfoResponse
}

val SmallPersonCard = FC<SmallPersonCardPersonProps> { props ->
    val personInfo = props.personInfo
    Link {
        this.to = "/person"
        this.state = jso<PersonPageLocationState> { personUri = personInfo.person.value }
        div {
            div {
                personInfo.thumbnail.whenNotBlank {
                    img {
                        src = personInfo.thumbnail!!.value
                    }
                } placeholder {
                    wikipediaPhoto {
                        this.uri = props.personInfo.person.value
                        this.type = "person"
                    }
                }
            }
            span {
                personInfo.nameFoaf.whenNotBlank { +it } orElse {
                    personInfo.nameDbp.whenNotBlank { +it }
                } placeholder {
                    +personInfo.label.value
                }
            }
        }
    }
}


