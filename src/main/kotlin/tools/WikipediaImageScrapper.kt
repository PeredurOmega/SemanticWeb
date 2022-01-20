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

fun getImagesFromWikipediaPage(pageName: String, nbImages: Int, setSchoolImagesUri: StateSetter<List<String>>, filter : (String) -> Boolean, domain : String = "fr") {
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort()) {
            if (xhr.status == 200.toShort()) {
                val results : WikipediaScrapperResults = JSON.parse(xhr.responseText)
                println("page name : $pageName")
                val formattedResults = results.items.map {
                    formatUri(it.srcset.last().src)
                }
                println("RAW: $formattedResults, $filter")
                var filteredResults = formattedResults.filter(filter).take(nbImages)
                println("FILTERED: $filteredResults, $filter")
                if (filteredResults.isEmpty()) {
                    filteredResults = formattedResults.take(nbImages)
                }
                println("FINAL: $filteredResults, $filter")
                setSchoolImagesUri(filteredResults)
            }
            else if (xhr.status == 404.toShort() && domain != "en") {
                println("404 page name : $pageName")
                getImagesFromWikipediaPage(pageName, nbImages, setSchoolImagesUri, filter,"en")
            }
            else {
                //TODO Gérer les cas d'erreurs
            }
        }
    }
    xhr.open("GET", "https://${domain}.wikipedia.org/api/rest_v1/page/media-list/${pageName}")
    xhr.send()
}

private fun acceptAll(str : String) = true

fun useWikipediaScrapper(pageName: String, nbImages : Int = 2, filter : (String) -> Boolean = ::acceptAll) : List<String> {
    val (schoolImagesUri, setSchoolImagesUri) = useState(listOf<String>())
    useEffectOnce {
        getImagesFromWikipediaPage(pageName.removePrefix("http://dbpedia.org/resource/"), nbImages, setSchoolImagesUri, filter = filter)
    }
    return schoolImagesUri
}