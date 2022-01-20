package tools.sparql

val getSchoolInfo = SparqlSingleResult<GetSchoolInfoVariables, GetSchoolInfoResponse>("getSchoolInfo")

external interface GetSchoolInfoResponse : SparqlResponse {
    var label: SparqlValue<String>
    var nameFoaf: SparqlValue<String>?
    var nameDbp: SparqlValue<String>?
    var cityUrl: SparqlValue<String>?
    var cityName: SparqlValue<String>?
    var countryUrl: SparqlValue<String>?
    var countryName: SparqlValue<String>?
    var endowment: SparqlValue<String>?
    var motto: SparqlValue<String>?
    var academicStaff: SparqlValue<String>?
    var administrativeStaff: SparqlValue<String>?
    var doctoral: SparqlValue<String>?
    var numberOfDoctoralStudents: SparqlValue<String>?
    var established: SparqlValue<String>?
    var president: SparqlValue<String>?
    var students: SparqlValue<String>?
    var numberOfStudents: SparqlValue<String>?
    var undergrad: SparqlValue<String>?
    var website: SparqlValue<String>?
    var homepage: SparqlValue<String>?
    var georss: SparqlValue<String>?
    var comment: SparqlValue<String>?
    var abstract: SparqlValue<String>?

}

external interface GetSchoolInfoVariables : SparqlVariables {
    var uri: String
}