package p10.t2

val ADDX_REGEX = Regex("addx (-?\\d+)")

sealed interface Instruction {
    object NoOp : Instruction
    data class AddX(val x: Int) : Instruction
}

fun solution(data: List<String>): String {
    val instructions = data.asSequence().map { line ->
        ADDX_REGEX.find(line)?.let { match ->
            Instruction.AddX(match.groupValues[1].toInt())
        } ?: Instruction.NoOp
    }

    val screen = buildString {
        var middlePartSpritePosition = 1
        for (instruction in instructions) {
            val cycles = when (instruction) {
                Instruction.NoOp -> 1
                is Instruction.AddX -> 2
            }
            for (i in 0 until cycles) {
                val position = length % 40
                if (middlePartSpritePosition in (position - 1..position + 1)) {
                    append('#')
                } else {
                    append('.')
                }
            }
            if (instruction is Instruction.AddX) {
                middlePartSpritePosition += instruction.x
            }
        }
    }
    return screen.chunked(40).joinToString(separator = "\n")
}