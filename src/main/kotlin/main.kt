/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.pi4j.io.gpio.GpioFactory
import javafx.application.Application
import kotlinx.coroutines.experimental.*
import tornadofx.*

fun main(args: Array<String>) {
  //init the pi interface.
  //launch
  Application.launch(FilmOMaticUI::class.java, *args)
}
