/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import javafx.application.Application
import kotlinx.coroutines.experimental.*
import tornadofx.*


fun main(args: Array<String>) {
  //init the pi interface.
  //launch
  Application.launch(FilmOMaticUI::class.java, *args)
}
