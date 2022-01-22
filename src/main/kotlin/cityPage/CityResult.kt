package cityPage

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import tools.sparql.GetCityResponse
import tools.sparql.SparqlQueryConsumerProps

external interface CityResultProps : SparqlQueryConsumerProps<GetCityResponse> {
    var cityUri : String
}

val cityResult = FC<CityResultProps> { props ->
    val searchResult = props.queryResult
    div {
        className = "card-result"
        div {
            div {
                div {
                    if (!searchResult.name?.value.isNullOrBlank() && !searchResult.countryName?.value.isNullOrBlank()) {
                        +"${searchResult.name!!.value}, ${searchResult.countryName!!.value}"
                    }
                }
                div {
                    if (!searchResult.communeStatusLabel?.value.isNullOrBlank()) {
                        +searchResult.communeStatusLabel!!.value
                    }
                }
                div {
                    if (!searchResult.abstract?.value.isNullOrBlank()) {
                        +searchResult.abstract!!.value
                    }
                }
                div {
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Code postal: "
                        }
                        span {
                            if (!searchResult.postalCode?.value.isNullOrBlank()) +searchResult.postalCode?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Code commune INSEE: "
                        }
                        span {
                            if (!searchResult.inseeCode?.value.isNullOrBlank()) +searchResult.inseeCode?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Superficie :"
                        }
                        span {
                            if (!searchResult.area?.value.isNullOrBlank()) +searchResult.area?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Altitude min.: "
                        }
                        span {
                            if (!searchResult.altitudeMin?.value.isNullOrBlank()) +searchResult.altitudeMin?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Altitude max.: "
                        }
                        span {
                            if (!searchResult.altitudeMax?.value.isNullOrBlank())  +searchResult.altitudeMax?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Maire: "
                        }
                        span {
                            if (!searchResult.mayor?.value.isNullOrBlank())  +searchResult.mayor?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Parti politique: "
                        }
                        span {
                            if (!searchResult.politicalParty?.value.isNullOrBlank())  +searchResult.politicalParty?.value!!
                            else +"NC"
                        }
                    }
                    div {
                        className = "detailled-info"
                        i {
                            className = "fas fa-fw fa-calendar-day"
                        }
                        span {
                            +"Arrondissement: "
                        }
                        span {
                            if (!searchResult.district?.value.isNullOrBlank())  +searchResult.district?.value!!
                            else +"NC"
                        }
                    }
                }
            }
        }
    }
}