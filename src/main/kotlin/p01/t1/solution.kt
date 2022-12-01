package p01.t1

fun solution(data: List<String>): Int {
    return data.fold(mutableListOf(0)) { acc, line ->
        val calories = line.toIntOrNull()
        if (calories != null) {
            acc[acc.lastIndex] = acc.last() + calories
        } else {
            acc.add(0)
        }
        acc
    }.max()
}
