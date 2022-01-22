package search

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
import schoolPage.SchoolPageLocationState
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
                    popup {
                        div {
                            className = "marker-popup"
                            Link {
                                this.to = "/school"
                                this.state = jso<SchoolPageLocationState> { schoolUri = it.schoolUri }
                                span {
                                    +it.popupText
                                }
                            }
                            br { }
                            Link {
                                this.to = "/city"
                                this.state = jso<SchoolPageLocationState> { schoolUri = it.cityUri }
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