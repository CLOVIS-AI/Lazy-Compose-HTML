# Lazy layouts for Compose HTML

Lazy layouts for [Compose HTML](https://github.com/JetBrains/compose-multiplatform#compose-html).

## Usage

Add a dependency on the library:
```kotlin
// Configure the project for Kotlin Multiplatform
// Don't forget to add the Compose plugin

kotlin {
	js(IR) {
		browser()
	}
	
	sourceSets.jsMain.dependencies {
		implementation("dev.opensavvy.compose.lazy:lazy-layouts:<version>")
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

## Features

- Items are loaded batch by batch when the end of the loaded section is reached.
- Items can change size.
- Sections can change over time by editing the backing list, items from other sections are not reloaded.
- The library only adds the most minimal CSS to make the containing `div` use flex, the style is entirely customizable with plain CSS or through the Compose HTML `attrs` builder.

To learn more, see the [documentation](https://opensavvy.gitlab.io/ui/compose-lazy-html/api-docs/index.html).

## Example

A full example project is available [here](demo). The latest version is deployed [here](https://opensavvy.gitlab.io/ui/compose-lazy-html/demo/index.html).

## License

This project is licensed under the [Apache 2.0 license](LICENSE).

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).
- To learn more about our coding conventions and workflow, see the [OpenSavvy website](https://opensavvy.dev/open-source/index.html).
- This project is based on the [OpenSavvy Playground](docs/playground/README.md), a collection of preconfigured project templates.

If you don't want to clone this project on your machine, it is also available using [DevContainer](https://containers.dev/) (open in [VS Code](https://code.visualstudio.com/docs/devcontainers/containers) • [IntelliJ & JetBrains IDEs](https://www.jetbrains.com/help/idea/connect-to-devcontainer.html)). Don't hesitate to create issues if you have problems getting the project up and running.
