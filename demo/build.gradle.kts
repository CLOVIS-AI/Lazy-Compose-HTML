plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(libsCommon.plugins.compose.compiler)
}

kotlin {
	js {
		browser()

		binaries.executable()
	}

	sourceSets.jsMain.dependencies {
		implementation(projects.lazyLayouts)
	}
}
