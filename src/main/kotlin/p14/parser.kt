package p14

val LINE = Regex("(\\d+,\\d+)(:? -> )?")

data class Point(val x: Int, val y: Int)

fun Pair<Int, Int>.toPoint() = Point(x = first, y = second)
enum class LineType {
    Horizontal,
    Vertical,
}

data class Line(
    val type: LineType,
    val origin: Point,
    val length: Int,
)

fun parse(input: List<String>): List<Line> {
    return input.flatMap { line ->
        LINE.findAll(line)
            .map { match ->
                val (x, y) = match.groupValues[1].split(",")
                x.toInt() to y.toInt()
            }
            .zipWithNext { a, b ->
                when {
                    a.first == b.first -> {
                        val (origin, length) = if (a.second < b.second) {
                            a to (b.second - a.second)
                        } else {
                            b to (a.second - b.second)
                        }
                        Line(
                            type = LineType.Vertical,
                            origin = origin.toPoint(),
                            length = length
                        )
                    }

                    a.second == b.second -> {
                        val (origin, length) = if (a.first < b.first) {
                            a to (b.first - a.first)
                        } else {
                            b to (a.first - b.first)
                        }
                        Line(
                            type = LineType.Horizontal,
                            origin = origin.toPoint(),
                            length = length
                        )
                    }

                    else -> throw Exception("Invalid data!")
                }
            }
    }
}
