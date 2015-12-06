package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates
import com.github.skozlov.ai.World._
import Agent.Action._

class World(val fields: Matrix[Temperature], agent: Agent, val agentCoordinates: Matrix.Coordinates){
	require(fields containsCellWithCoordinates agentCoordinates)

	def tact(): World = {
		val newCoordinates = agent.react(fields(agentCoordinates)) match {
			case North => agentCoordinates.northNeighborCoordinates
			case South => agentCoordinates.southNeighborCoordinates(rowsCount = fields.rowsCount)
			case West => agentCoordinates.westNeighborCoordinates
			case East => agentCoordinates.eastNeighborCoordinates(columnsCount = fields.columnsCount)
			case Stand => agentCoordinates
		}
		new World(fields, agent, newCoordinates)
	}
}

object World{
	type Temperature = Int

	def random(minSize: Int, maxSize: Int, agent: Agent): World = {
		require(minSize >= 1)
		require(maxSize >= minSize)

		val size = Random.elementFrom(minSize to maxSize)
		val fields: Matrix[Temperature] = {
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
		val agentCoordinates = Coordinates.random(rowsCount = size, columnsCount = size)
		new World(fields, agent, agentCoordinates)
	}
}