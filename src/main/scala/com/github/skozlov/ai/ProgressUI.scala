package com.github.skozlov.ai

import javax.swing.table.DefaultTableModel

import scala.swing.BorderPanel.Position._
import scala.swing._

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

	layout(tactNumber) = North
	layout(new ScrollPane(table)) = Center
}
