package p04.t2

fun solution(data: List<String>): Int {
    val rangesRegex = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
    return data.asSequence()
        .map { line ->
            val match = rangesRegex.find(line) ?: throw Exception("Line in wrong format: $line")
            match.groupValues.asSequence().drop(1).map { it.toInt() }.toList()
        }.map { (start1, end1, start2, end2) ->
            start1..end1 to start2..end2
        }.count { (range1, range2) ->
            !(range1.last < range2.first || range2.last < range1.first)
        }
}
