package tools

import react.ChildrenBuilder
import react.router.Navigate
import sparql.SparqlValue

fun requireSCSS(fileName: String) {
    kotlinext.js.require("css/${fileName}.scss")
}

fun ChildrenBuilder.redirectToHome() {
    Navigate {
        to = "/"
        replace = true
    }
}

fun <V1 : String?, V2 : String?> concatenate(
    sparqlValue1: SparqlValue<V1>?,
    sparqlValue2: SparqlValue<V2>?,
    separator: String = ", "
): String {
    return if (!sparqlValue1?.value.isNullOrBlank() && !sparqlValue2?.value.isNullOrBlank()) {
        "${sparqlValue1!!.value}${separator}${sparqlValue2!!.value}"
    } else if (!sparqlValue1?.value.isNullOrBlank()) {
        sparqlValue1!!.value!!
    } else if (!sparqlValue2?.value.isNullOrBlank()) {
        sparqlValue2!!.value!!
    } else {
        ""
    }
}