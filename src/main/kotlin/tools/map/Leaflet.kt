@file:JsModule("leaflet")
@file:JsNonModule

package tools.map

@JsName("Map")
external interface Map {
    /**
     * https://leafletjs.com/reference.html#zoom/pan-options
     */
    fun invalidateSize(animate: Boolean): Map

    fun fitBounds(latLngBounds: LatLngBounds)
}

@JsName("LatLngBounds")
external interface LatLngBounds

@JsName("latLngBounds")
external fun latLngBounds(latLngs: Array<LatLng>, fitBoundsOptions: FitBoundsOptions): LatLngBounds

@JsName("FitBoundsOptions")
external interface FitBoundsOptions {
    var padding: Point
}

@JsName("LatLng")
external interface LatLng

@JsName("latLng")
external fun latLng(lat: Double, lng: Double): LatLng

@JsName("Point")
external interface Point

@JsName("point")
external fun point(x: Double, y: Double): Point