plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(libsCommon.plugins.compose.multiplatform)
	alias(libsCommon.plugins.compose.compiler)
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
