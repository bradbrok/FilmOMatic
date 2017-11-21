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
        minutes += 1
        if (seconds < 10) {
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
        if (seconds < 10) {
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
        if (seconds < 10) {
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
        if (seconds < 10) {
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
              style {
                fontSize = 40.px
                fontFamily = "Courier"
              }
              bind(timeString)
              gridpaneConstraints {
                columnRowIndex(1, 1)
                columnSpan = 10
              }
            }
            button("+") {
              prefWidth = 50.0
              action {
                incrementM()
              }
              gridpaneConstraints {
                columnRowIndex(1, 2)
              }
            }
            button("-") {
              prefWidth = 50.0
              action {
                decrementM()
              }

              gridpaneConstraints {
                columnRowIndex(1, 3)
              }
            }
            button("+") {
              prefWidth = 50.0
              action {
                incrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 2)
              }
            }
            button("-") {
              prefWidth = 50.0
              action {
                decrementS()
              }
              gridpaneConstraints {
                columnRowIndex(3, 3)
              }
            }
            button("Start B&W") {
              prefWidth = 100.0
              prefHeight = 50.0
              action {
                val time = (minutes * 60) + (seconds)
                planList = listOf(
                        Plan(Bath.WATER, 0, 60, true),
                        Plan(Bath.A, 10, time, true),
                        Plan(Bath.WATER, 0, 60, true),
                        Plan(Bath.B, 10, 300, false),
                        Plan(Bath.WATER, 0, 120, true)
                )
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
      val stepLabel = SimpleStringProperty("Idle")
      label("time ") {
        bind(timeLabelString)
        style {
          fontSize = 40.px
          fontFamily = "Courier"
        }
      }
      label("text") {
        bind(stepLabel)
        style {
          fontSize = 24.px
        }
      }
      progressbar()
      {
        prefWidth = 320.0
        prefHeight = 60.0
        launch {
          scheduleBuilder(planList).forEach { step -> time += step.time }
          time /= 2
          for (i in 1..time) {
            Platform.runLater { progress = (i.toDouble() / time.toDouble())}
            Platform.runLater {
              val m = (((time) - i) / 60)
              val s = (((time) - i) % 60)
              if (s < 10) {
                val str = "$m:0$s";timeLabelString.value = str
              } else {
                val str = "$m:$s";timeLabelString.value = str
              }
            }
            Thread.sleep(1000)
          }
        }
        launch {
          planExecutor(scheduleBuilder(planList))
        }
        launch {
          scheduleBuilder(planList).forEach { step ->
            val thisStepTime = step.time
            val thisStepFlows = step.flows
            val thisStepBath = step.bath
            Platform.runLater {
              stepLabel.value = "$thisStepBath, $thisStepFlows for ${thisStepTime}s"
            }
            Thread.sleep(thisStepTime.toLong() * 1000)
          }
        }
      }
    }
  }
}