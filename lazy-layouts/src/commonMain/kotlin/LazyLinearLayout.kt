package opensavvy.compose.lazy

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement
import kotlin.math.max

private const val step = 20

@Composable
internal fun LazyLinearLayout(
	dsl: LazyDsl,
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
	val elementsLoaders = dsl.elements
	var cursor by remember { mutableStateOf(Cursor()) }
	var nextLoaderIndex by remember { mutableStateOf(0) }

	val visibilityDetector = remember(cursor) {
		{
			Snapshot.withMutableSnapshot {
				// Only move end when there are still elements to be displayed
				if (nextLoaderIndex < elementsLoaders.size || !cursor.isCursorAtEnd()) {
					cursor = cursor.moveEnd(step)
					if (cursor.isCursorAtEnd() && nextLoaderIndex < elementsLoaders.size) {
						val newItems = elementsLoaders[nextLoaderIndex]()
						cursor = cursor.addItems(newItems)
						nextLoaderIndex++
					}
				}
			}
		}
	}

	Div(attrs) {
		if (cursor.isEmpty()) {
			VisibilityDetector(visibilityDetector)
		}

		val items = cursor.getCurrentItems()
		for ((i, item) in items.withIndex()) {
			Div {
				// Add the visibility detector as part of an element to avoid duplicate margins
				if (i == max(0, items.size - 5)) {
					VisibilityDetector(visibilityDetector)
				}

				key(item.key) {
					item.block()
				}
			}
		}
	}
}
