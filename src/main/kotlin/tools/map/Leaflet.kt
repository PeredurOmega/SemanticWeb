@file:JsModule("leaflet")
@file:JsNonModule

package tools.map

@JsName("Map")
external interface Map {
    /**
     * https://leafletjs.com/reference.html#zoom/pan-options
     */
    fun invalidateSize(animate: Boolean): Map

    fun fitBounds(latLngBounds : LatLngBounds)
}

@JsName("LatLngBounds")
external interface LatLngBounds {

    fun extend(latLng: LatLng): LatLngBounds

}

@JsName("latLngBounds")
external fun latLngBounds(latLngs: Array<LatLng>): LatLngBounds

@JsName("LatLng")
external interface LatLng

@JsName("latLng")
external fun latLng(lat: Double, lng: Double): LatLng