package com.github.skozlov.ai

import scala.swing.{Frame, SimpleSwingApplication}

object App extends SimpleSwingApplication {
	override lazy val top: Frame = {
		val ui = new UI(World.random(minSize = 3, maxSize = 10, agent = new ChaoticAgent))
		ui.start()
		ui
	}
}