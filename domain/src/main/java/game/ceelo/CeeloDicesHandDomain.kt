package game.ceelo

object CeeloDicesHandDomain {
    /**
     * un jet de dés au hazard
     */
    fun runDices(): List<Int> = List(size = 3, init = { (ONE..SIX).random() })
    /**
     * Renvoi le dé du milieu
     */
    fun List<Int>.middle(): Int = if (isEmpty())
        throw NoSuchElementException("dice throw is empty.")
    else elementAt(index = 1)

    val List<Int>.is456: Boolean get() = containsAll(`4_5_6`)

    val List<Int>.is123: Boolean get() = containsAll(`1_2_3`)

    /**
     * Est ce un triplet?
     */
    val List<Int>.isUniformTriplet: Boolean
        get() = UNIFORM_TRIPLETS.map {
            it.first().run {
                this == first() && this == middle() && this == last()
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


}