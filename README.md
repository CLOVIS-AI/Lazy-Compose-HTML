# Lazy layouts for Compose HTML

Lazy layouts for [Compose HTML](https://github.com/JetBrains/compose-multiplatform#compose-html).

## Usage

Add a dependency on the library:
```kotlin
// Configure the project for Kotlin Multiplatform
// Don't forget to add the Compose plugin

kotlin {
	js(IR)
	
	val jsMain by sourceSets.getting {
		dependencies {
			implementation("dev.opensavvy.compose.lazy:lazy-layouts:<version>")
		}
	}
}
```
To select a version, see the [list of releases](https://gitlab.com/opensavvy/ui/compose-lazy-html/-/releases).

This library introduces lazy layout implementations that work with Compose HTML:
```kotlin
@Composable
fun ShowUsers(users: List<UserId>) {
    LazyColumn {
        items(users) { userId ->
            var user by remember { mutableStateOf<User?>(null) }

            LaunchedEffect(userId) {
                user = requestUser(userId)
            }

            user?.also { Show(it) }
        }
    }
}
```

## License

This project is licensed under the [Apache 2.0 license](LICENSE).

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).
- To learn more about our coding conventions and workflow, see the [OpenSavvy Wiki](https://gitlab.com/opensavvy/wiki/-/blob/main/README.md#wiki).
- This project is based on the [OpenSavvy Playground](docs/playground/README.md), a collection of preconfigured project templates.
