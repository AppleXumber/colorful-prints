package org.example

/**
 * Class responsible for printing the colored and styled mesages to the console.
 * */
class Kolorful {
  private val createString = CreateString()

  /**
   * Prints a message to the console with optional color, background color, and styles.
   *
   * @param message The message to be printed.
   * @param color The text color. Can be a [Color], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param backgroundColor The background color. Can be a [Background], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param styles An array of [Style] options to apply to the text (e.g., bold, italic). You must pass them using `arrayOf(Style.Bold, Style.Underlined)` or as a vararg.
   */
  fun println(
    message: Any,
    color: Any? = null,
    backgroundColor: Any? = null,
    vararg styles: Style
  ) {
    val formatedMessage = createString.createString(message, color, backgroundColor, *styles)
    kotlin.io.println(formatedMessage)
  }

  /**
   * Prints a message without a newline to the console with optional color, background color, and styles.
   *
   * @param message The message to be printed.
   * @param color The text color. Can be a [Color], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param backgroundColor The background color. Can be a [Background], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param styles An array of [Style] options to apply to the text (e.g., bold, italic). You must pass them using `arrayOf(Style.Bold, Style.Underlined)` or as a vararg.
   */
  fun print(
    message: Any,
    color: Any? = null,
    backgroundColor: Any? = null,
    vararg styles: Style
  ) {
    val formatedMessage = createString.createString(message, color, backgroundColor, *styles)
    kotlin.io.print(formatedMessage)
  }

  /**
   * Displays all 256 supported colors in the console, divided into 16 items per line.
   */
  fun show256Colors() {
    for (i in 0..255) {
      kotlin.io.print("\u001B[38;5;${i}m$i ")
      if (i % 16 == 15) kotlin.io.println("\u001B[0m")
    }
  }
}