package p07.t2

import p07.flatten
import p07.parseInput
import java.math.BigInteger

fun solution(data: List<String>): BigInteger {
    val fileSystemSize = BigInteger.valueOf(70_000_000)
    val requiredFreeSpace = BigInteger.valueOf(30_000_000)
    val root = parseInput(data)
    val dirs = flatten(root)
    val unusedSpace = fileSystemSize - dirs.first().size
    val requiredFreeUpSize = requiredFreeSpace - unusedSpace
    val directoryToDelete = dirs.filter { dir ->
        dir.size >= requiredFreeUpSize
    }.minBy { it.size }
    return directoryToDelete.size
}
