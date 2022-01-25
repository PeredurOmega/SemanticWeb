package search

import react.*

val MapCoordinatesContext = createContext(mutableListOf<Coordinates>())

val MapCoordinatesSetterContext = createContext<StateSetter<MutableList<Coordinates>>?>(null)

val mapCoordinatesContextProvider = FC<PropsWithChildren> { props ->
    val (mapCoordinates, setMapCoordinates) = useState(mutableListOf<Coordinates>())

    MapCoordinatesSetterContext.Provider(setMapCoordinates) {
        MapCoordinatesContext.Provider(mapCoordinates) {
            props.children()
        }
    }
}

data class Coordinates(
    val coordinates: String?,
    val primaryText: String?,
    val primaryUri: String?,
    val secondaryText: String?,
    val secondaryUri: String?
)
