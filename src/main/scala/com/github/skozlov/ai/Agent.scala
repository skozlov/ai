package com.github.skozlov.ai

import com.github.skozlov.ai.World.Temperature
import Agent._
import Action.Action

trait Agent{
	def react(temperature: Temperature): Action
}

object Agent{
	object Action extends Enumeration{
		type Action = Value
		val North, South, West, East, Stand = Value
	}
}