package com.github.skozlov.ai

import scala.concurrent.duration._
import scala.swing.{Frame, SimpleSwingApplication}

object App extends SimpleSwingApplication {
	private val controller = new Controller(minSize = 3, maxSize = 10, tactMinDuration = 1.second)

	override lazy val top: Frame = new AgentTypesUI(controller)
}