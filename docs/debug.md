# Debug mode

Understanding why things recompose can be quite difficult on HTML. To help with this, this project includes a "debug mode" that logs more information and displays information about the sections directly in the app.

For performance reasons, the debug mode is compiled out during release. It is not possible to enable it using a published version of the library. However, it can easily be enabled during development. First, clone the repository somewhere on your system:

```shell
git clone https://gitlab.com/opensavvy/ui/compose-lazy-html.git
```

Ensure you are working with the same version as you are using in your project (for example, if you are using the version `0.2.6`):
```shell
git switch --detach 0.2.6
```

Open the file `Debug.kt` and set the constant `DEBUG_LAZY_HTML` to `true`. If you cannot find the file, you may be using a version of the library from being debug mode was enabled.

In your own project, add the following line at the end of your root `settings.gradle.kts`:
```kotlin
includeBuild("../replace/this/by/the/path/to/where/you/cloned/the/repository")
```

This configuration tells Gradle to build the project from source and substitute all its usages in your project by the source version ([learn more](https://docs.gradle.org/current/userguide/composite_builds.html)).

Now, restart your app, and you should see the debug markers and additional logging information.
