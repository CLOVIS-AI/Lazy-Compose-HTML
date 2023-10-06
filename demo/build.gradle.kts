plugins {
	id("conventions.base")
	id("conventions.kotlin")

	alias(libs.plugins.compose)
}

kotlin {
	js(IR) {
		browser()

		binaries.executable()
	}

	val jsMain by sourceSets.getting {
		dependencies {
			implementation(projects.lazyLayouts)
		}
	}
}
