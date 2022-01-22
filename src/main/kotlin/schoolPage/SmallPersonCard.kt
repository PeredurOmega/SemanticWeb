package schoolPage

import person.PersonPageLocationState
import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import tools.sparql.GetPersonGeneralInfoResponse
import tools.wikipediaPhoto

external interface SmallPersonCardPersonProps : Props {
    var personInfo : GetPersonGeneralInfoResponse
}

val SmallPersonCard = FC<SmallPersonCardPersonProps> { props ->
    val personInfo = props.personInfo
    Link {
        this.to = "/person"
        this.state = jso<PersonPageLocationState> { personUri = personInfo.person.value }
        div {
            div {
                if (!personInfo.thumbnail?.value.isNullOrBlank()) {
                    img {
                        src =  personInfo.thumbnail!!.value
                    }
                } else wikipediaPhoto {
                    this.uri = props.personInfo.person.value
                    this.type = "person"
                }
            }
            span {
                if (!personInfo.nameFoaf?.value.isNullOrBlank()) +personInfo.nameFoaf!!.value
                else if (!personInfo.nameDbp?.value.isNullOrBlank()) +personInfo.nameDbp!!.value
                else if (!personInfo.label.value.isBlank()) +personInfo.label.value
            }
        }
    }
}


