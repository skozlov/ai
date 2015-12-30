package com.github.skozlov.ai.sandbox

import com.github.skozlov.ai.sandbox.Agent.Action.Action
import com.github.skozlov.ai.sandbox.ChaoticAgent._
import com.github.skozlov.ai.sandbox.World.Temperature
import com.github.skozlov.commons.scala.random.Random

class ChaoticAgent extends Agent {
	override def react(temperature: Temperature): Action = Random.elementFrom(Actions)
}

object ChaoticAgent{
	val Actions = Agent.Action.values.toList
}