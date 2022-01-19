package schoolPage

import react.FC
import react.Props
import react.useState
import search.mapResult
import tools.useWikipediaScrapper

val imagesPanel = FC<Props> {
    //TODO remplacer le nom de la page depuis la recherche
    val schoolImagesUri = useWikipediaScrapper("École_nationale_supérieure_d'informatique_et_de_mathématiques_appliquées")
    if (schoolImagesUri.isNotEmpty()) {
        val schoolLogoUri = schoolImagesUri[0]
        val schoolImageUri = schoolImagesUri[1]
        imageDisplay {
            this.schoolImageUri = schoolLogoUri
        }
        imageDisplay {
            this.schoolImageUri = schoolImageUri
        }
    }
}