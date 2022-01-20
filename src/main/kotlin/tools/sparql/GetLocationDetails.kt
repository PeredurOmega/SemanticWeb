package tools.sparql

val getLocationDetailsResult = SparqlSingleResult<GetLocationDetailsResultVariables, GetLocationDetailsResponse>("getLocationDetails")

external interface GetLocationDetailsResponse: SparqlResponse {
    var name : SparqlValue<String>?
    var communeStatusLabel : SparqlValue<String>?
    var country : SparqlValue<String>?
    var abstract : SparqlValue<String>?
    var thumbnail: SparqlValue<String>?
}

external interface GetLocationDetailsResultVariables : SparqlVariables {
    var uri: String
}