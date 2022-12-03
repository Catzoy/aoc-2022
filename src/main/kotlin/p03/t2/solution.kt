package p03.t2

fun solution(data: List<String>): Int {
    return data.asSequence().chunked(3).map { lines ->
        val (e1, e2, e3) = lines.map { it.toSet() }
        val common = e1.intersect(e2).intersect(e3).first()
        ('a'..'z').indexOf(common).takeIf { it >= 0 }?.let { it + 1 }
            ?: ('A'..'Z').indexOf(common).takeIf { it >= 0 }?.let { it + 27 }
            ?: throw Exception("Unknown symbol $common")
    }.sum()
}
