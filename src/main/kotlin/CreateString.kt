package org.example

/**
 * Class responsible for creating formatted strings with colors, background colors, and styles.
 */
class CreateString {
  /**
   * Creates a formatted string with optional color, background color, and styles.
   *
   * @param message The message to be formatted.
   * @param color The text color. Can be a [Color], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
   * @param backgroundColor The background color. Can be a [Color], an [Int] representing a 256-color code, or a [String] in the RGB format ("r;g;b").
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
      is Color -> (backgroundColor.toBackground())
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