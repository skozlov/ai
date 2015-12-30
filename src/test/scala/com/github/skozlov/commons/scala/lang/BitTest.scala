package com.github.skozlov.commons.scala.lang

import org.scalatest.{Matchers, FlatSpec}
import Bit._

class BitTest extends FlatSpec with Matchers {
	"Sequence of bits" should "be constructed from int" in {
		Bit.fromInt(5) shouldBe Seq.fill(29)(`0`) ++ Seq(`1`, `0`, `1`)
	}

	it should "be constructed from negative int" in {
		Bit.fromInt(-1) shouldBe Seq.fill(32)(`1`)
	}
}
