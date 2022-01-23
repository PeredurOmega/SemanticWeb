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

//TODO USE PRIMARY TEXT AND SECONDARY TEXT AND PRIMARY URI AND SECONDARY URI
data class Coordinates(
    val coordinates: String?,
    val popupText: String,
    val cityName: String?,
    val countryName: String?,
    val cityUri: String?,
    val schoolUri: String?
)
