# Module Lazy layouts for Compose HTML

Compose HTML implementation of LazyRow and LazyColumn.

<a href="https://search.maven.org/search?q=dev.opensavvy.compose.lazy"><img src="https://img.shields.io/maven-central/v/dev.opensavvy.compose.lazy/lazy-layouts.svg?label=Maven%20Central"></a>
<a href="https://opensavvy.dev/open-source/stability.html"><img src="https://badgen.net/static/Stability/alpha/purple"></a>
<a href="https://javadoc.io/doc/dev.opensavvy.compose.lazy/lazy-layouts"><img src="https://badgen.net/static/Other%20versions/javadoc.io/blue"></a>

## Introduction

This library introduces the [LazyColumn][opensavvy.compose.lazy.LazyColumn] and [LazyRow][opensavvy.compose.lazy.LazyRow] composables.
They allow displaying large amounts of information on screen without overwhelming the engine, only loading the items that are necessary.

```kotlin
@Composable
fun ShowUsers(users: List<UserId>) {
	LazyColumn {
		// We are now inside the LazyDSL, which allows us 
		// to declare the items we want to display.
		// Items are displayed in the same order as they 
		// are declared in.
		
		// Each function call in the lazy DSL is called a 'section'.
		// Here, we create a section that has a single item.
		item {
			Text("This could be a header")
			// Here, we can use any composable, for example
			// to make the text bold, etc.
		}
		
		// We can also create sections that have multiple items.
		items(10) { index ->
			Text("Loading element #$index…")
		}

		// If you know the list of elements you want to display,
		// you can pass it directly.
		// In this example, we know the list of identifiers.
		// Each element makes a request to dereference itself
		// when it becomes visible.
		items(userIds) { userId ->
			var user by remember { mutableStateOf<User?>(null) }

			LaunchedEffect(userId) {
				user = requestUser(userId)
			}

			user?.also { Show(it) }
		}
	}
}
```

To learn more about the DSL, see [LazyDSL][opensavvy.compose.lazy.LazyDsl].
