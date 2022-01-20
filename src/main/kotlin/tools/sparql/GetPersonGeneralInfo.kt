package tools.sparql

val getPersonAlmaMaterInfo = SparqlMultipleResults<GetPersonGeneralInfoVariables, GetPersonGeneralInfoResponse>("getPersonAlmaMaterInfo")
val getPersonEducationalInfo = SparqlMultipleResults<GetPersonGeneralInfoVariables, GetPersonGeneralInfoResponse>("getPersonEducationInfo")


external interface GetPersonGeneralInfoResponse : SparqlResponse {
    var person: SparqlValue<String>
    var label: SparqlValue<String>
    var nameDbp: SparqlValue<String>?
    var nameFoaf: SparqlValue<String>?
    var thumbnail: SparqlValue<String>?
}

external interface GetPersonGeneralInfoVariables : SparqlVariables {
    var uri: String
}