/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.sun.prism.paint.Color
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
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

var planList: List<Plan> = listOf()

class MainView : View() {
  override val root = HBox()

  init {
    with(root) {
      style {
        //How do we remove the button bar? Mmmm
      }
      prefHeight = 240.0
      prefWidth = 320.0

      var minutes = 5
      var seconds = 0
      val timeString = SimpleStringProperty("$minutes:0$seconds")

      fun incrementM() {
        minutes+= 1
        if(seconds < 10) {
          timeString.value = "$minutes:0$seconds"
        } else {
          timeString.value = "$minutes:$seconds"
        }
      }

      fun decrementM() {
        when (minutes) {
          0 -> println("Nope.")
          else -> minutes--
        }
        if(seconds < 10) {
          timeString.value = "$minutes:0$seconds"
        } else {
          timeString.value = "$minutes:$seconds"
        }
      }

      fun incrementS() {
        when (seconds) {
          59 -> {
            minutes++; seconds = 0
          }
          else -> seconds++
        }
        if(seconds < 10) {
          timeString.value = "$minutes:0$seconds"
        } else {
          timeString.value = "$minutes:$seconds"
        }
      }

      fun decrementS() {
        when (seconds) {
          0 -> if (seconds == 0) {
            when (minutes) {
              0 -> println("Nope")
              else -> {
                minutes--; seconds = 59
              }
            }
          }
          else -> seconds--
        }
        if(seconds < 10) {
          timeString.value = "$minutes:0$seconds"
        } else {
          timeString.value = "$minutes:$seconds"
        }
      }

      tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab("B&W", GridPane()) {
          prefWidth = 320.0
          gridpane {
            label(timeString) {
              prefWidth = 100.0
              style {
                fontSize = 24.px
              }
              bind(timeString)
              gridpaneConstraints {
                columnRowIndex(1, 1)
                columnSpan = 3
              }
            }
            button("+") {
              prefWidth = 30.0
              action {
                incrementM()
              }
              gridpaneConstraints {
                columnRowIndex(1, 2)
              }
            }
            button("-") {
              prefWidth = 30.0
              action {
                decrementM()
              }

              gridpaneConstraints {
                columnRowIndex(1, 3)
              }
            }
            button("+") {
              prefWidth = 30.0
              action {
                incrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 2)
              }
            }
            button("-") {
              prefWidth = 30.0
              action {
                decrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 3)
              }
            }
            button("Start B&W") {
              action {
                val time = (minutes * 60) + (seconds)
                planList = listOf(
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
  override val root = VBox()

  init {
    with(root) {
      var time = 0
      scheduleBuilder(planList).forEach { step -> time += step.time }
      val timeLabelString = SimpleStringProperty("$time")
      label("time") {
        bind(timeLabelString)
        style {
          fontSize = 40.px
        }
      }
      progressbar()
      {
        prefWidth = 320.0
        prefHeight = 70.0
        val thr = thread {
          scheduleBuilder(planList).forEach { step -> time += step.time }
          for (i in 1..time) {
            Platform.runLater { progress = i.toDouble() / time.toDouble() }
            Thread.sleep(1000)
            Platform.runLater {
              val m = (time - i) / 60;
              val s = (time - i) % 60;
              if (s < 10) {
                val str = "$m:0$s";timeLabelString.value = str
              } else {
                val str = "$m:$s";timeLabelString.value = str
              }
            }
          }
        }
      }
      button("Done") {
        action {
          replaceWith(MainView::class)
        }
      }
    }
  }
}