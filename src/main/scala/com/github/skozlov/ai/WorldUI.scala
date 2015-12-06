package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates

import scala.concurrent.Future
import scala.swing._
import scala.concurrent.ExecutionContext.Implicits.global

class WorldUI(private var world: World) extends Frame{
	title = world.agent.getClass.getName

	private val cells = new MatrixBuilder[Label](
		rowsCount = world.fields.rowsCount,
		columnsCount = world.fields.columnsCount)

	contents = new BorderPanel{
		val fields = new GridPanel(rows0 = world.fields.rowsCount, cols0 = world.fields.columnsCount)

		for(
			row <- 0 to (world.fields.rowsCount - 1);
			column <- 0 to (world.fields.columnsCount - 1);
			coordinates = Coordinates(row, column);
			cell = new Label(world.fields(coordinates).toString)){

			cells(coordinates) = cell
			fields.contents += cell
		}

		cells(world.agentCoordinates).text = "@"

		layout(fields) = BorderPanel.Position.Center
	}

	def start(): Unit ={
		visible = true
		Future{
			while(true){
				Thread.sleep(1000)
				val agentOldCoordinates = world.agentCoordinates
				world = world.tact()
				cells(agentOldCoordinates).text = world.fields(agentOldCoordinates).toString
				cells(world.agentCoordinates).text = "@"
			}
		}
	}
}