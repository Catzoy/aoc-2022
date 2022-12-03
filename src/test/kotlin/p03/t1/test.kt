package p03.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p03", "t1"))

    @Test
    fun sample() {
        assertEquals(157, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(7691, solution(resolver.artifact))
    }
}

