package p07

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

    fun toSized(): SizedDirectory {
        val dirs = directories.map { it.toSized() }
        val size = files.sumOf { it.size } + dirs.sumOf { it.size }
        return SizedDirectory(
            name = name,
            size = size,
            files = files,
            directories = dirs,
        )
    }
}

data class SizedDirectory(
    val name: String,
    val size: BigInteger,
    val files: List<File>,
    val directories: List<SizedDirectory>,
)

data class File(
    val name: String,
    val size: BigInteger,
)

fun parseInput(data: List<String>): SizedDirectory {
    val root = Directory("/")
    var currentDir = root

    for (line in data) {
        if (LIST_CONTENT.find(line) != null) {
            continue
        }

        CHANGE_DIRECTORY.find(line)?.let { match ->
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
        }
    }

    return root.toSized()
}

fun flatten(tree: SizedDirectory): List<SizedDirectory> {
    val dirs = mutableListOf(tree)
    dirs.addAll(
        tree.directories.flatMap { flatten(it) }
    )
    return dirs
}