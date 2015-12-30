package com.github.skozlov.ai.sandbox

import javax.swing.table.DefaultTableModel

import scala.swing.BorderPanel.Position._
import scala.swing._
import scala.swing.event.ButtonClicked

class ProgressUI(implicit model: Model) extends BorderPanel{
	private val tactNumber = new Label

	model.tactNumber.foreach{ n => Swing.onEDT{
		tactNumber.text = "Tact: " + n
	}}

	private val worlds = model.worlds()
	private val agentNames = worlds map {w => w.agent.getClass.getName}

	private val table = new Table{
		this.model = new DefaultTableModel(
			Array("Agent type", "Total pleasure").asInstanceOf[Array[Object]],
			worlds.size){

			for((name, i) <- agentNames.zipWithIndex){
				setValueAt(name, i, 0)
			}

			for(
				(world, i) <- worlds.zipWithIndex;
				totalPleasure <- world.totalPleasure
			){
				Swing.onEDT{
					setValueAt(totalPleasure, i, 1)
				}
			}
		}
	}

	private val stop = new Button("Stop")
	listenTo(stop)
	reactions += {
		case ButtonClicked(b) if b eq stop =>
			stop.enabled = false
			model.stop()
	}

	layout(tactNumber) = North
	layout(new ScrollPane(table)) = Center
	layout(stop) = South
}
