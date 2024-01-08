package opensavvy.compose.lazy.demo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import opensavvy.compose.lazy.LazyColumn
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextUInt

fun main() {
	val letterIndexes = List(26) { it }

	renderComposable(rootElementId = "root") {
		val randomElements = remember { mutableStateListOf<UInt>() }

		LazyColumn ({
			classes("lazy")
		}) {
			item {
				Text("First element")
			}

			items(200, key = { it }) {
				Text("Element #$it")
			}

			item {
				Button({
					onClick {
						Snapshot.withMutableSnapshot {
							randomElements.clear()
							repeat(Random.nextInt(10..100)) {
								randomElements.add(Random.nextUInt())
							}
						}
					}
				}) {
					Text("Generate random elements")
				}
			}

			items(randomElements) {
				Text("Random value: $it")
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
