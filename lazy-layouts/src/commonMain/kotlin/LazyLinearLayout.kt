package opensavvy.compose.lazy

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

@Composable
internal fun LazyLinearLayout(
	dsl: LazyDsl.() -> Unit,
	itemAttrs: AttrBuilderContext<HTMLDivElement>? = null,
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
	val sections by remember(dsl) {
		derivedStateOf {
			LazyDsl().apply(dsl)
				.sections
				.toList()
		}
	}

	val loaders = sections.map {
		remember(it.identity) {
			SectionLoader(it)
		}
	}

	Div(attrs) {
		var visibleEndIndex: Int? = null
		for ((i, loader) in loaders.withIndex()) {
			for (item in loader.items) key(item.key) {
				Div(itemAttrs) {
					item.block()
				}
			}

			if (visibleEndIndex == null && !loader.endReached) {
				visibleEndIndex = i
				VisibilityDetector {
					Snapshot.withMutableSnapshot {
						loader.loadMoreAtEnd()
					}
				}
			}
		}
	}
}
