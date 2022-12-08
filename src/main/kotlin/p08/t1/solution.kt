package p08.t1

fun solution(data: List<String>): Int {
    val heights = data.map { line -> line.map { it.digitToInt() } }
    var visibles = 0
    for (i in heights.indices) {
        val row = heights[i]
        for (j in row.indices) {
            val currentHeight = row[j]
            val rights = row.takeLast(row.lastIndex - j)
            if (rights.all { it < currentHeight }) {
                visibles += 1
                continue
            }

            val lefts = row.take(j)
            if (lefts.all { it < currentHeight }) {
                visibles += 1
                continue
            }

            val tops = heights.take(i)
            if (tops.all { it[j] < currentHeight }) {
                visibles += 1
                continue
            }

            val bots = heights.takeLast(heights.lastIndex - i)
            if (bots.all { it[j] < currentHeight }) {
                visibles += 1
                continue
            }
        }
    }
    return visibles
}
