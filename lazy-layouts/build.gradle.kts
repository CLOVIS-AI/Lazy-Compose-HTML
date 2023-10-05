plugins {
	id("conventions.base")
	id("conventions.kotlin")
	id("conventions.library")
}

kotlin {
	js(IR) {
		browser()
	}
}

library {
	name.set("Lazy layouts for Compose HTML")
	description.set("LazyColumn and LazyRow implementation for Compose HTML")
	homeUrl.set("https://gitlab.com/opensavvy/ui/compose-lazy-html")
}
