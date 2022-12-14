package p14.t2

import p14.*

fun Matrix.refillLastWithRock() = with(last()) {
    for (i in indices.reversed()) {
        if (get(i) is Item.Air) {
            set(i, Item.Rock)
        } else {
            break
        }
    }
}

fun solution(data: List<String>): Int {
    val lines = parse(data)
    val matrix = buildMatrix(lines).apply {
        val refSize = first().size
        add(MutableList(refSize) { Item.Air })
        add(MutableList(refSize) { Item.Rock })
    }

    var iterations = 0
    val ref = Point(x = 500, y = 0)
    var unitPosition = ref
    do {
        unitPosition = unitPosition.copy(y = unitPosition.y + 1)
        val lowerLine = matrix[unitPosition.y]

        val below = lowerLine[unitPosition.x]
        if (below is Item.Air) {
            continue
        }

        val leftX = unitPosition.x - 1
        val belowLeft = lowerLine[leftX]
        if (belowLeft is Item.Air) {
            unitPosition = unitPosition.copy(x = leftX)
            continue
        }

        val rightX = unitPosition.x + 1
        val belowRight = lowerLine.getOrNull(rightX) ?: run {
            matrix.ensureWidth(rightX)
            matrix.refillLastWithRock()
            lowerLine[rightX]
        }
        if (belowRight is Item.Air) {
            unitPosition = unitPosition.copy(x = rightX)
            continue
        }

        unitPosition = unitPosition.copy(y = unitPosition.y - 1)
        iterations += 1
        if (matrix[unitPosition.y][unitPosition.x] !is Item.Air) {
            break
        }

        matrix[unitPosition.y][unitPosition.x] = Item.Sand
        unitPosition = ref
    } while (true)

    return iterations
}
