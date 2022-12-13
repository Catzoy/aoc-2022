package p13.t2

enum class Result {
    Right,
    Same,
    Wrong,
}

sealed interface Item {

    fun cmp(other: Item): Result

    @JvmInline
    value class Integer(val value: Int) : Item {
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

    @JvmInline
    value class ItemList(val value: List<Item>) : Item {

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
    val startKey = Item.ItemList(listOf(Item.ItemList(listOf(Item.Integer(2)))))
    val endKey = Item.ItemList(listOf(Item.ItemList(listOf(Item.Integer(6)))))
    return data.asSequence()
        .chunked(3)
        .flatMap { lines ->
            listOf(
                parse(lines[0]),
                parse(lines[1])
            )
        }
        .plus(startKey)
        .plus(endKey)
        .sortedWith { a, b ->
            when (a.cmp(b)) {
                Result.Right -> -1
                Result.Same -> 0
                Result.Wrong -> 1
            }
        }
        .withIndex()
        .filter { (_, item) ->
            item == startKey || item == endKey
        }
        .map {
            it.index + 1
        }
        .reduce { a, b ->
            a * b
        }
}
