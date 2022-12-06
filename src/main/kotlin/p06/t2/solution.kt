package p06.t2

fun solution(data: List<String>): Int {
    val line = data.first()
    val symbols = mutableListOf<Char>()
    var allDifferentIndex: Int? = null
    for ((i, char) in line.withIndex()) {
        val charIx = symbols.indexOf(char)
        if (charIx >= 0) {
            repeat(charIx + 1) {
                symbols.removeAt(0)
            }
        }
        symbols.add(char)
        if (symbols.size == 14) {
            allDifferentIndex = i
            break
        }
    }
    return allDifferentIndex!! + 1
}
