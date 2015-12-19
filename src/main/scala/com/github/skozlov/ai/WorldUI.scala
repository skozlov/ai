package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates

import scala.swing._

class WorldUI(world: World) extends Frame{
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

		layout(fields) = BorderPanel.Position.Center
	}

	private var previousAgentCoordinates: Option[Matrix.Coordinates] = None

	for(
		agentCoordinates <- world.agentCoordinatesStream;
		cell = cells(agentCoordinates)
	){
		Swing.onEDT{
			for(oldAgentCoordinates <- previousAgentCoordinates){
				cells(oldAgentCoordinates).text = world.fields(oldAgentCoordinates).toString
			}
			previousAgentCoordinates = Some(agentCoordinates)
			cell.text = "@"
		}
	}
}