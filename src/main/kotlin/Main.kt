package org.example

fun main() {
  val kolor = ColorfulPrint()
  kolor.println(200.8, "-255;00;250")
  kolor.println(
    "Teste2",
    color = Color.Red,
    backgroundColor = Background.BlackBackground,
    styles = arrayOf(Style.Bold)
  )
}

class ColorfulPrint {
  private val createString = CreateString()

  fun println(
    message: Any,
    color: Any? = null,
    backgroundColor: Any? = null,
    vararg styles: Style
  ) {
    val formatedMessage = createString.createString(message, color, backgroundColor, *styles)
    kotlin.io.println(formatedMessage)
  }

  fun print(
    message: Any,
    color: Any? = null,
    backgroundColor: Any? = null,
    vararg styles: Style
  ) {
    val formatedMessage = createString.createString(message, color, backgroundColor, *styles)
    kotlin.io.print(formatedMessage)
  }

  fun show255Colors() {
    for (i in 0..255) {
      kotlin.io.print("\u001B[38;5;${i}m$i ")
      if (i % 16 == 15) kotlin.io.println("\u001B[0m")
    }
  }

  private inner class CreateString {
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

    private fun formatAndValidateRGB(string: String): String {
      val parts = string.replace(" ", "").split(";").toMutableList()
      if (parts.size != 3) throw IllegalArgumentException("Invalid RGB format")
      val r = parts[0].toIntOrNull()?.coerceIn(0, 255) ?: 0
      val g = parts[1].toIntOrNull()?.coerceIn(0, 255) ?: 0
      val b = parts[2].toIntOrNull()?.coerceIn(0, 255) ?: 0

      return "$r;$g;$b"
    }
  }
}

enum class Background(private val color: String) {
  BlackBackground("40"),
  RedBackground("41"),
  GreenBackground("42"),
  YellowBackground("43"),
  BlueBackground("44"),
  MagentaBackground("45"),
  CyanBackground("46"),
  WhiteBackground("47");

  fun print(): String = color
}

enum class Style(private val style: String) {
  Bold("1"),
  Underlined("4"),
  Italic("3"),
  Strikethrough("9");

  fun print(): String = style
}

enum class Color(private val color: String) {
  Red("31"),
  Blue("34"),
  Black("30"),
  Green("32"),
  Yellow("33"),
  Magenta("35"),
  Cyan("36"),
  White("37");

  fun print(): String = color
}
