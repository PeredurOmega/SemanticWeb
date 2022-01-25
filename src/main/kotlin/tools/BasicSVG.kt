package tools

import kotlinext.js.jso
import org.w3c.dom.events.Event
import react.ChildrenBuilder

val imageDir = "/images/"

fun ChildrenBuilder.basicSVG(
    iconName: String,
    title: String,
    className: String? = null,
    onClick: ((Event) -> Unit)? = null
) {
    child(inlineSVG, basicSVGProps(iconName, title, className, onClick))
}

private fun basicSVGProps(
    iconName: String,
    title: String,
    className: String? = null,
    onClick: ((Event) -> Unit)? = null
) = jso<InlineSVGProps> {
    src = "$imageDir$iconName.svg"
    this.title = title
    if (className != null) this.className = className
    if (onClick != null) this.onClick = onClick
}