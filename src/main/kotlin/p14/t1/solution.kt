package p14.t1

import p14.Item
import p14.Point
import p14.buildMatrix
import p14.parse

fun solution(data: List<String>): Int {
    val lines = parse(data)
    val matrix = buildMatrix(lines)
    var iterations = 0
    val ref = Point(x = 500, y = 0)
    var unitPosition = ref
    do {
        unitPosition = unitPosition.copy(y = unitPosition.y + 1)
        val lowerLine = matrix.getOrNull(unitPosition.y) ?: break

        val below = lowerLine[unitPosition.x]
        if (below is Item.Air) {
            continue
        }

        val leftX = unitPosition.x - 1
        val belowLeft = lowerLine.getOrNull(leftX) ?: break
        if (belowLeft is Item.Air) {
            unitPosition = unitPosition.copy(x = leftX)
            continue
        }

        val rightX = unitPosition.x + 1
        val belowRight = lowerLine.getOrNull(rightX) ?: break
        if (belowRight is Item.Air) {
            unitPosition = unitPosition.copy(x = rightX)
            continue
        }

        unitPosition = unitPosition.copy(y = unitPosition.y - 1)
        if (matrix[unitPosition.y][unitPosition.x] !is Item.Air) {
            break
        }

        matrix[unitPosition.y][unitPosition.x] = Item.Sand
        unitPosition = ref
        iterations += 1
    } while (true)
    return iterations
}
