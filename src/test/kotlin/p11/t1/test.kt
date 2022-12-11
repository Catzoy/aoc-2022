package p11.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p11", "t1"))

    @Test
    fun sample() {
        assertEquals(10605, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(0, solution(resolver.artifact))
    }
}

