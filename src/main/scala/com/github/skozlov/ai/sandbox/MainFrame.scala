package com.github.skozlov.ai.sandbox

import javax.swing.JOptionPane._
import javax.swing.filechooser.FileNameExtensionFilter

import scala.swing._

class MainFrame(implicit model: Model) extends Frame{
	title = "AI"

	menuBar = new MenuBar{
		contents += new Menu("File"){
			contents += new Menu("Export"){
				enabled = false
				model.stopStream.foreach{_ => Swing.onEDT{
					enabled = true
				}}

				val csvFileChooser = new FileChooser(){
					fileFilter = new FileNameExtensionFilter("CSV", "csv")
				}
				contents += new MenuItem(Action("CSV"){
					csvFileChooser.showSaveDialog(null) match {
						case FileChooser.Result.Approve => model.exportAsCsv(csvFileChooser.selectedFile)
					}
				})
			}
		}
	}

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