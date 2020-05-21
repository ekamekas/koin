package com.github.ekamekas.koin.transaction.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.github.ekamekas.koin.transaction.R
import com.github.ekamekas.koin.transaction.ext.parseThenCalculate

/**
 * Widget to input numeric text with keyboard interface. Has the ability to calculate numeric value with basic mathematics operator
 */
class NumericKeyboard @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attr, defStyleAttr) {

    // data state
    private var mText: String? = null
    private var mOperationResult: Double? = null

    // listener
    private var mOnClick: ((text: String, operationResult: Double) -> Unit)? = null

    // view
    private var keyOne: View
    private var keyTwo: View
    private var keyThree: View
    private var keyFour: View
    private var keyFive: View
    private var keySix: View
    private var keySeven: View
    private var keyEight: View
    private var keyNine: View
    private var keyDecimal: View
    private var keyZero: View
    private var keyRemove: View
    private var keyAddition: View
    private var keyMultiplication: View
    private var keyDecrease: View
    private var keyDivision: View
    private var keySummation: View

    init {
        val view = View.inflate(context, R.layout.widget_numeric_keyboard, this)

        // assign instance
        keyOne = view.findViewById(R.id.key_1)
        keyTwo = view.findViewById(R.id.key_2)
        keyThree = view.findViewById(R.id.key_3)
        keyFour = view.findViewById(R.id.key_4)
        keyFive = view.findViewById(R.id.key_5)
        keySix = view.findViewById(R.id.key_6)
        keySeven = view.findViewById(R.id.key_7)
        keyEight = view.findViewById(R.id.key_8)
        keyNine = view.findViewById(R.id.key_9)
        keyDecimal = view.findViewById(R.id.key_decimal)
        keyZero = view.findViewById(R.id.key_0)
        keyRemove = view.findViewById(R.id.key_remove)
        keyAddition = view.findViewById(R.id.key_addition)
        keyMultiplication = view.findViewById(R.id.key_multiplication)
        keyDecrease = view.findViewById(R.id.key_decrease)
        keyDivision = view.findViewById(R.id.key_division)
        keySummation = view.findViewById(R.id.key_summation)

        // attach listener
        keyOne.setOnClickListener { onKeyOneClick() }
        keyTwo.setOnClickListener { onKeyTwoClick() }
        keyThree.setOnClickListener { onKeyThreeClick() }
        keyFour.setOnClickListener { onKeyFourClick() }
        keyFive.setOnClickListener { onKeyFiveClick() }
        keySix.setOnClickListener { onKeySixClick() }
        keySeven.setOnClickListener { onKeySevenClick() }
        keyEight.setOnClickListener { onKeyEightClick() }
        keyNine.setOnClickListener { onKeyNineClick() }
        keyDecimal.setOnClickListener { onKeyDecimalClick() }
        keyZero.setOnClickListener { onKeyZeroClick() }
        keyRemove.setOnClickListener { onKeyRemoveClick() }
        keyAddition.setOnClickListener { onKeyAdditionClick() }
        keyMultiplication.setOnClickListener { onKeyMultiplicationClick() }
        keyDecrease.setOnClickListener { onKeyDecreaseClick() }
        keyDivision.setOnClickListener { onKeyDivisionClick() }
        keySummation.setOnClickListener { onKeySummationClick() }
    }

    /**
     * Set on click listener
     */
    fun setOnClickListener(func: (text: String, operationResult: Double) -> Unit) {
        mOnClick = func
    }

    /**
     * Set text
     */
    fun setText(text: String) {
        onTextChange(text)
    }

    // callback
    private fun onKeyOneClick() {
        appendText("1")
    }
    private fun onKeyTwoClick() {
        appendText("2")
    }
    private fun onKeyThreeClick() {
        appendText("3")
    }
    private fun onKeyFourClick() {
        appendText("4")
    }
    private fun onKeyFiveClick() {
        appendText("5")
    }
    private fun onKeySixClick() {
        appendText("6")
    }
    private fun onKeySevenClick() {
        appendText("7")
    }
    private fun onKeyEightClick() {
        appendText("8")
    }
    private fun onKeyNineClick() {
        appendText("9")
    }
    private fun onKeyDecimalClick() {
        appendText(context.getString(R.string.label_widget_keyboard_key_decimal))
    }
    private fun onKeyZeroClick() {
        appendText("0")
    }
    private fun onKeyRemoveClick() {
        removeText()
    }
    private fun onKeyAdditionClick() {
        appendText(" + ")
    }
    private fun onKeyMultiplicationClick() {
        appendText(" * ")
    }
    private fun onKeyDecreaseClick() {
        appendText(" - ")
    }
    private fun onKeyDivisionClick() {
        appendText(" / ")
    }
    private fun onKeySummationClick() {
        try {
            calculateOperationResult(mText)
            mText = mOperationResult?.toString()?.replace(".", context.getString(R.string.label_widget_keyboard_key_decimal))
        } catch (o_O: Exception) {

        } finally {
            mOnClick?.invoke(mText ?: "", mOperationResult ?: 0.0)
        }
    }
    private fun onTextChange(text: String?){
        try {
            calculateOperationResult(text)
            mText = text
        } catch (o_O: Exception) {

        } finally {
            mOnClick?.invoke(mText ?: "", mOperationResult ?: 0.0)
        }
    }

    private fun appendText(text: String) {
        val result = mText?.plus(text) ?: if(text.contains(Regex("\\s[+*\\-/.]\\s"))) {
            "0.0".plus(text)
        } else {
            text
        }
        onTextChange(result)
    }
    @Throws(Exception::class)
    private fun calculateOperationResult(text: String?) {
        mOperationResult = text?.replace(context.getString(R.string.label_widget_keyboard_key_decimal), ".")?.parseThenCalculate()
    }
    private fun removeText() {
        onTextChange(null)
    }

}