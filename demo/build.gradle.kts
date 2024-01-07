plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(opensavvyConventions.plugins.aligned.composeMultiplatform)
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
