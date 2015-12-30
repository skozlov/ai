package com.github.skozlov.ai.sandbox

import com.github.skozlov.ai.sandbox.World.Temperature

import scala.collection.mutable.{Set => MutableSet}
import com.github.skozlov.ai.sandbox.Agent.Action._

class SpecializedAgent extends Agent {
	private val directionPairs = Array((West, East), (North, South))
	private var currentDirectionPairIndex = 0
	private var currentDirection = directionPairs(0)._1
	private val completedDirections = MutableSet.empty[Action]
	private var previousTemperature: Option[Temperature] = None
	private var lastAction: Option[Action] = None

	override def react(temperature: Temperature): Action = {
		val action: Action = {
			if(currentDirectionPairIndex >= directionPairs.length){
				Stand
			} else {
				lastAction match {
					case Some(lastAct) =>
						if(currentDirection == lastAct){
							if(temperature > previousTemperature.get){
								completedDirections += oppositeDirection()
								currentDirection
							} else if(temperature == previousTemperature.get) {
								onCompleteCurrentDirection()
							} else {
								oppositeDirection()
							}
						} else {
							onCompleteCurrentDirection()
						}
					case None => currentDirection
				}
			}
		}
		lastAction = Some(action)
		previousTemperature = Some(temperature)
		action
	}

	private def onCompleteCurrentDirection(): Action = {
		completedDirections += currentDirection
		val reverseDirection = oppositeDirection()
		val nextDirection = if (completedDirections contains reverseDirection) {
			currentDirectionPairIndex += 1
			if (currentDirectionPairIndex >= directionPairs.length)
				Stand
			else
				directionPairs(currentDirectionPairIndex)._1
		} else reverseDirection
		currentDirection = nextDirection
		nextDirection
	}

	private def oppositeDirection(): Action = {
		val currentDirectionPair = directionPairs(currentDirectionPairIndex)
		if(currentDirection == currentDirectionPair._1) currentDirectionPair._2 else currentDirectionPair._1
	}
}