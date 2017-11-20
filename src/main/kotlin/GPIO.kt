/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.pi4j.io.gpio.*
import com.pi4j.wiringpi.Gpio

object GPIO {
  init {
    try {
      val gpioInstance = GpioFactory.getInstance()
    } catch (e: Exception) {
      val gpioInstance = null
    }
  }
}