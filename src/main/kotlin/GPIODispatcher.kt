import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

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

val pumpIn = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_05, "IN", PinState.LOW)
val pumpOut = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_06, "OUT", PinState.LOW)
val solenoidA = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_13, "A", PinState.LOW)
val solenoidB = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_19, "B", PinState.LOW)
val solenoidC = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_26, "C", PinState.LOW)
val solenoidWater = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_12, "Water", PinState.LOW)
val solenoidWaste = gpioInstance.provisionDigitalOutputPin(RaspiPin.GPIO_20, "Waste", PinState.LOW)

fun idle() {
  println("Turning off relay")
}

fun waste() {
  println("Waste")
}

fun bathA(flows: Flows) {
  when (flows) {
    Flows.IN -> println("Bath A Flowing In") //Run relay for A:IN state
    Flows.OUT -> println("Bath A flowing Out") // Run relay for A:OUT state
    Flows.IDLE -> idle()
    Flows.WASTE -> waste()
    Flows.AGITATE -> println("Bath A Agitating")
  }
}

fun bathB(flows: Flows) {

}

fun bathC(flows: Flows) {

}

fun bathWater(flows: Flows) {

}

fun bathWaste(flows: Flows) {

}

fun gpioDispatcher(bath: Bath, flows: Flows) {

}