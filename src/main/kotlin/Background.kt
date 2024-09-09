package org.example

/**
 * Enum class representing background colors for the console.
 */
enum class Background(private val color: String) {
  BlackBackground("40"),
  RedBackground("41"),
  GreenBackground("42"),
  YellowBackground("43"),
  BlueBackground("44"),
  MagentaBackground("45"),
  CyanBackground("46"),
  WhiteBackground("47");

  /**
   * Returns the ANSI code for the background color.
   */
  fun print(): String = color
}