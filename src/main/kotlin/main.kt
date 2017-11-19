/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import javafx.application.Application
import kotlinx.coroutines.experimental.*
import tornadofx.*


fun main(args: Array<String>) {
  //init the pi interface.
  //launch
  val thisList = listOf(Plan(Bath.WATER, 0, 60, true),
          Plan(Bath.A, 10, 720, true),
          Plan(Bath.WATER, 0, 0, true),
          Plan(Bath.B, 10, 300, false))
  println(scheduleBuilder(thisList))
  println(x)
  Application.launch(FilmOMaticUI::class.java, *args)
}
