package org.example

fun main() {
  val kolor = ColorfulPrint()
  kolor.println("Testando uma string:", "0;2500;2500")
  kolor.println(
    "Teste2",
    color = Color.Red,
    backgroundColor = Background.BlackBackground,
    styles = arrayOf(Style.Bold)
  )



}

/**
 * Class responsible for printing the colored and styled mesages to the console.
 * */
class ColorfulPrint {
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

/**
 * Class responsible for creating formatted strings with colors, background colors, and styles.
 */
class CreateString {
  /**
   * Creates a formatted string with optional color, background color, and styles.
   *
   * @param message The message to be formatted.
   * @param color The text color. Can be a [Color], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param backgroundColor The background color. Can be a [Background], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param styles A variable number of [Style] options to apply to the text (e.g., bold, italic).
   * @return The formatted string with ANSI escape codes.
   */
  fun createString(
    message: Any,
    color: Any? = null,
    backgroundColor: Any? = null,
    vararg styles: Style
  ): String {
    val initializer = "\u001B["
    val finalizer = "\u001B[0m"

    val colorCode = when (color) {
      is Color -> color.print()
      is Int -> "38;5;${color}"
      is String -> "38;2;${this.formatAndValidateRGB(color)}"
      else -> null
    }

    val backgroundcolorCode = when (backgroundColor) {
      is Background -> backgroundColor.print()
      is Int -> "48;5;${backgroundColor}"
      is String -> "48;2;${this.formatAndValidateRGB(backgroundColor)}"
      else -> null
    }

    val styleCodes = styles.map { it.print() }
    val ansiCode = listOfNotNull(
      backgroundcolorCode,
      *styleCodes.toTypedArray(),
      colorCode
    ).joinToString(";")

    return (
            if (ansiCode.isNotBlank()) "$initializer${ansiCode}m$message$finalizer"
            else message.toString()
            )
  }

  /**
   * Formats and validates a string in RGB format ("r;g;b").
   *
   * @param string The RGB string to validate.
   * @return A validated RGB string.
   * @throws IllegalArgumentException if the input string is not a valid RGB format.
   */
  private fun formatAndValidateRGB(string: String): String {
    val parts = string.replace(" ", "").split(";").toMutableList()
    if (parts.size != 3) throw IllegalArgumentException("Invalid RGB format")
    val r = parts[0].toIntOrNull()?.coerceIn(0, 255) ?: 0
    val g = parts[1].toIntOrNull()?.coerceIn(0, 255) ?: 0
    val b = parts[2].toIntOrNull()?.coerceIn(0, 255) ?: 0

    return "$r;$g;$b"
  }
}

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

/**
 * Enum class representing text styles for the console.
 */
enum class Style(private val style: String) {
  Bold("1"),
  Underlined("4"),
  Italic("3"),
  Strikethrough("9");
  /**
   * Returns the ANSI code for the style.
   */
  fun print(): String = style
}

/**
 * Enum class representing text colors for the console.
 */
enum class Color(private val color: String) {
  Red("31"),
  Blue("34"),
  Black("30"),
  Green("32"),
  Yellow("33"),
  Magenta("35"),
  Cyan("36"),
  White("37");

  /**
   * Returns the ANSI code for the text color.
   */
  fun print(): String = color
}
