package com.github.skozlov.ai.brain

import com.github.skozlov.ai.brain.Brain._
import com.github.skozlov.commons.scala.combinatorics.Tuples
import com.github.skozlov.commons.scala.lang.Bit
import com.github.skozlov.commons.scala.random.Random

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Future}

class Brain(inputsCount: Int, outputsCount: Int)(pleasureCalculator: (Inputs) => Pleasure) {
	require(inputsCount > 0)
	require(outputsCount > 0)

	private val memory = new Memory

	private lazy val possibleOutputs: Seq[Outputs] = {
		import OutputSignal._
		Tuples.all(Seq(`0`, `1`))(outputsCount)
	}

	def react(inputs: Inputs)(implicit maxDuration: FiniteDuration): Outputs = {
		require(inputs.size == inputsCount)
		memory.onPerception(inputs)
		val predictions: Iterable[(Outputs, Pleasure)] = Await.result(
			Future.traverse(possibleOutputs)(outputs => Future{(outputs, predictPleasure(outputs))}),
			maxDuration)
		val maxPleasure = predictions.maxBy{case (outputs, pleasure) => pleasure}._2
		val selectedAction: Outputs = Random.elementFrom(
			predictions filter {case (outputs, pleasure) => pleasure == maxPleasure}
		)._1
		memory.onAction(selectedAction)
		selectedAction
	}

	private def predictPleasure(outputs: Outputs): Pleasure = ???
}

object Brain{
	type InputSignal = Bit.Bit
	val InputSignal = Bit
	type Inputs = Seq[InputSignal]

	type OutputSignal = Bit.Bit
	val OutputSignal = Bit
	type Outputs = Seq[OutputSignal]

	private type Pleasure = Double
}