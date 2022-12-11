package p11.t2

import DataResolver
import java.math.BigInteger
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p11", "t2"))

    @Test
    fun sample() {
        assertEquals(BigInteger.valueOf(2713310158), solution(resolver.sample))
    }

    @Test
    fun artifact() {
        assertEquals(BigInteger.valueOf(0), solution(resolver.artifact))
    }
}

