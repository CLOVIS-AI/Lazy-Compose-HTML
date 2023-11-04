package opensavvy.compose.lazy

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

@Composable
internal fun LazyLinearLayout(
	dsl: LazyDsl.() -> Unit,
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
		remember(it.dependencies) {
			SectionLoader(it)
		}
	}

	Div(attrs) {
		var visibleEndIndex: Int? = null
		for ((i, loader) in loaders.withIndex()) {
			for (item in loader.items) Div {
				key(item.key) {
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
