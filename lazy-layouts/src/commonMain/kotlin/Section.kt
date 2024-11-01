package opensavvy.compose.lazy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot

/**
 * Immutable representation of a section.
 */
internal data class Section(
	/**
	 * An object that represents the identity of this section.
	 */
	val identity: Any,
	/**
	 * Loads an item from this section at a given index.
	 *
	 * This lambda returns `null` when the index is out of range for this section.
	 *
	 * Sections must be continuous.
	 * That is, for any `n`, if `n` and `n+2` are in-range, then `n+1` must be in-range too.
	 *
	 * Sections must be around 0.
	 * That is, only an empty section may consider 0 to be out of range.
	 * Any other section must consider 0 to be in-range, and may extend any distance in each direction.
	 */
	val loadAt: (index: Int) -> LoadedItem?,
)

/**
 * Mutable incremental loader for a [Section].
 */
internal data class SectionLoader(private val section: Section) {
	private var loadedMax by mutableStateOf(-1)
	private var knownMax by mutableStateOf<Int?>(null)

	private val loaded = mutableStateMapOf<Int, LoadedItem>()

	val items get() = (0..loadedMax).asSequence()
		.mapNotNull { loaded[it] }

	val endReached get() = loadedMax == knownMax

	fun loadMoreAtEnd() = Snapshot.withMutableSnapshot {
		repeat(step) {
			val last = loadedMax
			val datum = section.loadAt(last + 1)

			if (datum != null) {
				// We just loaded an item
				loadedMax = last + 1
				loaded[last + 1] = datum
			} else {
				// we loaded everything
				knownMax = last
			}
		}
	}
}

private const val step = 20
