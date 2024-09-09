package org.example

fun main() {
  val colorfulPrint = ColorfulPrint()
  val myString = "Uma string qualquer"

  println(myString)
  colorfulPrint.println(myString, "0;0;255", Background.BlackBackground, Style.Underlined)
}



