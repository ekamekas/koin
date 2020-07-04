package com.github.ekamekas.koin.transaction.ext

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import java.util.*
import kotlin.math.pow

@Throws(NumberFormatException::class)
fun String.toCurrency(): String {
    return this
        .split(' ').filter { it != "" }
        .joinToString(" ") {
            it.toDoubleOrNull()?.toCurrency(appendWithCode = false) ?: it
        }
}

/**
 * Convert base64 encoded image to drawable
 *
 * @param context context of resource
 *
 * @return Drawable
 */
fun String.toDrawable(context: Context): Drawable? {
    return try {
        fromBase64()?.let { decoded ->
            BitmapDrawable(context.resources,
                BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
            )
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Decode string representation of base64
 */
fun String.fromBase64(): ByteArray? {
    return Base64.decode(this, Base64.DEFAULT)
}

/**
 * Parse and calculate mathematical operation from string
 */
@Throws(IllegalStateException::class)
fun String.parseThenCalculate(): Double {
    return replace(Regex("(\\s[+*\\-/.]\\s)$"), "").parseInfixToRPD().calculateFromRPN()
}

// parsing mathematical operation from infix to RPN
// source: https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Kotlin
private fun String.parseInfixToRPD(): String {
    val operator = "-+/*^"
    val sb = StringBuilder()
    val s = Stack<Int>()
    val rx = Regex("""\s""")
    for (token in this.split(rx)) {
        if (token.isEmpty()) continue
        val c = token[0]
        val idx = operator.indexOf(c)

        // check for operator
        if (idx != - 1) {
            if (s.isEmpty()) {
                s.push(idx)
            }
            else {
                while (!s.isEmpty()) {
                    val prec2 = s.peek() / 2
                    val prec1 = idx / 2
                    if (prec2 > prec1 || (prec2 == prec1 && c != '^')) {
                        sb.append(operator[s.pop()]).append(' ')
                    }
                    else break
                }
                s.push(idx)
            }
        }
        else if (c == '(') {
            s.push(-2)  // -2 stands for '('
        }
        else if (c == ')') {
            // until '(' on stack, pop operators.
            while (s.peek() != -2) sb.append(operator[s.pop()]).append(' ')
            s.pop()
        }
        else {
            sb.append(token).append(' ')
        }
    }
    while (!s.isEmpty()) sb.append(operator[s.pop()]).append(' ')
    return sb.toString()
}

// calculate mathematical operation from RPN
// source https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Kotlin
@Throws(IllegalStateException::class)
private fun String.calculateFromRPN(): Double {
    if (this.isEmpty()) return 0.0
    val tokens = this.split(' ').filter { it != "" }
    val stack = mutableListOf<Double>()
    for (token in tokens) {
        val d = token.toDoubleOrNull()
        if (d != null) {
            stack.add(d)
        }
        else if ((token.length > 1) || (token !in "+-*/^")) {
            throw IllegalArgumentException("$token is not a valid token")
        }
        else if (stack.size < 2) {
            throw IllegalArgumentException("Stack contains too few operands")
        }
        else {
            val d1 = stack.removeAt(stack.lastIndex)
            val d2 = stack.removeAt(stack.lastIndex)
            stack.add(when (token) {
                "+"  -> d2 + d1
                "-"  -> d2 - d1
                "*"  -> d2 * d1
                "/"  -> d2 / d1
                else -> d2.pow(d1)
            })
        }
    }
    return stack[0]
}