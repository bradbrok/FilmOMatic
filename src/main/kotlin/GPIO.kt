/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.pi4j.io.gpio.*

class Gpio {
  init {
    val gpioInstance: GpioController
    try {
      gpioInstance = GpioFactory.getInstance()
    } catch(e: Exception) {
    }
  }
}