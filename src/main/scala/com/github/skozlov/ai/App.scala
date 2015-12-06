package com.github.skozlov.ai

import scala.swing.{MainFrame, Frame, SimpleSwingApplication}

object App extends SimpleSwingApplication {
	override lazy val top: Frame = new MainFrame{
		contents = new AgentTypesUI
	}
}