package com.github.skozlov.ai

import java.awt.BorderLayout
import java.awt.event.{MouseAdapter, MouseEvent}
import javax.swing.{DefaultListModel, JList}

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, Button, MainFrame}

class AgentTypesUI(controller: Controller) extends MainFrame{
	contents = new BorderPanel{
		tooltip = "Double-click to create a world with an agent of selected type"

		private val startButton = new Button("Start") {
			enabled = false
		}
		listenTo(startButton)
		reactions += {
			case ButtonClicked(_) =>
				startButton.enabled = false
				controller.start()
		}
		layout(startButton) = BorderPanel.Position.South

		peer.add(
			new JList[Class[_ <: Agent]] {
				val model = new DefaultListModel[Class[_ <: Agent]]() {
					Agent.AgentTypes foreach addElement
				}
				setModel(model)
				addMouseListener(new MouseAdapter {
					override def mouseClicked(e: MouseEvent): Unit = {
						if (e.getClickCount >= 2) {
							val index = locationToIndex(e.getPoint)
							val agentType = model.remove(index)
							controller addAgent agentType.newInstance()
							startButton.enabled = true
						}
					}
				})
			},
			BorderLayout.CENTER
		)
	}
}