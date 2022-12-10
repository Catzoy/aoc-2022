package p10.t2

import DataResolver
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class Run {
    private val resolver = DataResolver(dataPath = Path.of("p10", "t2"))

    @Test
    fun sample() {
        assertEquals(
            "##..##..##..##..##..##..##..##..##..##..\n" +
                    "###...###...###...###...###...###...###.\n" +
                    "####....####....####....####....####....\n" +
                    "#####.....#####.....#####.....#####.....\n" +
                    "######......######......######......####\n" +
                    "#######.......#######.......#######.....",
            solution(resolver.sample)
        )
    }

    @Test
    fun artifact() {
        assertEquals(
            "####.###....##.###..###..#..#..##..#..#.\n" +
                    "#....#..#....#.#..#.#..#.#.#..#..#.#..#.\n" +
                    "###..#..#....#.###..#..#.##...#..#.####.\n" +
                    "#....###.....#.#..#.###..#.#..####.#..#.\n" +
                    "#....#....#..#.#..#.#.#..#.#..#..#.#..#.\n" +
                    "####.#.....##..###..#..#.#..#.#..#.#..#.",
            solution(resolver.artifact)
        )
    }
}

