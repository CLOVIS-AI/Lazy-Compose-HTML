package opensavvy.compose.lazy

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

// *** THIS VALUE SHOULD ALWAYS BE FALSE ***
// *** ONLY SET IT TO TRUE TEMPORARILY FOR DEVELOPMENT, NEVER COMMIT IT ***
private const val DEBUG_LAZY_HTML = false

internal inline fun debugEvent(message: () -> String) {
	if (DEBUG_LAZY_HTML) {
		val msg = message()
		console.log(msg)
	}
}

@Composable
internal inline fun DebugOnly(
	color: CSSColorValue = Color.darkblue,
	top: Boolean = true,
	crossinline block: @Composable () -> Unit,
) {
	if (DEBUG_LAZY_HTML) {
		Div({
			style {
				backgroundColor(color)
				if (top) top(0.px) else bottom(0.px)
				position(Position.Sticky)
			}
		}) {
			block()
		}
	}
}
