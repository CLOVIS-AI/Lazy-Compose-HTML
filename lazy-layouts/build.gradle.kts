plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
	alias(libsCommon.plugins.compose.multiplatform)
	alias(libsCommon.plugins.compose.compiler)
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

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
	}
}
