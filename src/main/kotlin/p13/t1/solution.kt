package p13.t1

enum class Result {
    Right,
    Same,
    Wrong,
}

sealed interface Item {

    fun cmp(other: Item): Result
    data class Integer(val value: Int) : Item {
        override fun cmp(other: Item): Result {
            return when (other) {
                is Integer -> when {
                    value < other.value -> Result.Right
                    value == other.value -> Result.Same
                    else -> Result.Wrong
                }

                is ItemList -> ItemList(listOf(this)).cmp(other)
            }
        }

        override fun toString(): String {
            return value.toString()
        }
    }

    data class ItemList(val value: List<Item>) : Item {

        private fun cmp(other: ItemList): Result {
            for ((i, item) in value.withIndex()) {
                val otherItem = other.value.getOrNull(i) ?: return Result.Wrong
                return when (val result = item.cmp(otherItem)) {
                    Result.Same -> continue
                    else -> result
                }
            }

            return if (other.value.size == value.size) {
                Result.Same
            } else {
                Result.Right
            }
        }

        override fun cmp(other: Item): Result {
            return when (other) {
                is Integer -> cmp(ItemList(listOf(other)))
                is ItemList -> cmp(other)
            }
        }

        override fun toString(): String {
            return value.toString()
        }
    }
}

fun parseList(chars: Iterator<Char>): Item.ItemList {
    val items = mutableListOf<Item>()
    val digitBuf = mutableListOf<Char>()
    fun drainDigits() {
        if (digitBuf.isEmpty()) return
        val value = String(digitBuf.toCharArray()).toInt()
        items.add(Item.Integer(value))
        digitBuf.clear()
    }
    do {
        when (val char = chars.next()) {
            in '0'..'9' -> {
                digitBuf.add(char)
            }

            '[' -> {
                drainDigits()
                items.add(parseList(chars))
            }

            ',' -> {
                drainDigits()
            }

            ']' -> {
                drainDigits()
                break
            }
        }
    } while (chars.hasNext())
    return Item.ItemList(items)
}


fun parse(line: String): Item {
    val iter = line.iterator()
    return parseList(iter).value.first()
}

fun solution(data: List<String>): Int {
    return data.asSequence()
        .chunked(3)
        .map { lines ->
            parse(lines[0]) to parse(lines[1])
        }
        .map { (left, right) ->
            left.cmp(right)
        }
        .withIndex()
        .filter { (_, cmp) ->
            cmp == Result.Right
        }
        .sumOf { (i, _) ->
            i + 1
        }
}
