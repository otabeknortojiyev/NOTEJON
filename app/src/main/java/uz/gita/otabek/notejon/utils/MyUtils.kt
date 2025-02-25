package uz.gita.otabek.notejon.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.longToDateString(): String {
    val date = java.util.Date(this)
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(date)
}

enum class AlignEnum(val value: String) {
    JUSTIFY("justify"), RIGHT("right"), CENTER("center"), LEFT("left")
}

enum class DragAnchors {
    Start,
    Center,
    End,
}