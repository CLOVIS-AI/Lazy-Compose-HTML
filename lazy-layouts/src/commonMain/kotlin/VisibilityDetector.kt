package opensavvy.compose.lazy

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import web.dom.document
import web.dom.observers.IntersectionObserver
import kotlin.random.Random
import kotlin.random.nextUInt

/**
 * Invisible component that calls [onVisible] when it appears on screen.
 */
@Composable
internal fun VisibilityDetector(onVisible: () -> Unit) {
	val id = remember { "visibility-observer-" + Random.nextUInt() }
	var hit by remember { mutableStateOf(0) }

	DisposableEffect(onVisible, hit) {
		val div = document.getElementById(id) ?: run {
			console.warn("Lazy Compose: could not find the div with identifier $id, the lazy elements are broken")
			return@DisposableEffect onDispose { /* Nothing to do */ }
		}

		val observer = IntersectionObserver(
			callback = { entries, _ ->
				if (entries.any { it.isIntersecting }) {
					onVisible()
					hit++
				}
			}
		)

		observer.observe(div)

		onDispose {
			observer.disconnect()
		}
	}

	DebugOnly(color = Color.yellow) {
		Text("Visibility detector (has been hit $hit times)")
	}

	Div({
		id(id)
	})
}
