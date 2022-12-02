package p02.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p02", "t1"))

    @Test
    fun sample() {
        assertEquals(15, solution(resolver.sample))
    }

    @Test
    fun artifact() {
        println(solution(resolver.artifact))
    }
}

