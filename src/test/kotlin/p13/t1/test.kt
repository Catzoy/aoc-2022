package p13.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p13", "t1"))

    @Test
    fun sample() {
        assertEquals(13, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(5003, solution(resolver.artifact))
    }
}

