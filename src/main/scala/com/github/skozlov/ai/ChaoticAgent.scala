package com.github.skozlov.ai

import com.github.skozlov.ai.Agent.Action.Action
import com.github.skozlov.ai.World.Temperature
import ChaoticAgent._

class ChaoticAgent extends Agent {
	override def react(temperature: Temperature): Action = Random.elementFrom(Actions)
}

object ChaoticAgent{
	val Actions = Agent.Action.values.toList
}