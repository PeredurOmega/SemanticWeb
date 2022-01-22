package cityPage

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import sparql.GetCityResponse
import sparql.SparqlQueryConsumerProps
import sparql.placeholder
import sparql.whenNotBlank

external interface CityResultProps : SparqlQueryConsumerProps<GetCityResponse> {
    var cityUri: String
}

val cityResult = FC<CityResultProps> { props ->
    val searchResult = props.queryResult
    div {
        className = "card-result"
        div {
            div {
                cityHeader {
                    queryResult = searchResult
                }
                div {
                    detailedCityInfo {
                        queryResult = searchResult
                    }
                }
            }
        }
    }
}

private val cityHeader = FC<SparqlQueryConsumerProps<GetCityResponse>> { props ->
    val searchResult = props.queryResult
    div {
        searchResult.name.whenNotBlank { city ->
            searchResult.countryName.whenNotBlank { country ->
                +"$city, $country"
            }
        }
    }
    div {
        searchResult.communeStatusLabel.whenNotBlank { +it }
    }
    div {
        searchResult.abstract.whenNotBlank { +it }
    }
}

private val detailedCityInfo = FC<SparqlQueryConsumerProps<GetCityResponse>> { props ->
    val searchResult = props.queryResult
    div {
        className = "detailled-info"
        i {
            className = "fas fa-fw fa-calendar-day"
        }
        span {
            +"Code postal: "
        }
        span {
            searchResult.postalCode.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.inseeCode.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.area.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.altitudeMin.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.altitudeMax.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.mayor.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.politicalParty.whenNotBlank { +it } placeholder { +"NC" }
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
            searchResult.district.whenNotBlank { +it } placeholder { +"NC" }
        }
    }
}