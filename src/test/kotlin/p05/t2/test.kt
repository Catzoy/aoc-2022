package p05.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p05", "t2"))

    @Test
    fun sample() {
        assertEquals("MCD", solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals("NLCDCLVMQ", solution(resolver.artifact))
    }
}

