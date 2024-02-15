package com.mada.softpos.core.extension

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun String?.numberFormat(): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        isGroupingUsed = true
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(this?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
}