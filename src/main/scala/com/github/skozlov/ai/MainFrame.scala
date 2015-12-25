package com.github.skozlov.ai

import javax.swing.JOptionPane._

import scala.swing.{Swing, Frame}

class MainFrame(implicit model: Model) extends Frame{
	title = "AI"

	contents = new AgentTypesUI

	model.startStream.foreach{ _ => Swing.onEDT{
		contents = new ProgressUI
	}}

	override def closeOperation(): Unit = {
		visible = true
		if(OK_OPTION == showConfirmDialog(
			peer,
			"Are you sure you want to exit?",
			"Application Exit",
			YES_NO_OPTION, WARNING_MESSAGE)){

			sys.exit()
		}
	}
}