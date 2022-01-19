package search

import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.useContext
import tools.map.*
import kotlin.js.json

val mapResult = FC<Props> {
    val mapCoordinates = useContext(MapCoordinatesContext)
    div {
        className = "map-results"
        mapContainer {
            id = "map"
            center = json("lat" to "46.71", "lng" to "1.72")
            zoom = 6
            tileSize = 1

            tileLayer {
                attribution = "Grandes Ecoles"
                url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
            }

            mapCoordinates.forEach {
                val (lat, lng) = it.coordinates.split(" ")
                marker {
//                    icon = icon(
//                        json(
//                            "iconUrl" to "images/Marker.svg",
//                            "iconRetinaUrl" to "images/Marker.svg",
//                            "iconAnchor" to arrayOf(25, 50),
//                            "popupAnchor" to arrayOf(0, -35),
//                            "iconSize" to arrayOf(50, 50)
//                        )
//                    )
                    position = json(
                        "lat" to lat,
                        "lng" to lng
                    )
                    popup {
                        div {
                            className = "marker-popup"
                            span {
                                +it.popupText
                            }
                            br { }
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