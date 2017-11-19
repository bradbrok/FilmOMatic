/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

data class Step(val flows: Flows, val bath: Bath, val time: Int)

data class Plan(val bath: Bath, val agitationTime: Int, val time: Int, val waste: Boolean)

