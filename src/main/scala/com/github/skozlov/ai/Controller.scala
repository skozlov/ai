package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates
import com.github.skozlov.ai.World.Temperature

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

class Controller(minSize: Int, maxSize: Int, tactMinDuration: FiniteDuration){
	require(minSize >= 1)
	require(minSize <= maxSize)

	private val size = Random.elementFrom(minSize to maxSize)

	private val fields: Matrix[Temperature] = {
		val peakCoordinates: Coordinates = Coordinates.random(rowsCount = size, columnsCount = size)
		val peakTemperature: Temperature = {
			val maxHorizontalDistance: Int = Math.max(peakCoordinates.column, size - 1 - peakCoordinates.column)
			val maxVerticalDistance: Int = Math.max(peakCoordinates.row, size - 1 - peakCoordinates.row)
			maxHorizontalDistance + maxVerticalDistance
		}
		val fieldsBuilder = new MatrixBuilder[Int](size, size)
		for(
			row <- 0 to size-1;
			column <- 0 to size-1;
			coordinate = Coordinates(row, column)){
			fieldsBuilder(coordinate) = peakTemperature - coordinate.distanceTo(peakCoordinates)
		}
		fieldsBuilder.toMatrix
	}
	private val agentInitCoordinates = Coordinates.random(rowsCount = size, columnsCount = size)

	private val worlds = new ListBuffer[World]

	def addAgent(agent: Agent): Unit = {
		val world = new World(fields, agent, agentInitCoordinates)
		worlds += world
		new WorldUI(world).visible = true
	}

	def start(): Unit ={
		Future{
			def tact(): Unit ={
				val deadline = tactMinDuration.fromNow
				Future.traverse(worlds){world => Future{world.tact()}}.map{_ =>
					val rest = deadline.timeLeft.toMillis
					if(rest > 0) Thread.sleep(rest)
					tact()
				}
			}
			tact()
		}
	}
}