import kotlinext.js.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.router.useLocation
import tools.basicSVG
import tools.requireSCSS

external interface PersonResult {
    var firstName : String
    var lastName : String
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
        firstName = "Lucas"
        lastName = "Emile"
        domain = "Informatique"
        imageURL = "https://www.google.fr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Ffr%2Fphotos%2Finformaticien&psig=AOvVaw0ETgoIJllac16sc0-BghDF&ust=1642264312086000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKDprbnVsfUCFQAAAAAdAAAAABAD"
        description = "C'est un homme est un informaticien du 21e siècle"
        title = "Informaticien le plus stylé"
        birthCountry = "Lyon, France"
        universities = listOf("INSA Lyon", "INSA Rennes", "Ecole du Nord")
    }

    div{
        div{
            span{
                +"${person.firstName} ${person.lastName}"
            }
            span{
                +person.domain
            }
            span{
                +person.description
            }
            div{
                basicSVG("briefcase", "Fonction")
                b{
                    +"Titre : "
                }
                span{
                    +person.title
                }
            }
        }
        div{

        }
    }
}