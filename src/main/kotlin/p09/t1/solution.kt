package p09.t1

import kotlin.math.absoluteValue

fun List<String>.parseInput(): Sequence<Pair<String, Int>> {
    val regex = Regex("([UDLR]) (\\d+)")
    return asSequence()
        .map { line ->
            regex.find(line)!!
        }.map { match ->
            val letter = match.groupValues[1]
            val times = match.groupValues[2].toInt()
            (letter to times)
        }
}

fun solution(data: List<String>): Int {
    var headPosition = 0 to 0
    var tailPosition = 0 to 0
    val matrix = mutableListOf(mutableListOf(true))
    for ((letter, times) in data.parseInput()) {
        repeat(times) {
            var (headX, headY) = headPosition
            val rowSize = matrix.first().size
            when (letter) {
                "U" -> {
                    headY -= 1
                    if (headY == -1) {
                        matrix.add(0, MutableList(rowSize) { false })
                        headY = 0
                        tailPosition = tailPosition.let { (x, y) ->
                            x to (y + 1)
                        }
                    }
                }

                "D" -> {
                    headY += 1
                    if (headY == matrix.size) {
                        matrix.add(MutableList(rowSize) { false })
                    }
                }

                "L" -> {
                    headX -= 1
                    if (headX == -1) {
                        for (row in matrix) {
                            row.add(0, false)
                        }
                        headX = 0
                        tailPosition = tailPosition.let { (x, y) ->
                            (x + 1) to y
                        }
                    }
                }

                "R" -> {
                    headX += 1
                    if (headX == rowSize) {
                        for (row in matrix) {
                            row.add(false)
                        }
                    }
                }
            }
            headPosition = headX to headY

            var (tailX, tailY) = tailPosition
            val (diffX, diffY) = (headX - tailX) to (headY - tailY)

            when (diffY) {
                2 -> tailY += 1
                -2 -> tailY -= 1
                1 -> {
                    if (diffX.absoluteValue == 2) {
                        tailY += 1
                    }
                }

                -1 -> {
                    if (diffX.absoluteValue == 2) {
                        tailY -= 1
                    }
                }
            }
            when (diffX) {
                2 -> tailX += 1
                -2 -> tailX -= 1
                1 -> {
                    if (diffY.absoluteValue == 2) {
                        tailX += 1
                    }
                }

                -1 -> {
                    if (diffY.absoluteValue == 2) {
                        tailX -= 1
                    }
                }
            }
            tailPosition = tailX to tailY
            matrix[tailY][tailX] = true
        }
    }

    return matrix.fold(0) { acc, row ->
        acc + row.count { it }
    }
}
