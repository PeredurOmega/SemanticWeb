package tools.sparql

val getCityBirthPlacePersonsInfo = SparqlMultipleResults<GetCityBirthDeathPlacePersonsInfoVariables, GetCityBirthDeathPlacePersonsInfoResponse>("getCityBirthPlacePersonsInfo")
val getCityDeathPlacePersonsInfo = SparqlMultipleResults<GetCityBirthDeathPlacePersonsInfoVariables, GetCityBirthDeathPlacePersonsInfoResponse>("getCityDeathPlacePersonsInfo")


external interface GetCityBirthDeathPlacePersonsInfoResponse : SparqlResponse {
    var name: SparqlValue<String>
    var thumbnail: SparqlValue<String>?
}

external interface GetCityBirthDeathPlacePersonsInfoVariables : SparqlVariables {
    var uri: String
}