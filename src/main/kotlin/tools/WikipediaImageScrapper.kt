package tools

import org.w3c.xhr.XMLHttpRequest
import react.*

external interface WikipediaScrapperResults {
    var items: Array<WikipediaScrapperResult>

    interface WikipediaScrapperResult {
        var title: String
        val srcset : Array<WikipediaSrcSet>

        interface WikipediaSrcSet {
            val src : String
        }
    }
}

fun formatUri(uri : String) : String {
    return "https:${uri}"
}

fun getImagesFromWikipediaPage(pageName: String, nbImages: Int, setSchoolImagesUri: StateSetter<List<String>>) {
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort()) {
            if (xhr.status == 200.toShort()) {
                val results : WikipediaScrapperResults = JSON.parse(xhr.responseText)
                val schoolImagesUri = results.items.take(nbImages).map {
                    formatUri(it.srcset.last().src)
                }
                setSchoolImagesUri(schoolImagesUri)
            }
            else {
                //TODO Gérer les cas d'erreurs
            }
        }
    }
    xhr.open("GET", "https://fr.wikipedia.org/api/rest_v1/page/media-list/${pageName}")
    xhr.send()
}

fun useWikipediaScrapper(pageName: String, nbImages : Int = 2) : List<String> {
    val (schoolImagesUri, setSchoolImagesUri) = useState(listOf<String>())
    useEffectOnce {
        getImagesFromWikipediaPage(pageName, nbImages, setSchoolImagesUri)
    }
    return schoolImagesUri
}