package cityPage

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.useContext
import react.useEffectOnce
import search.Coordinates
import search.MapCoordinatesSetterContext
import tools.cleanPageName
import tools.sparql.GetCityResponse
import tools.sparql.SparqlQueryConsumerProps

external interface CityResultProps : SparqlQueryConsumerProps<GetCityResponse> {
    var cityUri : String
}

val cityResult = FC<CityResultProps> { props ->
    val setCoordinates = useContext(MapCoordinatesSetterContext)
    useEffectOnce {
        val coordinates = props.queryResult.coordinates?.value ?: "46.71 1.72"
        val schoolName = props.queryResult.name?.value ?: ""
        val cityName = ""
        val countryName = props.queryResult.countryName?.value ?: ""
        val cityUri = ""
        val schoolUri = ""

        setCoordinates?.invoke(
            mutableListOf(
                Coordinates(
                    coordinates,
                    schoolName,
                    cityName,
                    countryName,
                    cityUri,
                    schoolUri
                )
            )
        )
    }

    val searchResult = props.queryResult
    div {
        className = "city-info"
        div {
            className = "general-info"
            div {
                className = "city-name"
                if (!searchResult.name?.value.isNullOrBlank() && !searchResult.countryName?.value.isNullOrBlank()) {
                    +"${searchResult.name!!.value}, ${searchResult.countryName!!.value}"
                }
            }
            div {
                className = "city-status"
                if (!searchResult.communeStatusLabel?.value.isNullOrBlank()) {
                    +searchResult.communeStatusLabel!!.value
                }
            }
            div {
                className = "city-description"
                if (!searchResult.abstract?.value.isNullOrBlank()) {
                    +searchResult.abstract!!.value
                }
            }
        }
        hr { }
        div {
            className = "detailled-info"
            div {
                className = "detailled-info-panel"
                div {
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-user-tie"
                    }
                    span {
                        +"Maire: "
                    }
                    span {
                        if (!searchResult.mayor?.value.isNullOrBlank()) +cleanPageName(searchResult.mayor?.value!!, listOf("http://dbpedia.org/resource/"))
                        else +"NC"
                    }
                }
                div {
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-handshake"
                    }
                    span {
                        +"Parti politique: "
                    }
                    span {
                        if (!searchResult.politicalParty?.value.isNullOrBlank()) +searchResult.politicalParty?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-map-signs"
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
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-street-view"
                    }
                    span {
                        +"Arrondissement: "
                    }
                    span {
                        if (!searchResult.district?.value.isNullOrBlank()) +searchResult.district?.value!!
                        else +"NC"
                    }
                }
            }
            div {
                className = "detailled-info-panel"

                div {
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-city"
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
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-draw-polygon"
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
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-sort-amount-up-alt"
                    }
                    span {
                        +"Altitude max : "
                    }
                    span {
                        if (!searchResult.altitudeMax?.value.isNullOrBlank()) +searchResult.altitudeMax?.value!!
                        else +"NC"
                    }
                }
                div {
                    className = "raw-info"
                    i {
                        className = "fas fa-fw fa-sort-amount-down-alt"
                    }
                    span {
                        +"Altitude min : "
                    }
                    span {
                        if (!searchResult.altitudeMin?.value.isNullOrBlank()) +searchResult.altitudeMin?.value!!
                        else +"NC"
                    }
                }
            }
        }
    }
}