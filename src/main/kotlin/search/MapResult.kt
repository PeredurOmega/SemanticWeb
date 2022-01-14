package search

import react.FC
import react.PropsWithChildren
import tools.map.MapContainerProps
import tools.map.TileLayerProps
import tools.map.mapContainer
import tools.map.tileLayer

external interface MapResultProps : PropsWithChildren, MapContainerProps, TileLayerProps { }

val mapResult = FC<MapResultProps> { props ->
    mapContainer {
        id = props.id
        center = props.center
        zoom = props.zoom
        tileSize = props.tileSize

        tileLayer {
            url = props.url
            attribution = props.attribution
        }

        props.children()
    }
}