# Lazy layouts for Compose HTML

Lazy layouts for [Compose HTML](https://github.com/JetBrains/compose-multiplatform#compose-html).

## Usage

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
