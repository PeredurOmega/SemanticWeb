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

fun getImagesFromWikipediaPage(pageName: String, nbImages: Int, setSchoolImagesUri: StateSetter<List<String>>, domain : String = "fr") {
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
            else if (xhr.status == 404.toShort() && domain != "en") {
                getImagesFromWikipediaPage(pageName, nbImages, setSchoolImagesUri,"en")
            }
            else {
                //TODO Gérer les cas d'erreurs
            }
        }
    }
    xhr.open("GET", "https://${domain}.wikipedia.org/api/rest_v1/page/media-list/${pageName}")
    xhr.send()
}

fun useWikipediaScrapper(pageName: String, nbImages : Int = 2) : List<String> {
    val (schoolImagesUri, setSchoolImagesUri) = useState(listOf<String>())
    useEffectOnce {
        getImagesFromWikipediaPage(pageName.removePrefix("http://dbpedia.org/resource/"), nbImages, setSchoolImagesUri)
    }
    return schoolImagesUri
}