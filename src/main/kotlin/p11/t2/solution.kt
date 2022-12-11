package p11.t2

import java.math.BigInteger

val STARTING_ITEMS_REGEX = Regex("Starting items: (.+)")
val OPERATION_REGEX = Regex("Operation: new = (old|\\d+) (\\+|\\*) (old|\\d+)")
val TEST_REGEX = Regex("Test: divisible by (\\d+)")
val TRUE_CASE_REGEX = Regex("If true: throw to monkey (\\d+)")
val FALSE_CASE_REGEX = Regex("If false: throw to monkey (\\d+)")

sealed interface OperationItem {

    companion object {
        fun parse(str: String): OperationItem {
            return when (str) {
                "old" -> Ref
                else -> Constant(str.toLong())
            }
        }
    }

    fun provide(old: Long): Long

    @JvmInline
    value class Constant(private val value: Long) : OperationItem {
        override fun provide(old: Long): Long {
            return value
        }
    }

    object Ref : OperationItem {
        override fun provide(old: Long): Long {
            return old
        }
    }
}

sealed interface OperationAction {
    companion object {
        fun parse(str: String): OperationAction {
            return when (str) {
                "+" -> Sum
                "*" -> Times
                else -> throw IllegalArgumentException("Must be either '+' or '*'")
            }
        }
    }

    fun act(left: Long, right: Long): Long

    object Sum : OperationAction {
        override fun act(left: Long, right: Long): Long {
            return left + right
        }
    }

    object Times : OperationAction {
        override fun act(left: Long, right: Long): Long {
            return left * right
        }
    }
}

data class Operation(
    val left: OperationItem,
    val right: OperationItem,
    val deed: OperationAction,
) {
    companion object {
        fun parse(str: String): Operation {
            val values = OPERATION_REGEX.find(str)!!.groupValues
            return Operation(
                left = OperationItem.parse(values[1]),
                right = OperationItem.parse(values[3]),
                deed = OperationAction.parse(values[2]),
            )
        }
    }

    fun apply(old: Long): Long {
        return deed.act(
            left = left.provide(old),
            right = right.provide(old),
        )
    }
}

data class Monkey(
    val items: MutableList<Long>,
    val operation: Operation,
    val divisionTest: Long,
    val trueMonkeyIdx: Int,
    val falseMonkeyIdx: Int,
    var inspections: Long = 0,
) {
    fun inspect(item: Long): Long {
        return operation.apply(item).also { inspections += 1 }
    }
}

fun solution(data: List<String>): BigInteger {
    val monkeys = data.asSequence().chunked(7).map { monkey ->
        val items = STARTING_ITEMS_REGEX.find(monkey[1])!!.groupValues[1].split(", ").map { it.toLong() }
        val test = TEST_REGEX.find(monkey[3])!!.groupValues[1].toLong()
        val trueCase = TRUE_CASE_REGEX.find(monkey[4])!!.groupValues[1].toInt()
        val falseCase = FALSE_CASE_REGEX.find(monkey[5])!!.groupValues[1].toInt()
        Monkey(
            items = items.toMutableList(),
            operation = Operation.parse(monkey[2]),
            divisionTest = test,
            trueMonkeyIdx = trueCase,
            falseMonkeyIdx = falseCase,
        )
    }.toList()

    val modulo = monkeys.asSequence()
        .map { it.divisionTest }
        .reduce { acc, i -> acc * i }

    for (round in 0 until 10000) {
        for (monkey in monkeys) {
            monkey.items.removeAll { initialWorryLvl ->
                val worryLvl = monkey.inspect(initialWorryLvl) % modulo
                val monkeyIdx = if (worryLvl % monkey.divisionTest != 0L) {
                    monkey.falseMonkeyIdx
                } else {
                    monkey.trueMonkeyIdx
                }

                monkeys[monkeyIdx].items.add(worryLvl)
                true
            }
        }
    }

    monkeys.forEach { m ->
        println(m)
    }

    val mostActive =
        monkeys.asSequence().sortedByDescending { it.inspections }.take(2).map { it.inspections.toBigInteger() }
    return mostActive.reduce { acc, inspections -> acc * inspections }
}
