package tools.sparql

val getCityInfo = SparqlSingleResult<GetCityInfoVariables, GetCityResponse>("getCityInfo")

external interface GetCityResponse: SparqlResponse {
    var name : SparqlValue<String>?
    var communeStatusLabel : SparqlValue<String>?
    var countryName : SparqlValue<String>?
    var abstract : SparqlValue<String>?
    var thumbnail: SparqlValue<String>?
    var postalCode: SparqlValue<String>?
    var inseeCode: SparqlValue<String>?
    var area: SparqlValue<String>?
    var altitudeMin: SparqlValue<String>?
    var altitudeMax: SparqlValue<String>?
    var mayor: SparqlValue<String>?
    var politicalParty: SparqlValue<String>?
    var district: SparqlValue<String>?
    var coordinates: SparqlValue<String>?
}

external interface GetCityInfoVariables : SparqlVariables {
    var uri: String
}

val getCityImage = SparqlSingleResult<GetCityImageVariables, GetCityImageResponse>("getImage")

external interface GetCityImageResponse : SparqlResponse, GetCityImageResponseCard

external interface GetCityImageResponseCard {
    var imageURL: SparqlValue<String>?
}

external interface GetCityImageVariables : SparqlVariables {
    var uri: String
}