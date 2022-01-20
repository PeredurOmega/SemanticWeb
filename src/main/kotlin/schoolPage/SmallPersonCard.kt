package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span
import tools.imageDir
import tools.sparql.GetPersonGeneralInfoResponse
import tools.useWikipediaScrapper

external interface SmallPersonCardPersonProps : Props {
    var personInfo : GetPersonGeneralInfoResponse
}

val SmallPersonCard = FC<SmallPersonCardPersonProps> { props ->
    val personInfo = props.personInfo
    div {
        if (!personInfo.thumbnail?.value.isNullOrBlank()) {
            img {
                src =  personInfo.thumbnail!!.value
            }
        } else wikipediaPhoto {
            this.personUri = props.personInfo.person.value
        }
        //TODO Gérer l'incrustation de la photo si elle existe dans le thumbnail et sinon mettre une photo par défaut
        span {
            if (!personInfo.nameFoaf?.value.isNullOrBlank()) +personInfo.nameFoaf!!.value
            else if (!personInfo.nameDbp?.value.isNullOrBlank()) +personInfo.nameDbp!!.value
            else if (!personInfo.label.value.isBlank()) +personInfo.label.value
        }
    }
}

external interface WikipediaPhotoProps  : Props {
    var personUri : String
}

private val wikipediaPhoto = FC<WikipediaPhotoProps> { props ->
    val schoolImagesUri = useWikipediaScrapper(props.personUri, 1)
    println(props.personUri)
    img {
        src = if (schoolImagesUri.isNotEmpty()) schoolImagesUri[0]
        else "${imageDir}Anonyme.png"
    }
}

