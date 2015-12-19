package com.github.skozlov.ai

import rx.lang.scala.Observable
import rx.lang.scala.subjects.BehaviorSubject

class Property[T](initValue: T){
	private var _value = initValue
	private val stream = BehaviorSubject(initValue)

	def value: T = _value

	def value_=(newValue: T): Unit ={
		if(_value != newValue){
			_value = newValue
			stream.onNext(newValue)
		}
	}
}

object Property{
	def apply[T](initValue: T): Property[T] = new Property[T](initValue)

	implicit def property2Observable[T](property: Property[T]): Observable[T] = property.stream
}