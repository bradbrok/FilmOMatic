/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

fun scheduleBuilder(plan: List<Plan>): MutableList<Step> {
  val list: MutableList<Step> = mutableListOf<Step>()
  for ((bath, agitation, time, waste) in plan) {
    val steps = planBuilder(bath, agitation, time, waste)
    for (item in steps) {
      list.add(item)
    }
  }
  return list
}