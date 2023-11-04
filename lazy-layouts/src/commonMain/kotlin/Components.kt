package opensavvy.compose.lazy

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.w3c.dom.HTMLDivElement

/**
 * HTML-based lazy column implementation.
 *
 * Use this composable to display large amounts of elements on screen.
 * Items are only loaded as they appear on screen.
 *
 * Elements are loaded from top to bottom, in the same order as they are declared in [block].
 *
 * ### Example
 *
 * Lazily display a large list of users, unreferencing each from its by `UserId` by calling `requestUser()`:
 *
 * ```kotlin
 * @Composable
 * fun ShowUsers(users: List<UserId>) {
 *     LazyColumn {
 *         items(users) { userId ->
 *             var user by remember { mutableStateOf<User?>(null) }
 *
 *             LaunchedEffect(userId) {
 *                 user = requestUser(userId)
 *             }
 *
 *             user?.also { Show(it) }
 *         }
 *     }
 * }
 * ```
 */
@Composable
fun LazyColumn(
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
	block: LazyDsl.() -> Unit,
) {
	LazyLinearLayout(block) {
		style {
			display(DisplayStyle.Flex)
			flexDirection(FlexDirection.Column)
		}

		attrs?.invoke(this)
	}
}

/**
 * HTML-based lazy row implementation.
 *
 * Use this composable to display large amounts of elements on screen.
 * Items are only loaded as they appear on screen.
 *
 * Elements are loaded from side to side, respecting the user's reading direction, in the same order as they are declared in [block].
 */
@Composable
fun LazyRow(
	attrs: AttrBuilderContext<HTMLDivElement>? = null,
	block: LazyDsl.() -> Unit,
) {
	LazyLinearLayout(block) {
		style {
			display(DisplayStyle.Flex)
			flexDirection(FlexDirection.Row)
		}

		attrs?.invoke(this)
	}
}
