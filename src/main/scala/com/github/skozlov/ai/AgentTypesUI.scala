package com.github.skozlov.ai

import scala.swing.ListView
import scala.swing.event.MouseClicked

class AgentTypesUI extends ListView[Class[_ <: Agent]](Agent.AgentTypes){
	tooltip = "Double-click to create a world with an agent of selected type"

	listenTo(mouse.clicks)

	reactions += {
		case MouseClicked(_, point, _, 2, _) =>
			val agentType = Agent.AgentTypes(peer.locationToIndex(point))
			val agent = agentType.newInstance()
			val world = World.random(minSize = 3, maxSize = 10, agent)
			new WorldUI(world).start()
	}
}