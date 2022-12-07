package p07.t1

import java.math.BigInteger

val CHANGE_DIRECTORY = Regex("\\$ cd (.+)")
val LIST_CONTENT = Regex("\\$ ls")
val CONTENT_DIRECTORY = Regex("dir (.+)")
val CONTENT_FILE = Regex("(\\d+) (.+)")

data class Directory(
    val name: String,
    var size: BigInteger = BigInteger.ZERO,
    val parent: Directory? = null,
    val files: MutableList<File> = mutableListOf(),
    val directories: MutableList<Directory> = mutableListOf(),
) {
    override fun toString(): String {
        return "Directory(name=$name, directories=[${directories.joinToString(separator = "\n")}], files=[${
            files.joinToString(
                separator = "\n"
            )
        }])"
    }
}

data class File(
    val name: String,
    val size: BigInteger,
)


fun parseInput(data: List<String>): Directory {
    val root = Directory("/")
    var currentDir = root
    fun Directory.updateSize() {
        size = files.sumOf { it.size } + directories.sumOf { it.size }
    }

    fun updateSizes() {
        var dir: Directory? = currentDir
        do {
            dir!!.updateSize()
            dir = dir.parent
        } while (dir != null)
    }

    for (line in data) {
        if (LIST_CONTENT.find(line) != null) {
            continue
        }

        CHANGE_DIRECTORY.find(line)?.let { match ->
            updateSizes()
            currentDir = when (val input = match.groupValues[1]) {
                "/" -> root
                ".." -> currentDir.parent!!
                else -> {
                    currentDir.directories.find {
                        it.name == input
                    }!!
                }
            }
        }

        CONTENT_DIRECTORY.find(line)?.let { match ->
            val directoryName = match.groupValues[1]
            val directory = Directory(
                name = directoryName,
                parent = currentDir,
            )
            currentDir.directories.add(directory)
        }

        CONTENT_FILE.find(line)?.let { match ->
            val (size, name) = match.groupValues.drop(1)
            val file = File(
                name = name,
                size = BigInteger.valueOf(size.toLong()),
            )
            currentDir.files.add(file)
            updateSizes()
        }
    }

    return root
}

fun traverseDirectory(directory: Directory): Map<String, BigInteger> {
    val directoriesSizes = directory.directories.fold(mutableMapOf<String, BigInteger>()) { map, dir ->
        val innerSizes = traverseDirectory(dir).entries.fold(mutableMapOf<String, BigInteger>()) { map2, entry ->
            map2["${directory.name}/${entry.key}"] = entry.value
            map2
        }
        map.putAll(innerSizes)
        map
    }

    val directorySizes = mutableMapOf<String, BigInteger>()
    directorySizes.putAll(directoriesSizes)
    directorySizes[directory.name] = directory.size

    return directorySizes
}

fun solution(data: List<String>): BigInteger {
    val ceil = BigInteger.valueOf(100_000)
    val root = parseInput(data)
    val sizes = traverseDirectory(root)
    val sub100000 = sizes.entries.filter { it.value <= ceil }
    return sub100000.sumOf { it.value }
}
