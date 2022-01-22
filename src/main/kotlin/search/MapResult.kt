package search

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import schoolPage.SchoolPageLocationState
import tools.map.*
import kotlin.js.json

val mapResult = FC<Props> {
    val mapCoordinates = useContext(MapCoordinatesContext)
    div {
        className = "map-results"
        mapContainer {
            id = "map"
//            center = json("lat" to "46.71", "lng" to "1.72")
//            zoom = 6
            tileSize = 1

            MapPositioner { }

            tileLayer {
                attribution = "Grandes Écoles"
                url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
            }

            mapCoordinates.forEach {
                val (lat, lng) = it.coordinates.split(" ")
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
                    position = json(
                        "lat" to lat,
                        "lng" to lng
                    )
                    popup {
                        div {
                            className = "marker-popup"
                            Link {
                                this.to = "/school"
                                this.state = jso<SchoolPageLocationState> { schoolUri = it.schoolUri}
                                span {
                                    +it.popupText
                                }
                            }
                            br { }
                            Link {
                                this.to = "/city"
                                this.state = jso<SchoolPageLocationState> { schoolUri = it.cityUri}
                                span {
                                    +"${it.cityName}, ${it.countryName}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val MapPositioner = FC<Props> {
    val map = useMap()
    val mapCoordinates = useContext(MapCoordinatesContext)
    useEffect(mapCoordinates) {
        if (mapCoordinates.size > 1) {
            val bounds = latLngBounds(mapCoordinates.map {
                val (lat, lng) = it.coordinates.split(" ")
                latLng(lat.toDouble(), lng.toDouble())
            }.toTypedArray())
            map.fitBounds(bounds)
            map.invalidateSize(false)
        } else if (mapCoordinates.isNotEmpty()) {
            val bounds = latLngBounds(arrayOf(
                latLng(48.0, -5.5),
                latLng(51.4, 2.3),
                latLng(48.6, 8.4),
                latLng(43.7, 8.0),
                latLng(42.0, 3.1),
                latLng(43.4, -2.4)
            ))
            map.fitBounds(bounds)
            map.invalidateSize(true)
        }
    }
}