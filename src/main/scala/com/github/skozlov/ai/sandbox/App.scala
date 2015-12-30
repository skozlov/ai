package com.github.skozlov.ai.sandbox

import scala.concurrent.duration._
import scala.swing.{Frame, SimpleSwingApplication}

object App extends SimpleSwingApplication {
	private implicit val model = new Model(
		minSize = 3, maxSize = 10, tactMinDuration = 1.second, tactMaxDuration = 1.minute)

	override lazy val top: Frame = new MainFrame
}