package opensavvy.compose.lazy

import androidx.compose.runtime.Composable

internal class LoadedItem(
	val key: Any?,
	val block: @Composable () -> Unit,
)
