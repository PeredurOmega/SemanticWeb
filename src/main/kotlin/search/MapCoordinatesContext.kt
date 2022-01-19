package search

import react.*

data class Coordinates(val coordinates: String, val popupText: String, val cityName: String, val countryName : String)

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