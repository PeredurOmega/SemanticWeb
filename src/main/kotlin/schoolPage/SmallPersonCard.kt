package schoolPage

import PersonPageLocationState
import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.link
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import tools.imageDir
import tools.sparql.GetPersonGeneralInfoResponse
import tools.useWikipediaScrapper

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
                    this.personUri = props.personInfo.person.value
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

external interface WikipediaPhotoProps  : Props {
    var personUri : String
}

private val wikipediaPhoto = FC<WikipediaPhotoProps> { props ->
    val schoolImagesUri = useWikipediaScrapper(props.personUri, 1)
    img {
        src = if (schoolImagesUri.isNotEmpty()) schoolImagesUri[0]
        else "${imageDir}PersonPlaceholder.png"
    }
}

