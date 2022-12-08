package p08.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p08", "t2"))

    @Test
    fun sample() {
        assertEquals(8, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(263670, solution(resolver.artifact))
    }
}

