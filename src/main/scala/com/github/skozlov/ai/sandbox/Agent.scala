package com.github.skozlov.ai.sandbox

import java.lang.reflect.Modifier

import com.github.skozlov.ai.sandbox.Agent.Action.Action
import com.github.skozlov.ai.sandbox.Agent.Pleasure
import com.github.skozlov.ai.sandbox.World.Temperature
import org.reflections.Reflections

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

trait Agent{

	private val _totalPleasureHistory: ListBuffer[Pleasure] = ListBuffer()

	def totalPleasureHistory(): List[Pleasure] = _totalPleasureHistory.toList

	def affect(temperature: Temperature): Action = {
		val previous = _totalPleasureHistory.lastOption getOrElse 0
		_totalPleasureHistory append (previous + temperature)
		react(temperature)
	}
	
	protected def react(temperature: Temperature): Action
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