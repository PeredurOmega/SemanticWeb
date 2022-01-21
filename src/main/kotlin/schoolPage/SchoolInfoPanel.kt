package schoolPage

import kotlinext.js.jso
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import search.Coordinates
import search.MapCoordinatesSetterContext
import tools.sparql.GetSchoolInfoResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.sparql.getSchoolInfo
import tools.sparql.sparqlQueryLoaderSingle

external interface SchoolInfoPanelProps : Props {
    var schoolUri : String
}

val schoolInfoPanel = FC<SchoolInfoPanelProps> { props ->
    div {
        sparqlQueryLoaderSingle(getSchoolInfo, jso { uri = props.schoolUri }, true) {
            infoPanelWrapper { }
        }
    }
}

external interface InfoPanelWrapperProps : SparqlQueryConsumerProps<GetSchoolInfoResponse>

private val infoPanelWrapper = FC<InfoPanelWrapperProps> { props ->
    val setCoordinates = useContext(MapCoordinatesSetterContext)
    useEffectOnce {
        val coordinates = props.queryResult.coordinate?.value?: "46.71 1.72"
        val schoolName = props.queryResult.nameDbp?.value?:props.queryResult.nameFoaf?.value?: props.queryResult.label.value
        val cityName = props.queryResult.cityName?.value?:""
        val countryName = props.queryResult.countryName?.value?:""
        setCoordinates?.invoke(mutableListOf(Coordinates(coordinates, schoolName, cityName, countryName)))
    }

    generalInfoPanel {
        this.schoolInfo = props.queryResult
    }
    hr { }
    detailedInfoPanel {
        this.schoolInfo = props.queryResult
    }
}