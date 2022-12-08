package p08.t2


fun List<Int>.countVisible(maxHeight: Int): Int {
    var counter = 0
    for (height in this) {
        counter += 1
        if (height >= maxHeight) {
            break
        }
    }
    return counter
}

fun solution(data: List<String>): Int {
    val heights = data.map { line -> line.map { it.digitToInt() } }
    var highestScore = 0
    for (i in heights.indices) {
        val row = heights[i]
        for (j in row.indices) {
            val currentHeight = row[j]
            var score = row
                .takeLast(row.lastIndex - j)
                .countVisible(maxHeight = currentHeight)

            score *= row
                .take(j)
                .asReversed()
                .countVisible(maxHeight = currentHeight)

            score *= heights
                .take(i)
                .map { it[j] }
                .asReversed()
                .countVisible(maxHeight = currentHeight)

            score *= heights
                .takeLast(heights.lastIndex - i)
                .map { it[j] }
                .countVisible(maxHeight = currentHeight)

            if (highestScore < score) {
                highestScore = score
            }
        }
    }
    return highestScore
}