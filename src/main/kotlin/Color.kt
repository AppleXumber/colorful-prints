package org.example

/**
 * Enum class representing text colors for the console.
 */
enum class Color(private val color: String) {
  Black("30"),
  Red("31"),
  Green("32"),
  Yellow("33"),
  Blue("34"),
  Magenta("35"),
  Cyan("36"),
  White("37");

  /**
   * Returns the ANSI code for the text color.
   */
  fun print(): String = color
}