@file:JsModule("react-leaflet")
@file:JsNonModule

package tools.map

import react.ComponentClass
import react.Props
import kotlin.js.Json

@JsName("MapContainer")
external val mapContainer: ComponentClass<MapContainerProps>

@JsName("TileLayer")
external val tileLayer: ComponentClass<TileLayerProps>

@JsName("Marker")
external val marker: ComponentClass<MarkerProps>

@JsName("Popup")
external val popup: ComponentClass<Props>

@JsName("useMap")
external fun useMap(): Map

external interface MapContainerProps : Props {
    var id: String
    var center: Json
    var zoom: Int
    var tileSize: Int
}

external interface TileLayerProps : Props {
    var url: String
    var attribution: String
}

external interface MarkerProps : Props {
    var position: Json
    var icon: dynamic
}