package schoolPage

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span
import tools.useWikipediaScrapper

val SmallPersonCard = FC<Props> {
        //props ->
//    val personInfo = props.personGeneralInfo
//    div {
//        img {
//            if (!personInfo.thumbnail.value.isNullOrBlank()) src = personInfo.thumbnail.value!!
//            else {
//                val schoolImagesUri = useWikipediaScrapper("Niklas_Höhne", 1)
//                if (schoolImagesUri.isNotEmpty()) src = schoolImagesUri[0]
//                else src = ""
//            }
//        }
//        //TODO Gérer l'incrustation de la photo si elle existe dans le thumbnail et sinon mettre une photo par défaut
//        span {
//            if (!personInfo.nameFoaf.value.isNullOrBlank()) +personInfo.nameFoaf.value!!
//            else if (!personInfo.nameDbp.value.isNullOrBlank()) +personInfo.nameDbp.value!!
//            else if (!personInfo.label.value.isNullOrBlank()) +personInfo.label.value
//        }
//    }
}