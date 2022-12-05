package p05.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p05", "t1"))

    @Test
    fun sample() {
        assertEquals("CMZ", solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals("VQZNJMWTR", solution(resolver.artifact))
    }
}

