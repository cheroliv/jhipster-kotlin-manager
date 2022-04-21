@file:Suppress("ObjectPropertyName")

package game.ceelo

const val ONE = 1
const val TWO = 2
const val THREE = 3
const val FOUR = 4
const val FIVE = 5
const val SIX = 6
const val CEELO_DICE_THROW_SIZE = 3
const val NOT_A_TRIPLET = -1
const val NOT_A_DOUBLET = 0

const val AUTOMATIC_WIN_456_BRANCH = 1
const val AUTOMATIC_LOOSE_123_BRANCH = 2
const val TRIPLET_BRANCH = 3
const val DOUBLET_BRANCH = 4
const val STRAIGHT_234_345_BRANCH = 5



val `4_5_6` by lazy { listOf(FOUR, FIVE, SIX) }
val `1_2_3` by lazy { listOf(ONE, TWO, THREE) }

val `1_1_1` by lazy { listOf(ONE, ONE, ONE) }
val `2_2_2` by lazy { listOf(TWO, TWO, TWO) }
val `3_3_3` by lazy { listOf(THREE, THREE, THREE) }
val `4_4_4` by lazy { listOf(FOUR, FOUR, FOUR) }
val `5_5_5` by lazy { listOf(FIVE, FIVE, FIVE) }
val `6_6_6` by lazy { listOf(SIX, SIX, SIX) }

val UNIFORM_TRIPLETS by lazy {
    listOf(
        `1_1_1`,
        `2_2_2`,
        `3_3_3`,
        `4_4_4`,
        `5_5_5`,
        `6_6_6`
    )
}

val `1_1_x` by lazy { listOf(ONE, ONE) }
val `2_2_x` by lazy { listOf(TWO, TWO) }
val `3_3_x` by lazy { listOf(THREE, THREE) }
val `4_4_x` by lazy { listOf(FOUR, FOUR) }
val `5_5_x` by lazy { listOf(FIVE, FIVE) }
val `6_6_x` by lazy { listOf(SIX, SIX) }


val UNIFORM_DOUBLETS by lazy {
    listOf(
        `1_1_x`,
        `2_2_x`,
        `3_3_x`,
        `4_4_x`,
        `5_5_x`,
        `6_6_x`
    )
}

