package sparql

val getPersonInfo = SparqlSingleResult<GetPersonInfoVariables, GetPersonInfoResponse>("getPersonInfo")

external interface GetPersonInfoResponse : SparqlResponse, GetPersonInfoResponseCard

external interface GetPersonInfoResponseCard {
    var wiki: SparqlValue<String>?//foaf:isPrimaryTopicOf lien wiki
    var labelen: SparqlValue<String>? //rdfs:label
    var domain: SparqlValue<String>? //gold:hypernym
    var descriptionfr: SparqlValue<String>? //rdfs:comment
    var descriptionen: SparqlValue<String>? //rdfs:comment
    var titles: SparqlValue<String>?
    var titlelab: SparqlValue<String>?
    var cityen: SparqlValue<String>? //dbo:birthPlace recup ville puis aller prendre le pays
    var cityconcat: SparqlValue<String>?
}

external interface GetPersonInfoVariables : SparqlVariables {
    var uri: String
}

val getPersonUniversities =
    SparqlMultipleResults<GetPersonUniversitiesVariables, GetPersonUniversitiesResponse>("getPersonUniversities")

external interface GetPersonUniversitiesResponse : SparqlResponse, GetPersonUniversitiesResponseCard

external interface GetPersonUniversitiesResponseCard {
    //foaf:isPrimaryTopicOf lien wiki
    var universities: SparqlValue<String>?
    var ge: SparqlValue<String>?
    var resource: SparqlValue<String>?
}

external interface GetPersonUniversitiesVariables : SparqlVariables {
    var uri: String
}

val getPersonImage = SparqlSingleResult<GetPersonImageVariables, GetPersonImageResponse>("getImage")

external interface GetPersonImageResponse : SparqlResponse, GetPersonImageResponseCard

external interface GetPersonImageResponseCard {
    var imageURL: SparqlValue<String>?
}

external interface GetPersonImageVariables : SparqlVariables {
    var uri: String
}


