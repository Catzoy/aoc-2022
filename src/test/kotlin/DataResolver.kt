import java.nio.file.Path

class DataResolver(
    dataPath: Path,
) {
    companion object {
        private const val SAMPLE = "sample.txt"
        private const val ARTIFACT = "artifact.txt"

        private val TESTS_PATH = Path.of("src", "test", "kotlin")
    }

    private val dataPath = TESTS_PATH.resolve(dataPath)

    private fun readLinesOf(file: String): List<String> {
        return dataPath.resolve(file).toFile().readLines()
    }

    val sample: List<String> = readLinesOf(SAMPLE)

    val artifact: List<String> = readLinesOf(ARTIFACT)
}