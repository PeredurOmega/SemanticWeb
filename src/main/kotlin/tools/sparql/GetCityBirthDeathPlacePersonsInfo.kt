package tools.sparql

val getCityBirthPlacePersonsInfo =
    SparqlMultipleResults<GetCityBirthDeathPlacePersonsInfoVariables, GetPersonGeneralInfoResponse>("getCityBirthPlacePersonsInfo")
val getCityDeathPlacePersonsInfo =
    SparqlMultipleResults<GetCityBirthDeathPlacePersonsInfoVariables, GetPersonGeneralInfoResponse>("getCityDeathPlacePersonsInfo")

external interface GetCityBirthDeathPlacePersonsInfoVariables : SparqlVariables {
    var uri: String
}