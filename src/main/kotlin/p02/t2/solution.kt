package p02.t2

import p02.GameResult
import p02.Paper
import p02.Rock
import p02.Scissors

fun solution(data: List<String>): Int {
    val opponents = mapOf(
        "A" to Rock,
        "B" to Paper,
        "C" to Scissors
    )
    val results = mapOf(
        "X" to GameResult.Loss,
        "Y" to GameResult.Draw,
        "Z" to GameResult.Win
    )
    return data.asSequence()
        .map { line ->
            val (opponent, result) = line.split(" ")
            opponents.getValue(opponent) to results.getValue(result)
        }
        .map { (opponent, result) ->
            val you = opponent.predict(result)
            you.value + result.score
        }
        .sum()
}
