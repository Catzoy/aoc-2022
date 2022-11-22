package p01.t1

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p01", "t1"))

    @Test
    fun sample() {
        assertEquals(0, resolver.sample.size)
    }

    @Test
    fun artifact() {

    }
}

