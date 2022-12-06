package p06.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p06", "t2"))

    @Test
    fun sample() {
        assertEquals(19, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(2414, solution(resolver.artifact))
    }
}

