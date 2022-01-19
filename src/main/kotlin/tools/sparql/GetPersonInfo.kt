package tools.sparql

val getPersonInfo = SparqlQuery<GetSearchResultVariables, GetSearchResultResponse>("getSearchResult")

external interface GetPersonInfoResponse : SparqlResponse, GetSearchResultResponseCard, GetSearchResultResponseMaps

external interface GetPersonInfoResponseCard {
    var label: SparqlValue<String> //rdfs:label
    var domain: SparqlValue<String> //gold:hypernym
    var imageURL: SparqlValue<String>
    var description: SparqlValue<String> //rdfs:comment
    var title: SparqlValue<String?>
    var birthCountry: SparqlValue<String> //dbo:birthPlace recup ville puis aller prendre le pays
    var universities: SparqlValue<List<String>> //dbo:almaMatter
}

external interface GetPersonInfoVariables : SparqlVariables {
    var uri: String
}