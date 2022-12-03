package p03.t1

fun solution(data: List<String>): Int {
    return data.asSequence()
        .map { line ->
            val first = line.take(line.length / 2).toSet()
            val second = line.takeLast(line.length / 2).toSet()
            val common = first.intersect(second).first()
            ('a'..'z').indexOf(common).takeIf { it >= 0 }?.let { it + 1 }
                ?: ('A'..'Z').indexOf(common).takeIf { it >= 0 }?.let { it + 27 }
                ?: throw Exception("Unknown symbol $common")
        }
        .sum()
}
