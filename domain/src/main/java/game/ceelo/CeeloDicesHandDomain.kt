package game.ceelo

object CeeloDicesHandDomain {

    /**
     * Renvoi le dé du milieu
     */
    fun List<Int>.middle(): Int = if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    else elementAt(index = 1)

    val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

    val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)


    fun List<Int>.firstDice(): Int = if (isEmpty())
        throw NoSuchElementException("no first dice found in hand.")
    else first()

    /**
     * Est ce un triplet?
     */
    val List<Int>.isUniformTriplet: Boolean
        get() = UNIFORM_TRIPLETS.map {
            it.firstDice().run {
                this == firstDice() && this == middle() && this == last()
            }
        }.contains(true)

    /**
     * La valeur faciale du dé triplet
     * Si le jet n'est pas un triplet
     * renvoi NOT_A_TRIPLET
     */
    val List<Int>.uniformTripletValue: Int
        get() = if (!isUniformTriplet) NOT_A_TRIPLET
        else UNIFORM_TRIPLETS.find { containsAll(elements = it) }!!.first()

    val List<Int>.isUniformDoublet: Boolean
        get() = run {
            UNIFORM_DOUBLETS.map {
                it.first().run {
                    first() == this && middle() == this && last() != this ||
                            first() == this && last() == this && middle() != this ||
                            middle() == this && last() == this && first() != this
                }
            }.contains(true)
        }

    /**
     * Renvoi la valeur du dé qui n'est pas un doublet
     */
    val List<Int>.uniformDoubletValue: Int
        get() = if (!isUniformTriplet && !isUniformDoublet) NOT_A_DOUBLET
        else when {
            isUniformTriplet -> NOT_A_DOUBLET
            isUniformDoublet ->
                find { it: Int ->
                    UNIFORM_DOUBLETS.first {
                        first() == it.first() && middle() == it.first() ||
                                first() == it.first() && last() == it.first() ||
                                middle() == it.first() && last() == it.first()
                    }.first() != it
                }!!
            else -> NOT_A_DOUBLET
        }

    val List<Int>.isStraight: Boolean
        get() = STRAIGHT_TRIPLETS.map { containsAll(it) }.contains(true)

    fun List<Int>.getDiceImageFromDiceValue(
        diceValue: Int
    ): Int = when (diceValue) {
        ONE -> this[ONE - 1]
        TWO -> this[TWO - 1]
        THREE -> this[THREE - 1]
        FOUR -> this[FOUR - 1]
        FIVE -> this[FIVE - 1]
        SIX -> this[SIX - 1]
        else -> throw Exception("Only six faces is possible!")
    }
}