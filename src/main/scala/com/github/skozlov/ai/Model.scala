package com.github.skozlov.ai

import com.github.skozlov.ai.Matrix.Coordinates
import com.github.skozlov.ai.World.Temperature
import rx.lang.scala.subjects.PublishSubject
import rx.lang.scala.{Observable, Subject}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.FiniteDuration

class Model(minSize: Int, maxSize: Int, tactMinDuration: FiniteDuration, tactMaxDuration: FiniteDuration){
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

	private val _worlds = new ListBuffer[World]
	def worlds(): List[World] = _worlds.toList

	def addAgent(agent: Agent): Unit = {
		val world = new World(fields, agent, agentInitCoordinates)
		_worlds += world
		new WorldUI(world).visible = true
	}

	private var run = false

	def start(): Unit ={
		run = true
		startSubject.onNext()
		Future{
			while (run){
				val deadline = tactMinDuration.fromNow
				Await.ready(Future.traverse(_worlds){world => Future{world.tact()}}.map{_ =>
					val rest = deadline.timeLeft.toMillis
					if(rest > 0) Thread.sleep(rest)
					_tactNumber.value = _tactNumber.value + 1
				}, tactMaxDuration)
			}
		}
	}

	def stop(): Unit ={
		run = false
	}

	private val startSubject: Subject[Unit] = PublishSubject()
	val startStream: Observable[Unit] = startSubject

	private val _tactNumber = Property(0)
	val tactNumber: Observable[Int] = _tactNumber
}