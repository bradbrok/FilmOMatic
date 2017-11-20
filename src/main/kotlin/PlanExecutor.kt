/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

fun planExecutor(plan: List<Step>) {
  plan.forEach { step ->
    println("${step.bath} ${step.flows} for ${step.time} seconds.")
    val time = step.time
    Thread.sleep(time.toLong()*10)
  }
}