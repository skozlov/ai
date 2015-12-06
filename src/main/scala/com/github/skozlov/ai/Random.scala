package com.github.skozlov.ai

object Random {
	def elementFrom[A](sequence: Seq[A]): A = sequence(util.Random.nextInt(sequence.size))
}
