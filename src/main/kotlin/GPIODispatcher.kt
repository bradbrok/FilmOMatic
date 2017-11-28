/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

/*
The relay board will be on pins as follows:

          01  02 - 5V Power to relay
          03  04
          05  06 - Ground to relay
          07  08
          09  10
          11  12
          13  14
          15  16
          17  18
          19  20
          21  22
          23  24
          25  26
          27  28
 GPIO05-1 29  30
 GPIO06-2 31  32 GPIO12-6
 GPIO13-3 33  34
 GPIO19-4 35  36 GPIO16-7
 GPIO26-5 37  38 GPIO20-8
          39  40

Relay config:
R1 => Pump IN, Solenoid IN
R2 => Pump OUT, Solenoid OUT
R3 => Solenoid 1 - A
R4 => Solenoid 2 - B
R5 => Solenoid 3 - C
R6 => Solenoid 4 - Water
R7 => Solenoid 5 - Waste
R8 => TBD (Maybe an agitation motor later)
 */

val pins = listOf(2,3,4,23,14,15,17)

val pumpIn = Iogpio[2]
val pumpOut = Iogpio[3]
val solenoidA = Iogpio[4]
val solenoidB = Iogpio[23]
val solenoidC = Iogpio[14]
val solenoidWater = Iogpio[15]
val solenoidWaste = Iogpio[17]

val instanceList = listOf(
        pumpIn,
        pumpOut,
        solenoidA,
        solenoidB,
        solenoidC,
        solenoidWater,
        solenoidWaste
)

fun idle() {
  println("Turning off relay")
  instanceList.forEach { item -> item.low() }
}

fun waste() {
  println("Waste")
  pumpOut.high(); solenoidWaste.high()
}

fun bathA(flows: Flows) {
  when (flows) {
    Flows.IN -> {
      idle(); solenoidA.high(); pumpIn.high()
    }
    Flows.OUT -> {
      idle(); solenoidA.high(); pumpOut.high()
    }
    Flows.IDLE -> idle()
    Flows.WASTE -> waste()
    Flows.AGITATE -> {
      idle(); solenoidA.high(); pumpIn.high()
    }
  }
}

fun bathB(flows: Flows) {
  when (flows) {
    Flows.IN -> {
      idle(); solenoidB.high(); pumpIn.high()
    }
    Flows.OUT -> {
      idle(); solenoidB.high(); pumpOut.high()
    }
    Flows.IDLE -> idle()
    Flows.WASTE -> waste()
    Flows.AGITATE -> {
      idle(); solenoidB.high(); pumpIn.high()
    }
  }
}

fun bathC(flows: Flows) {
  when (flows) {
    Flows.IN -> {
      idle(); solenoidC.high(); pumpIn.high()
    }
    Flows.OUT -> {
      idle(); solenoidC.high(); pumpOut.high()
    }
    Flows.IDLE -> idle()
    Flows.WASTE -> waste()
    Flows.AGITATE -> {
      idle(); solenoidC.high(); pumpIn.high()
    }
  }
}

fun bathWater(flows: Flows) {
  when(flows) {
    Flows.IN -> {
      idle(); solenoidWater.high(); pumpIn.high()
    }
    Flows.OUT -> {
      idle(); solenoidWater.high(); pumpOut.high()
    }
    Flows.IDLE -> idle()
    Flows.WASTE -> waste()
    //Can't agitate water like this, would need external agitator device.
    //This will probably be V2
    Flows.AGITATE -> {println("MMmmmmmm"); idle()}
  }
}

fun gpioDispatcher(bath: Bath, flows: Flows) {
  when (bath) {
    Bath.A -> bathA(flows)
    Bath.B -> bathB(flows)
    Bath.C -> bathC(flows)
    Bath.WATER -> bathWater(flows)
  }
}