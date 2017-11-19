/*
 * Film-O-Matic Copyright (c) Bradley Brockman 2017.
 */

import com.sun.prism.paint.Color
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Button
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

      borderpane {
        top = hbox {
          button("B&W") {
            prefWidth = 80.0
            action{
              center = vbox {
                label("B&W")
                button("Start B&W")
              }
            }
          }
          button("C41") {
            prefWidth = 80.0
            action {
              center = vbox {
                label("C41")
              }
            }
          }
          button("E6") {
            prefWidth = 80.0
            action {
              center {
                label("E6")
              }
            }
          }
          button("Custom") {
            prefWidth = 80.0
            action {
              center {
                label("Custom Program")
              }
            }
          }
        }
        center {
          label("FILM-O-MATIC") {
          }
        }
      }
    }
  }
}
