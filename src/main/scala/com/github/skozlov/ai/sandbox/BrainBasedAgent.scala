package com.github.skozlov.ai.sandbox

import com.github.skozlov.ai.Brain$
import com.github.skozlov.ai.brain.Brain
import Brain.{Inputs, Outputs}
import com.github.skozlov.ai.sandbox.Agent.Action._
import com.github.skozlov.ai.sandbox.BrainBasedAgent._
import com.github.skozlov.ai.sandbox.World.Temperature
import com.github.skozlov.commons.scala.lang.Bit

class BrainBasedAgent extends Agent{
	private val brain = new Brain(inputsCount = InputsCount, outputsCount = OutputsCount)

	override protected def react(temperature: Temperature): Action = {
		require(temperature >= 0)

		val inputs = temperatureToInputs(temperature)
		val outputs = brain.react(inputs)
		outputsToAction(outputs)
	}

	private def temperatureToInputs(temperature: Temperature): Inputs = Bit.fromInt(temperature).takeRight(InputsCount)

	private def outputsToAction(outputs: Outputs): Action = {
		import Brain.OutputSignal._

		outputs match {
			case `1` :: Seq(_) => Stand
			case Seq(`0`, `0`, `0`) => North
			case Seq(`0`, `0`, `1`) => South
			case Seq(`0`, `1`, `0`) => West
			case Seq(`0`, `1`, `1`) => East
		}
	}
}

object BrainBasedAgent{
	private val InputsCount = 5
	private val OutputsCount = 3
}
