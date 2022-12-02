package p02.t1

import p02.Paper
import p02.Rock
import p02.Scissors

fun solution(data: List<String>): Int {
    val opponents = mapOf(
        "A" to Rock,
        "B" to Paper,
        "C" to Scissors
    )
    val yours = mapOf(
        "X" to Rock,
        "Y" to Paper,
        "Z" to Scissors
    )
    return data.asSequence()
        .map { line ->
            val (opponent, you) = line.split(" ")
            opponents.getValue(opponent) to yours.getValue(you)
        }
        .map { (opponent, you) ->
            you.value + you.play(opponent).score
        }
        .sum()
}
