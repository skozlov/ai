package com.github.skozlov.commons.scala.lang

object Bit extends Enumeration{
	type Bit = Value

	val `0`, `1` = Value

	def fromInt(number: Int): Seq[Bit] = {
		def fromInt(shiftedNumber: Int, bits: List[Bit]): List[Bit] = if(bits.size == 32) bits else {
			val bit = shiftedNumber & 1 match {
				case 1 => `1`
				case 0 => `0`
			}
			fromInt(shiftedNumber >> 1, bit :: bits)
		}

		fromInt(number, Nil)
	}
}
