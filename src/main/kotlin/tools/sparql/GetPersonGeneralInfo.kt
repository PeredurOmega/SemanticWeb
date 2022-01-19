package tools.sparql

val getPersonGeneralInfo = SparqlMultipleResults<GetPersonGeneralInfoVariables, GetPersonGeneralInfoResponse>("getPersonGeneralInfo")

external interface GetPersonGeneralInfoResponse : SparqlResponse {
    var label: SparqlValue<String>
    var nameDbp: SparqlValue<String?>
    var nameFoaf: SparqlValue<String?>
    var thumbnail: SparqlValue<String?>
}

external interface GetPersonGeneralInfoVariables : SparqlVariables {
    var uri: String
}