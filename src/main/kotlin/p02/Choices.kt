package p02

sealed interface Choice {
    val value: Int
    fun play(againts: Choice): GameResult
    fun predict(result: GameResult): Choice
}

object Rock : Choice {
    override val value: Int = 1

    override fun play(againts: Choice): GameResult {
        return when (againts) {
            Rock -> GameResult.Draw
            Paper -> GameResult.Loss
            Scissors -> GameResult.Win
        }
    }

    override fun predict(result: GameResult): Choice {
        return when (result) {
            GameResult.Loss -> Scissors
            GameResult.Draw -> Rock
            GameResult.Win -> Paper
        }
    }
}

object Paper : Choice {
    override val value: Int = 2

    override fun play(againts: Choice): GameResult {
        return when (againts) {
            Paper -> GameResult.Draw
            Rock -> GameResult.Win
            Scissors -> GameResult.Loss
        }
    }

    override fun predict(result: GameResult): Choice {
        return when (result) {
            GameResult.Loss -> Rock
            GameResult.Draw -> Paper
            GameResult.Win -> Scissors
        }
    }
}

object Scissors : Choice {
    override val value: Int = 3

    override fun play(againts: Choice): GameResult {
        return when (againts) {
            Paper -> GameResult.Win
            Rock -> GameResult.Loss
            Scissors -> GameResult.Draw
        }
    }

    override fun predict(result: GameResult): Choice {
        return when (result) {
            GameResult.Loss -> Paper
            GameResult.Draw -> Scissors
            GameResult.Win -> Rock
        }
    }
}