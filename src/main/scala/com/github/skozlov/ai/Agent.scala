package com.github.skozlov.ai

import java.lang.reflect.Modifier

import com.github.skozlov.ai.World.Temperature
import Agent._
import org.reflections.Reflections
import scala.collection.JavaConverters._

trait Agent{
	import Action.Action
	
	def react(temperature: Temperature): Action
}

object Agent{
	type Pleasure = Temperature

	object Action extends Enumeration{
		type Action = Value
		val North, South, West, East, Stand = Value
	}

	lazy val AgentTypes: List[Class[_ <: Agent]] =
		new Reflections().getSubTypesOf(classOf[Agent]).asScala.toList
			.filter{agentType =>
				!agentType.isInterface &&
					!Modifier.isAbstract(agentType.getModifiers) &&
					agentType.getConstructors.exists{_.getParameterCount == 0}
			}
}