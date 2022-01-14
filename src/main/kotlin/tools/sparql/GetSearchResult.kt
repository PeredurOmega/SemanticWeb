package tools.sparql

val getSearchResult = SparqlQuery<GetSearchResultVariables, GetSearchResultResponse>("getSearchResult")

external interface GetSearchResultResponse : SparqlResponse {
    var label: SparqlValue<String>
    var name: SparqlValue<String>
    var cityName: SparqlValue<String>
    var countryName: SparqlValue<String>
    var coordinates: SparqlValue<String?>
    var comment: SparqlValue<String?>
    var abstract: SparqlValue<String?>
}

external interface GetSearchResultVariables : SparqlVariables {
    var uri: String
}