/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import javafx.application.Application
import kotlinx.coroutines.experimental.*
import tornadofx.*

fun main(args: Array<String>) {
  //init the pi interface.
  //idle() will init the GPIO pins and sets all relays to idle state until ready.
  idle()
  Application.launch(FilmOMaticUI::class.java, *args)
}
