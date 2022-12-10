package p10.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p10", "t1"))

    @Test
    fun sample() {
        assertEquals(13140, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(0, solution(resolver.artifact))
    }
}

