package tools

import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.html.ReactHTML.img

external interface WikipediaScrapperResults {
    var items: Array<WikipediaScrapperResult>

    interface WikipediaScrapperResult {
        var title: String
        val srcset: Array<WikipediaSrcSet>

        interface WikipediaSrcSet {
            val src: String
        }
    }
}

fun formatUri(uri: String): String {
    return "https:${uri}"
}

fun getImagesFromWikipediaPage(
    pageName: String,
    nbImages: Int,
    setSchoolImagesUri: StateSetter<List<String>>,
    filter: (String) -> Boolean,
    domain: String = "fr"
) {
    val xhr = XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort()) {
            if (xhr.status == 200.toShort()) {
                val results: WikipediaScrapperResults = JSON.parse(xhr.responseText)
                val formattedResults = results.items.map {
                    formatUri(it.srcset.last().src)
                }
                var filteredResults = formattedResults.filter(filter).take(nbImages)
                if (filteredResults.isEmpty()) {
                    filteredResults = formattedResults.take(nbImages)
                }
                setSchoolImagesUri(filteredResults)
            } else if (xhr.status == 404.toShort() && domain != "en") {
                getImagesFromWikipediaPage(pageName, nbImages, setSchoolImagesUri, filter, "en")
            }
        }
    }
    xhr.open("GET", "https://${domain}.wikipedia.org/api/rest_v1/page/media-list/${pageName}")
    xhr.send()
}

private fun acceptAll(@Suppress("UNUSED_PARAMETER") str: String) = true

fun cleanPageName(pageName: String, prefix: List<String>, removeUnderscores: Boolean = false): String {
    var cleanedPageName = pageName
    prefix.forEach {
        cleanedPageName = cleanedPageName.removePrefix(it)
    }
    if (removeUnderscores) cleanedPageName = cleanedPageName.replace("_", " ")
    return cleanedPageName
}

private fun choosePageName(pageUri: String, pageSame: String): String {
    val parts = pageUri.split("_")
    val nbMatch = parts.count {
        pageSame.contains(it, ignoreCase = true)
    }
    if (pageUri.contains("po", ignoreCase = true) && pageSame.contains("politiques", ignoreCase = true)) return pageSame
    return if (nbMatch > parts.size * (0.9)) pageSame else pageUri
}


fun useWikipediaScrapper(
    pageUri: String,
    pageSame: String? = null,
    nbImages: Int = 2,
    filter: (String) -> Boolean = ::acceptAll
): List<String> {
    val (schoolImagesUri, setSchoolImagesUri) = useState(listOf<String>())
    val prefix = listOf("http://dbpedia.org/resource/", "http://fr.dbpedia.org/resource/")

    val pageName = if (pageSame != null) choosePageName(
        cleanPageName(pageUri, prefix),
        cleanPageName(pageSame, prefix)
    ) else pageUri
    useEffectOnce {
        getImagesFromWikipediaPage(cleanPageName(pageName, prefix), nbImages, setSchoolImagesUri, filter = filter)
    }
    return schoolImagesUri
}

external interface WikipediaPhotoProps : Props {
    var uri: String
    var type: String
}

val wikipediaPhoto = FC<WikipediaPhotoProps> { props ->
    val imagesUri = useWikipediaScrapper(props.uri, nbImages = 1)
    img {
        if (imagesUri.isNotEmpty() && !imagesUri[0].contains("defaut", ignoreCase = true)) src = imagesUri[0]
        else {
            if (props.type == "person") {
                src = "${imageDir}PersonPlaceholder.png"
            } else if (props.type == "city") {
                src = "${imageDir}CityPlaceholder.jpg"
            }
        }
    }
}
