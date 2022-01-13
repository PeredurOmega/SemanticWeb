@file:JsModule("leaflet")
@file:JsNonModule

package tools.map

@JsName("Map")
external interface Map {
    /**
     * https://leafletjs.com/reference.html#zoom/pan-options
     */
    fun invalidateSize(animate: Boolean): Map
}