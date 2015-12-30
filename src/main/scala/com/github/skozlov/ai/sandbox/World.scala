package com.github.skozlov.ai.sandbox

import com.github.skozlov.ai.sandbox.Agent.Pleasure
import com.github.skozlov.ai.sandbox.Agent.Action._
import com.github.skozlov.ai.sandbox.World.Temperature
import com.github.skozlov.commons.scala.collections.Matrix.Coordinates
import com.github.skozlov.commons.scala.collections.{Matrix, MatrixBuilder}
import com.github.skozlov.commons.scala.random.Random
import com.github.skozlov.commons.scala.reactivex.Property
import rx.lang.scala.Observable

class World(val fields: Matrix[Temperature], val agent: Agent, agentInitCoordinates: Matrix.Coordinates){
	require(fields containsCellWithCoordinates agentInitCoordinates)
	private val agentCoordinates = Property(agentInitCoordinates)
	private val _totalPleasure = Property(fields(agentInitCoordinates))

	def tact() {
		agentCoordinates.value = (agent.affect(fields(agentCoordinates.value)) match {
			case North => agentCoordinates.value.northNeighborCoordinates
			case South => agentCoordinates.value.southNeighborCoordinates(rowsCount = fields.rowsCount)
			case West => agentCoordinates.value.westNeighborCoordinates
			case East => agentCoordinates.value.eastNeighborCoordinates(columnsCount = fields.columnsCount)
			case Stand => Some(agentCoordinates.value)
		}).getOrElse(agentCoordinates.value)
		_totalPleasure.value = _totalPleasure.value + fields(agentCoordinates.value)
	}

	val agentCoordinatesStream: Observable[Matrix.Coordinates] = agentCoordinates
	val pleasure: Observable[Pleasure] = agentCoordinatesStream map {fields(_)}
	val totalPleasure: Observable[Pleasure] = _totalPleasure
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