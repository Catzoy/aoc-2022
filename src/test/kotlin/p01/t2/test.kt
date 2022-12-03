package p01.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p01", "t2"))

    @Test
    fun sample() {
        assertEquals(45000, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(197301, solution(resolver.artifact))
    }
}

