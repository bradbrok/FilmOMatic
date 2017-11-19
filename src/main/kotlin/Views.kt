/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.sun.prism.paint.Color
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*

class FilmOMaticUI : App(MainView::class)

class MainView : View() {
  override val root = HBox()

  init {
    with(root) {
      prefHeight = 240.0
      prefWidth = 320.0

      val minutes = SimpleIntegerProperty(5)
      val seconds = SimpleIntegerProperty(0)

      fun incrementM() {
        minutes.value += 1
      }

      fun decrementM() {
        if (minutes.value > 0) {
          minutes.value -= 1
        } else {
          println("Nope.")
        }
      }

      fun incrementS() {
        seconds.value += 1
      }

      fun decrementS() {
        if (seconds.value > 0) {
          seconds.value -= 1
        } else {
          println("Nope.")
        }
      }

      tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab("B&W", GridPane()) {
          prefWidth = 320.0
          label("B&W")
          label("$minutes") {
            bind(minutes)
          }
          button("+") {
            action {
              incrementM()
            }
          }
          button("-") {
            action {
              decrementM()
            }
          }
          button("Start B&W") {
            action {
              val time = (minutes.value * 60) + (seconds.value)
              val planList = listOf(
                      Plan(Bath.WATER, 0, 60, true),
                      Plan(Bath.A, 10, time, true),
                      Plan(Bath.WATER, 30, 60, true),
                      Plan(Bath.B, 10, 300, false)
              )
              println(scheduleBuilder(planList))
            }
          }
        }
        tab("C41", GridPane()) {

        }
        tab("E6", GridPane()) {

        }
        tab("Custom", GridPane()) {
        }
        tab("Settings", GridPane()) {

        }
      }
    }
  }
}
