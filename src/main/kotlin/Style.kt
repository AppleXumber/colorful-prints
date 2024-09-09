package org.example

/**
 * Enum class representing text styles for the console.
 */
enum class Style(private val style: String) {
  Bold("1"),
  Italic("3"),
  Strikethrough("9"),
  Underlined("4");
  /**
   * Returns the ANSI code for the style.
   */
  fun print(): String = style
}