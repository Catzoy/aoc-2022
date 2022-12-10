package p10.t1

val ADDX_REGEX = Regex("addx (-?\\d+)")

sealed interface Instruction {
    object NoOp : Instruction
    data class AddX(val x: Int) : Instruction
}

fun solution(data: List<String>): Int {
    val instructions = data.asSequence().map { line ->
        ADDX_REGEX.find(line)?.let { match ->
            Instruction.AddX(match.groupValues[1].toInt())
        } ?: Instruction.NoOp
    }

    val cyclesToValues = mutableMapOf<Int, Int?>(
        20 to null,
        60 to null,
        100 to null,
        140 to null,
        180 to null,
        220 to null,
    )

    var cycle = 0
    var x = 1
    for (instruction in instructions) {
        val cycles = when (instruction) {
            Instruction.NoOp -> 1
            is Instruction.AddX -> 2
        }
        for (i in 0 until cycles) {
            cycle += 1
            if (cyclesToValues.containsKey(cycle)) {
                cyclesToValues[cycle] = x
            }
        }
        if (instruction is Instruction.AddX) {
            x += instruction.x
        }
    }

    return cyclesToValues.entries.sumOf { (key, value) ->
        key * (value ?: throw IllegalStateException("Required cycle was not reached"))
    }
}
