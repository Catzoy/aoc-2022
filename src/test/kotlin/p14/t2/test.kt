package p14.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p14", "t2"))

    @Test
    fun sample() {
        assertEquals(93, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(30157, solution(resolver.artifact))
    }
}

