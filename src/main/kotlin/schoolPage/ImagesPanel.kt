package schoolPage

import react.FC
import tools.sparql.GetSchoolSameFrResponse
import tools.sparql.SparqlQueryConsumerProps
import tools.useWikipediaScrapper

external interface ImagePanelProps : SparqlQueryConsumerProps<GetSchoolSameFrResponse> {
    var uri : String
}

val imagesPanel = FC<ImagePanelProps> { props ->
    val schoolLogoUri = useWikipediaScrapper(props.uri, props.queryResult.sameFr.value,1) {
        it.contains("logo", ignoreCase = true) || it.contains(
            "signature",
            ignoreCase = true
        )
    }
    val schoolImageUri = useWikipediaScrapper(props.uri, props.queryResult.sameFr.value, 1) {
        !(it.contains("logo", ignoreCase = true) || it.contains(
            "signature",
            ignoreCase = true
        ))
    }
    if (schoolLogoUri.isNotEmpty() && schoolImageUri.isNotEmpty()) {
        imageDisplay {
            className = "logo-img"
            this.schoolImageUri = schoolLogoUri[0]
        }
        imageDisplay {
            className = "school-img"
            this.schoolImageUri = schoolImageUri[0]
        }
    }
}