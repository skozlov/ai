package com.github.skozlov.ai.sandbox

import java.awt.BorderLayout
import java.awt.event.{MouseAdapter, MouseEvent}
import javax.swing.{DefaultListModel, JList}

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, Button}

class AgentTypesUI(implicit model: Model) extends BorderPanel{
	tooltip = "Double-click to create a world with an agent of selected type"

	private val startButton = new Button("Start") {
		enabled = false
	}
	listenTo(startButton)
	reactions += {
		case ButtonClicked(b) if b eq startButton =>
			startButton.enabled = false
			model.start()
	}
	layout(startButton) = BorderPanel.Position.South

	peer.add(new JList[Class[_ <: Agent]] {
		val listModel = new DefaultListModel[Class[_ <: Agent]]() {
			Agent.AgentTypes foreach addElement
		}
		setModel(listModel)
		addMouseListener(new MouseAdapter {
			override def mouseClicked(e: MouseEvent): Unit = {
				if (e.getClickCount >= 2) {
					val index = locationToIndex(e.getPoint)
					val agentType = listModel.remove(index)
					model addAgent agentType.newInstance()
					startButton.enabled = true
				}
			}
		})
	}, BorderLayout.CENTER)
}
