package p07.t1

import DataResolver
import java.math.BigInteger
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p07", "t1"))

    @Test
    fun sample() {
        assertEquals(BigInteger.valueOf(95437), solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(BigInteger.valueOf(1644735), solution(resolver.artifact))
    }
}

