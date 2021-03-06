package sparql

val getSearchResult = SparqlSingleResult<GetSearchResultVariables, GetSearchResultResponse>("getSearchResult")

external interface GetSearchResultResponse : SparqlResponse, GetSearchResultResponseCard, GetSearchResultResponseMaps

external interface GetSearchResultResponseCard {
    var label: SparqlValue<String>
    var sameFr: SparqlValue<String>
    var cityName: SparqlValue<String>?
    var cityUrl: SparqlValue<String>?
    var countryName: SparqlValue<String>?
    var name: SparqlValue<String>?
    var comment: SparqlValue<String>?
    var abstract: SparqlValue<String>?
}

external interface GetSearchResultResponseMaps {
    var coordinates: SparqlValue<String>?
}

external interface GetSearchResultVariables : SparqlVariables {
    var uri: String
}