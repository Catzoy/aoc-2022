package p11.t1

import kotlin.math.floor

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
                else -> Constant(str.toInt())
            }
        }
    }

    fun provide(old: Int): Int

    @JvmInline
    value class Constant(private val value: Int) : OperationItem {
        override fun provide(old: Int): Int {
            return value
        }
    }

    object Ref : OperationItem {
        override fun provide(old: Int): Int {
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

    fun act(left: Int, right: Int): Int

    object Sum : OperationAction {
        override fun act(left: Int, right: Int): Int {
            return left + right
        }
    }

    object Times : OperationAction {
        override fun act(left: Int, right: Int): Int {
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

    operator fun invoke(old: Int): Int {
        return deed.act(
            left = left.provide(old),
            right = right.provide(old),
        )
    }
}

data class Monkey(
    val items: MutableList<Int>,
    val operation: Operation,
    val divisionTest: Int,
    val trueMonkeyIdx: Int,
    val falseMonkeyIdx: Int,
    var inspections: Int = 0,
) {
    fun inspect(item: Int): Int {
        val worryLvl = operation(item) / 3.0
        inspections += 1
        return floor(worryLvl).toInt()
    }
}

fun solution(data: List<String>): Int {
    val monkeys = data.asSequence().chunked(7).map { monkey ->
        val items = STARTING_ITEMS_REGEX.find(monkey[1])!!.groupValues[1].split(", ").map { it.toInt() }
        val test = TEST_REGEX.find(monkey[3])!!.groupValues[1].toInt()
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

    for (round in 0 until 20) {
        for (monkey in monkeys) {
            monkey.items.removeAll { initialWorryLvl ->
                val worryLvl = monkey.inspect(initialWorryLvl)
                val monkeyIdx = if (worryLvl % monkey.divisionTest == 0) {
                    monkey.trueMonkeyIdx
                } else {
                    monkey.falseMonkeyIdx
                }
                monkeys[monkeyIdx].items.add(worryLvl)
                true
            }
        }
    }

    val mostActive = monkeys.asSequence().sortedByDescending { it.inspections }.take(2).map { it.inspections }
    return mostActive.reduce { acc, inspections -> acc * inspections }
}
