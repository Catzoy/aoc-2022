package p07.t1

import p07.flatten
import p07.parseInput
import java.math.BigInteger

fun solution(data: List<String>): BigInteger {
    val ceil = BigInteger.valueOf(100_000)
    val root = parseInput(data)
    val dirs = flatten(root)
    val sub100000 = dirs.filter { it.size < ceil }
    return sub100000.sumOf { it.size }
}
