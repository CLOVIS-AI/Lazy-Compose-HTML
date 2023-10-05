plugins {
	id("conventions.base")
	id("conventions.kotlin")
	id("conventions.library")

	alias(libs.plugins.compose)
}

kotlin {
	js(IR) {
		browser()
	}

	val jsMain by sourceSets.getting {
		dependencies {
			api(compose.runtime)
			api(compose.html.core)

			implementation(libs.kotlin.browser)
		}
	}
}

library {
	name.set("Lazy layouts for Compose HTML")
	description.set("LazyColumn and LazyRow implementation for Compose HTML")
	homeUrl.set("https://gitlab.com/opensavvy/ui/compose-lazy-html")
}
