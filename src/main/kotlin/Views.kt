/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.sun.prism.paint.Color
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import kotlinx.coroutines.experimental.launch
import tornadofx.*
import kotlin.concurrent.thread

class FilmOMaticUI : App(MainView::class)

class MainView : View() {
  override val root = HBox()

  init {
    with(root) {
      style {
        //How do we remove the button bar? Mmmm
      }
      prefHeight = 240.0
      prefWidth = 320.0

      val minutes = SimpleIntegerProperty(5)
      val seconds = SimpleIntegerProperty(0)

      fun incrementM() {
        minutes.value += 1
      }

      fun decrementM() {
        when (minutes.value) {
          0 -> println("Nope.")
          else -> minutes.value--
        }
      }

      fun incrementS() {
        when (seconds.value) {
          59 -> {
            minutes.value++; seconds.value = 0
          }
          else -> seconds.value++
        }
      }

      fun decrementS() {
        when (seconds.value) {
          0 -> if (seconds.value == 0) {
            when (minutes.value) {
              0 -> println("Nope")
              else -> {
                minutes.value--; seconds.value = 59
              }
            }
          }
          else -> seconds.value--
        }
      }

      tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab("B&W", GridPane()) {
          prefWidth = 320.0
          gridpane {
            label(minutes) {
              prefWidth = 40.0
              style {
                fontSize = 24.px
              }
              bind(minutes)
              gridpaneConstraints {
                columnRowIndex(1, 1)
              }
            }
            label(":") {
              style {
                fontSize = 24.px
              }
              gridpaneConstraints {
                columnRowIndex(2, 1)
              }
            }
            label(seconds) {
              prefWidth = 40.0
              style {
                fontSize = 24.px
              }
              bind(seconds)
              gridpaneConstraints {
                columnRowIndex(3, 1)
              }
            }
            button("+") {
              action {
                incrementM()
              }
              gridpaneConstraints {
                columnRowIndex(1, 2)
              }
            }
            button("-") {
              action {
                decrementM()
              }
              gridpaneConstraints {
                columnRowIndex(1, 3)
              }
            }
            button("+") {
              action {
                incrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 2)
              }
            }
            button("-") {
              action {
                decrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 3)
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
                launch { planExecutor(scheduleBuilder(planList)) }
                replaceWith(InProgress::class)
              }
              gridpaneConstraints {
                columnRowIndex(1, 4)
                columnSpan = 3
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
}

class InProgress : View() {
  override val root = borderpane()

  init {
    with(root) {
      center = vbox {
        progressbar()
        {
          thread {
            for (i in 1..100) {
              Platform.runLater { progress = i.toDouble() / 100.0 }
              Thread.sleep(100)
            }
          }
        }
        button("Done" ) {
          action {
            replaceWith(MainView::class)
          }
        }
      }
    }
  }
}