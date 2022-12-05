package p05.t1

enum class ParsingState {
    Crates,
    Empty,
    Move,
}

val CRATES_REGEX = Regex("(?:(?:\\s\\s\\s)|(?:\\[([A-Z])]))\\s?")
val STACKS_REGEX = Regex("(?: \\d  ?)+")
val MOVE_REGEX = Regex("move (\\d+) from (\\d) to (\\d)")
fun solution(data: List<String>): String {
    var state = ParsingState.Crates
    val stacks = mutableListOf<MutableList<String>>()
    for (line in data) {
        if (state == ParsingState.Crates) {
            if (STACKS_REGEX.find(line) != null) {
                state = ParsingState.Empty
                continue
            }
            for ((i, match) in CRATES_REGEX.findAll(line).withIndex()) {
                if (stacks.size <= i) {
                    stacks.add(mutableListOf())
                }
                val (_, letter) = match.groupValues
                if (letter.isNotEmpty()) {
                    stacks[i].add(letter)
                }
            }
        }
        if (state == ParsingState.Empty) {
            state = ParsingState.Move
            continue
        }
        if (state == ParsingState.Move) {
            val (amount, from, to) = MOVE_REGEX.find(line)!!.groupValues.drop(1).map { it.toInt() }
            val fromIdx = from - 1
            val toIdx = to - 1
            for (i in 0 until amount) {
                val elem = stacks[fromIdx].removeFirst()
                stacks[toIdx].add(0, elem)
            }
        }
    }

    return stacks.joinToString(separator = "") { it.first() }
}
