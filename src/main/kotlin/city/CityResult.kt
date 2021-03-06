package city

import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.i
import react.dom.html.ReactHTML.span
import react.useContext
import react.useEffectOnce
import search.Coordinates
import search.MapCoordinatesSetterContext
import sparql.GetCityResponse
import sparql.SparqlQueryConsumerProps
import sparql.placeholder
import sparql.whenNotBlank
import tools.cleanPageName

external interface CityResultProps : SparqlQueryConsumerProps<GetCityResponse> {
    var cityUri: String
}

val cityResult = FC<CityResultProps> { props ->
    val setCoordinates = useContext(MapCoordinatesSetterContext)
    useEffectOnce {
        val coordinates = props.queryResult.coordinates?.value
        val primaryText = props.queryResult.name?.value
        val primaryUri = null
        val secondaryText = props.queryResult.countryName?.value
        val secondaryUri = null

        setCoordinates?.invoke(
            mutableListOf(
                Coordinates(
                    coordinates,
                    primaryText,
                    primaryUri,
                    secondaryText,
                    secondaryUri
                )
            )
        )
    }

    val searchResult = props.queryResult
    div {
        className = "city-info"
        cityHeader {
            queryResult = searchResult
        }
        hr { }
        detailedCityInfo {
            queryResult = searchResult
        }
    }
}

private val cityHeader = FC<SparqlQueryConsumerProps<GetCityResponse>> { props ->
    val searchResult = props.queryResult
    div {
        className = "general-info"
        div {
            className = "city-name"
            searchResult.name.whenNotBlank { city ->
                searchResult.countryName.whenNotBlank { country ->
                    +"$city, $country"
                }
            }
        }
        div {
            className = "city-status"
            searchResult.communeStatusLabel.whenNotBlank { +it }
        }
        div {
            className = "city-description"
            searchResult.abstract.whenNotBlank { +it }
        }
    }
}

private val detailedCityInfo = FC<SparqlQueryConsumerProps<GetCityResponse>> { props ->
    val searchResult = props.queryResult
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
                    +"Maire : "
                }
                span {
                    searchResult.mayor.whenNotBlank {
                        +cleanPageName(it, listOf("http://dbpedia.org/resource/"), true)
                    } placeholder { +"NC" }
                }
            }
            div {
                className = "raw-info"
                i {
                    className = "fas fa-fw fa-handshake"
                }
                span {
                    +"Parti politique : "
                }
                span {
                    searchResult.politicalParty.whenNotBlank { +it } placeholder { +"NC" }
                }
            }
            div {
                className = "raw-info"
                i {
                    className = "fas fa-fw fa-map-signs"
                }
                span {
                    +"Code postal : "
                }
                span {
                    searchResult.postalCode.whenNotBlank { +it } placeholder { +"NC" }
                }
            }
            div {
                className = "raw-info"
                i {
                    className = "fas fa-fw fa-street-view"
                }
                span {
                    +"Arrondissement : "
                }
                span {
                    searchResult.district.whenNotBlank { +it } placeholder { +"NC" }
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
                    +"Code commune INSEE : "
                }
                span {
                    searchResult.inseeCode.whenNotBlank { +it } placeholder { +"NC" }
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
                    searchResult.area.whenNotBlank { +it } placeholder { +"NC" }
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
                    searchResult.altitudeMax.whenNotBlank { +it } placeholder { +"NC" }
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
                    searchResult.altitudeMin.whenNotBlank { +it } placeholder { +"NC" }
                }
            }
        }
    }
}