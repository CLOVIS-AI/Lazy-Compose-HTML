package opensavvy.compose.lazy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey

/**
 * DSL to declare items in a lazy container.
 *
 * Items will be loaded in the same order as functions are called on this object.
 */
class LazyDsl internal constructor() {
	internal val sections = ArrayList<Section>()

	/**
	 * Adds [block] as an item of this lazy container.
	 *
	 * @param key A locally-unique identifier for this item.
	 * For more information, see [key][composeKey].
	 */
	fun item(
		key: Any? = null,
		block: @Composable () -> Unit,
	) {
		sections.add(
			Section(
				identity = Pair(key, block),
				loadAt = { index ->
					if (index == 0)
						LoadedItem(key, block)
					else
						null
				}
			)
		)
	}

	/**
	 * Adds [count] elements to this lazy container.
	 *
	 * Each item is displayed by calling [block] and passing the item index (from `0` inclusive to `count` exclusive).
	 *
	 * @param key A function which generates a locally-unique identifier for an item from its index.
	 * For more information, see [key][composeKey].
	 */
	fun items(
		count: Int,
		key: (index: Int) -> Any,
		block: @Composable (index: Int) -> Unit,
	) {
		sections.add(
			Section(
				identity = Triple(count, key, block),
				loadAt = { index ->
					if (index in 0..<count)
						LoadedItem(key(index)) { block(index) }
					else
						null // we're outside the requested range, give up
				}
			)
		)
	}

	/**
	 * Adds all the items of [items] to this lazy container.
	 *
	 * Each item is displayed by calling [block] and passing the item.
	 *
	 * @param key A function which generates a locally-unique identifier for an item.
	 * By default, uses the item itself as its own key.
	 * For more information, see [key][composeKey].
	 */
	fun <K : Any> items(
		items: Iterable<K>,
		key: (item: K) -> Any = { it },
		block: @Composable (item: K) -> Unit,
	) {
		val data = items.toList()
		sections.add(
			Section(
				identity = Triple(data, key, block),
				loadAt = { index ->
					if (index in data.indices) {
						val value = data[index]
						LoadedItem(key(value)) { block(value) }
					} else {
						null
					}
				}
			)
		)
	}
}
