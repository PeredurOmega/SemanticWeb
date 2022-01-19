import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.router.useLocation
import tools.basicSVG
import tools.requireSCSS

external interface PersonResult {
    var label : String
    var domain : String
    var imageURL : String
    var description : String
    var title : String
    var birthCountry : String
    var universities : List<String>
}

val personPage = FC<Props> {
    requireSCSS("person-page")
    //val location = useLocation()
    val person = jso<PersonResult>{
        label = "Lucas Emile"
        domain = "Informatique"
        imageURL = "https://media.istockphoto.com/photos/testing-software-picture-id1017296544?k=20&m=1017296544&s=612x612&w=0&h=I34gSez0j2HGciCztsZH09XiO142NLRmNIyFoJ-0U4M="
        description = "C'est un homme est un informaticien du 21e siècle"
        title = "Informaticien le plus stylé"
        birthCountry = "Lyon, France"
        universities = listOf("INSA Lyon", "INSA Rennes", "Ecole du Nord")
    }

    div{
        className = "main-person-div"
        div{
            className = "attribute-person-div"
            p{
                +"${person.label}"
            }
            p{
                +person.domain
            }
            p{
                +person.description
            }
            div{
                className = "icon-text-person"
                basicSVG("Briefcase", "Fonction", "person-icon")
                span{
                    +"Titre : "
                }
                span{
                    +person.title
                }
            }
            div{
                className = "icon-text-person"
                basicSVG("MapMarker", "Lieu", "person-icon")
                span{
                    +"Lieu de naissance : "
                }
                span{
                    +person.birthCountry
                }
            }
            div{
                className = "icon-text-person"
                basicSVG("GraduateStudent", "Universités", "person-icon")
                span{
                    +"Parcours universitaire : "
                }
                ul{
                    person.universities.forEach { uni ->
                        li{
                            +uni
                        }
                    }
                }
            }
        }
        div{
            className = "img-person"
            img{
                src = person.imageURL
            }
        }
    }
}