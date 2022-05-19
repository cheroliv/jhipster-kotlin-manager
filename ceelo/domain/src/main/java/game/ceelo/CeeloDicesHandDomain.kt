package game.ceelo

object CeeloDicesHandDomain {

    /**
     * Renvoi le dé du milieu
     */
    fun List<Int>.middleDice(): Int = if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    else elementAt(index = ONE)

    val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

    val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)


    @Suppress("MemberVisibilityCanBePrivate")
    fun List<Int>.firstDice(): Int = if (isEmpty())
        throw NoSuchElementException("no first dice found in hand.")
    else first()


    @Suppress("MemberVisibilityCanBePrivate")
    fun List<Int>.lastDice(): Int = if (isEmpty())
        throw NoSuchElementException("no last dice found in hand.")
    else last()


    /**
     * Est ce un triplet?
     */
    val List<Int>.isUniformTriplet: Boolean
        get() = UNIFORM_TRIPLETS.map {
            it.firstDice().run {
                this == firstDice() && this == middleDice() && this == last()
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
                it.firstDice().run {
                    firstDice() == this && middleDice() == this &&
                            lastDice() != this || firstDice() == this &&
                            lastDice() == this && middleDice() != this ||
                            middleDice() == this && lastDice() == this &&
                            firstDice() != this
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
                find { dice: Int ->
                    UNIFORM_DOUBLETS.first { hand: List<Int> ->
                        (firstDice() == hand.firstDice() && middleDice() == hand.firstDice()) ||
                                (firstDice() == hand.firstDice() && lastDice() == hand.firstDice()) ||
                                (middleDice() == hand.firstDice() && lastDice() == hand.firstDice())
                    }.first() != dice
                }!!
            else -> NOT_A_DOUBLET
        }

    val List<Int>.isStraight: Boolean
        get() = STRAIGHT_TRIPLETS.map { containsAll(it) }.contains(true)

    fun List<Int>.getDiceImageFromDiceValue(
        diceValue: Int
    ): Int = when (diceValue) {
        ONE -> first()
        TWO -> this[TWO - ONE]
        THREE -> this[THREE - ONE]
        FOUR -> this[FOUR - ONE]
        FIVE -> this[FIVE - ONE]
        SIX -> this[SIX - ONE]
        else -> throw Exception("Only six faces is possible!")
    }
}