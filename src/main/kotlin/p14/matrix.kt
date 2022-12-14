package p14

sealed interface Item {
    object Air : Item
    object SandSource : Item
    object Rock : Item
    object Sand : Item
}

typealias Matrix = MutableList<MutableList<Item>>

fun Matrix.ensureDepth(depth: Int) = apply {
    if (getOrNull(depth) != null) return@apply
    repeat(depth + 1 - size) {
        add(MutableList(first().size) { Item.Air })
    }
}

fun Matrix.ensureWidth(width: Int) = apply {
    if (first().getOrNull(width) != null) return@apply
    val diff = width + 1 - first().size
    forEach { line ->
        repeat(diff) {
            line.add(Item.Air)
        }
    }
}

fun buildMatrix(lines: List<Line>): Matrix {
    val matrix = mutableListOf(
        MutableList<Item>(501) { Item.Air }
    )
    matrix[0][500] = Item.SandSource

    for (line in lines) {
        matrix.ensureDepth(line.origin.y)
        matrix.ensureWidth(line.origin.x)

        when (line.type) {
            LineType.Vertical -> {
                val endY = line.origin.y + line.length
                matrix.ensureDepth(endY)
                for (y in line.origin.y..endY) {
                    matrix[y][line.origin.x] = Item.Rock
                }
            }

            LineType.Horizontal -> {
                val endX = line.origin.x + line.length
                matrix.ensureWidth(endX)
                for (x in line.origin.x..endX) {
                    matrix[line.origin.y][x] = Item.Rock
                }
            }
        }
    }

    return matrix
}