package search

import city.CityPageLocationState
import kotlinext.js.jso
import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import react.useContext
import react.useEffect
import school.SchoolPageLocationState
import tools.map.*
import kotlin.js.json

external interface MapResultProps : Props {
    var expectedCount: Int
}

val mapResult = FC<MapResultProps> { props ->
    val mapCoordinates = useContext(MapCoordinatesContext)
    div {
        className = "map-results"
        mapContainer {
            id = "map"
            if (props.expectedCount == 1) {
                center = json("lat" to "46.71", "lng" to "1.72")
                zoom = 5
            }
            tileSize = 1

            if (props.expectedCount != 1) {
                MapPositioner {
                    expectedCount = props.expectedCount
                }
            }

            tileLayer {
                attribution = "Grandes Écoles"
                url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
            }

            mapCoordinates.filter { it.coordinates != null }.forEach {
                val (lat, lng) = it.coordinates!!.split(" ")
                marker {
                    icon = icon(
                        json(
                            "iconUrl" to "images/Marker.svg",
                            "iconRetinaUrl" to "images/Marker.svg",
                            "iconAnchor" to arrayOf(35, 70),
                            "popupAnchor" to arrayOf(0, -55),
                            "iconSize" to arrayOf(70, 70)
                        )
                    )
                    position = json("lat" to lat, "lng" to lng)
                    markerPopup {
                        popupText = it.popupText
                        cityName = it.cityName
                        countryName = it.countryName
                        schoolUri = it.schoolUri
                        cityUri = it.cityUri
                    }
                }
            }
        }
    }
}

private val MapPositioner = FC<MapResultProps> { props ->
    val map = useMap()
    val mapCoordinates = useContext(MapCoordinatesContext)
    useEffect(mapCoordinates) {
        val timeout = window.setTimeout({
            val bounds = latLngBounds(mapCoordinates.filter { it.coordinates != null }.map {
                val (lat, lng) = it.coordinates!!.split(" ")
                latLng(lat.toDouble(), lng.toDouble())
            }.toTypedArray(), jso {
                padding = point(5.0, 5.0)
            })
            map.fitBounds(bounds)
            map.invalidateSize(false)
        }, if (mapCoordinates.size == props.expectedCount) 0 else 500)

        cleanup {
            window.clearTimeout(timeout)
        }
    }
}

private external interface MarkerPopupProps : Props {
    var popupText: String
    var cityName: String?
    var countryName: String?
    var schoolUri: String?
    var cityUri: String?
}

private val markerPopup = FC<MarkerPopupProps> { props ->
    popup {
        div {
            className = "marker-popup"
            if (props.schoolUri != null) {
                Link {
                    this.to = "/school"
                    this.state = jso<SchoolPageLocationState> { schoolUri = props.schoolUri }
                    span {
                        +props.popupText
                    }
                }
            } else {
                span {
                    +props.popupText
                }
            }

            br { }

            if (props.cityUri != null && props.cityName != null) {
                Link {
                    this.to = "/city"
                    this.state = jso<CityPageLocationState> { cityUri = props.cityUri }
                    span {
                        +"${props.cityName}"
                        if (props.countryName != null) +", ${props.countryName}"
                    }
                }
            } else if (props.cityName != null) {
                span {
                    +"${props.cityName}"
                    if (props.countryName != null) +", ${props.countryName}"
                }
            }
        }
    }
}