/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

fun planBuilder(bath: Bath, agitationTime: Int, time: Int, waste: Boolean): MutableList<Step> {
  val fillTime = 30
  val list = mutableListOf<Step>()
  var lastStep = 0
  val totalSteps = if (agitationTime > 0) {
    (time / 60) * 2
  } else {
    time / 60
  }
  if (time / 60 != totalSteps) {
    lastStep = if (agitationTime > 0) {
      (time - ((totalSteps * 60) / 2))
    } else {
      (time - (totalSteps * 60))
    }
  }
  val idleTime = 60 - agitationTime
  list.add(Step(Flows.IN, bath, fillTime))
  if (agitationTime > 0) {
    for (i in 1..totalSteps) {
      when (i % 2) {
        0 -> list.add(Step(Flows.IDLE, bath, idleTime))
        1 -> list.add(Step(Flows.AGITATE, bath, agitationTime))
      }
    }
  } else {
    list.add(Step(Flows.IDLE, bath, time))
  }
  if (lastStep != 0) {
    list.add(Step(Flows.IDLE, bath, lastStep))
  }
  when (waste) {
    false -> if (bath != Bath.WATER) {
      list.add(Step(Flows.OUT, bath, fillTime))
    } else {
      list.add(Step(Flows.WASTE, bath, fillTime))
    }
    true -> list.add(Step(Flows.WASTE, bath, fillTime))
  }
  return list

}
