package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates
import Math._
import util.{Random => Rand}

class Matrix[+C: Manifest](rows: Array[Array[C]]){
	require(rows.nonEmpty)

	private val _rows: Array[Array[_]] = rows map (_.clone())

	val rowsCount: Int = rows.length

	val columnsCount: Int = _rows(0).length

	for(row <- _rows){
		require(row.length == columnsCount)
	}

	def containsCellWithCoordinates(coordinates: Coordinates): Boolean = {
		coordinates.row < rowsCount && coordinates.column < columnsCount
	}

	def apply(coordinates: Coordinates): C = {
		require(containsCellWithCoordinates(coordinates))
		_rows(coordinates.row)(coordinates.column).asInstanceOf[C]
	}
}

class MatrixBuilder[C: Manifest](rowsCount: Int, columnsCount: Int){
	require(rowsCount > 0)
	require(columnsCount > 0)

	private val rows: Array[Array[C]] = Array.fill(rowsCount)(new Array[C](columnsCount))

	def apply(coordinates: Coordinates): C = {
		require(containsCellWithCoordinates(coordinates))
		rows(coordinates.row)(coordinates.column)
	}

	def update(coordinates: Coordinates, value: C): Unit ={
		require(containsCellWithCoordinates(coordinates))
		rows(coordinates.row)(coordinates.column) = value
	}

	private def containsCellWithCoordinates(coordinates: Coordinates): Boolean = {
		coordinates.row < rowsCount && coordinates.column < columnsCount
	}

	def toMatrix: Matrix[C] = new Matrix[C](rows)
}

object Matrix{
	case class Coordinates(row: Int, column: Int){
		require(row >= 0)
		require(column >= 0)

		lazy val northNeighborCoordinates: Coordinates = if(row == 0) this else Coordinates(row - 1, column)

		def southNeighborCoordinates(rowsCount: Int): Coordinates = {
			require(row < rowsCount)
			if(row == rowsCount - 1) this else Coordinates(row + 1, column)
		}

		lazy val westNeighborCoordinates: Coordinates = if(column == 0) this else Coordinates(row, column - 1)

		def eastNeighborCoordinates(columnsCount: Int): Coordinates = {
			require(column < columnsCount)
			if(column == columnsCount - 1) this else Coordinates(row, column + 1)
		}

		def distanceTo(other: Coordinates): Int = abs(this.row - other.row) + abs(this.column - other.column)
	}

	object Coordinates{
		def random(rowsCount: Int, columnsCount: Int): Coordinates =
			Coordinates(Rand.nextInt(rowsCount), Rand.nextInt(columnsCount))
	}
}