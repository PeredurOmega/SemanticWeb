@file:JsModule("react-inlinesvg")
@file:JsNonModule

package tools

import org.w3c.dom.events.Event
import react.ComponentClass
import react.Props

@JsName("default")
external val inlineSVG: ComponentClass<InlineSVGProps>

external interface InlineSVGProps : Props{
    var src: String
    var className: String
    var onClick : (Event) -> Unit
    var title: String
    //Check other props at [github.com](https://github.com/gilbarbara/react-inlinesvg)
}