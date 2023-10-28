package opensavvy.compose.lazy

internal class Cursor private constructor(
	private val start: Int,
	private val end: Int,
	private val loadedItems: List<LazyItem>
) {

	constructor() : this(0, 0, mutableListOf())

	fun getCurrentItems() =
		if (end - start <= loadedItems.size) {
			loadedItems.subList(start, end)
		} else {
			loadedItems
		}

	fun addItems(newItems: List<LazyItem>): Cursor {
		return Cursor(
			start = start,
			end = end,
			loadedItems = loadedItems + newItems
		)
	}

	fun isCursorAtEnd() = loadedItems.size <= end

	fun moveStart(distance: Int): Cursor {
		return Cursor(
			start = start + distance,
			end = end,
			loadedItems = loadedItems
		)
	}

	fun moveEnd(distance: Int): Cursor {
		return Cursor(
			start = start,
			end = end + distance,
			loadedItems = loadedItems
		)
	}

	fun isEmpty(): Boolean {
		return start == end
	}
}