package opensavvy.compose.lazy.demo

import opensavvy.compose.lazy.LazyColumn
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
	val letterIndexes = List(26) { it }

	renderComposable(rootElementId = "root") {
		LazyColumn {
			item {
				Text("First element")
			}

			items(200, key = { it }) {
				Text("Element #$it")
			}

			items(letterIndexes) {
				val lowerCase = it + 'a'.code

				Text("Letter ${lowerCase.toChar()}")
			}

			items(letterIndexes) {
				val upperCase = it + 'A'.code

				Text("Letter ${upperCase.toChar()}")
			}
		}
	}
}
