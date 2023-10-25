package opensavvy.compose.lazy

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement
import kotlin.math.max

const val step = 20

@Composable
internal fun LazyLinearLayout(
	dsl: LazyDsl,
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
	val elementsLoaders = dsl.elements

	var nextLoaderIndex by remember { mutableStateOf(0) }
	val items = remember { mutableStateListOf<LazyItem>() }
	var bufferedItems = remember { listOf<LazyItem>() }

	val visibilityDetector = remember(nextLoaderIndex, elementsLoaders) {
		{
			if (nextLoaderIndex <= elementsLoaders.lastIndex) {
				Snapshot.withMutableSnapshot {
					if (bufferedItems.size > step) {
						items += bufferedItems.take(step)
						bufferedItems = bufferedItems.drop(step)
					} else if(bufferedItems.isNotEmpty() && bufferedItems.size <= step) {
						items += bufferedItems
						bufferedItems = emptyList()
						nextLoaderIndex++
					} else {
						val newItems = elementsLoaders[nextLoaderIndex]()
						if (newItems.size > step) {
							items += newItems.take(step)
							bufferedItems = newItems.drop(step)
						} else {
							items += newItems
							nextLoaderIndex++
						}
					}
				}
			}
		}
	}

	Div(attrs) {
		if (items.isEmpty()) {
			VisibilityDetector(visibilityDetector)
		}

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
