package opensavvy.compose.lazy

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

@Composable
internal fun LazyLinearLayout(
	dsl: LazyDsl.() -> Unit,
	itemAttrs: AttrBuilderContext<HTMLDivElement>? = null,
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
	debugEvent { "Recomposing LazyLinearLayout — outer\n(the data definition has changed)" }

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
		debugEvent { "Recomposing LazyLinearLayout — inner\n(more data has been loaded)" }

		var lastVisibleSection: Int? = null
		for ((sectionIndex, section) in loaders.withIndex()) {
			DebugOnly { Text("Section $sectionIndex start (${section.items.count()} items, end reached: ${section.endReached})") }

			for (item in section.items) key(item.key) {
				Div(itemAttrs) {
					item.block()
				}
			}

			if (lastVisibleSection == null && !section.endReached) {
				lastVisibleSection = sectionIndex
				VisibilityDetector {
					Snapshot.withMutableSnapshot {
						debugEvent { "Loading more items at the end of section $sectionIndex because the user has reached it…" }
						section.loadMoreAtEnd()
					}
				}
			}

			DebugOnly(top = false) { Text("Section $sectionIndex end") }
		}
	}
}
