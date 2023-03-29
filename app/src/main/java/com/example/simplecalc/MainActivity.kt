package com.example.simplecalc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "CalculatorActivity"

    private var mCalculator: Calculator? = null

    private var mOperandOneEditText: EditText? = null
    private var mOperandTwoEditText: EditText? = null

    private var mResultTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the calculator class and all the views
        mCalculator = Calculator()
        mResultTextView = findViewById(R.id.operation_result_text_view)
        mOperandOneEditText = findViewById(R.id.operand_one_edit_text)
        mOperandTwoEditText = findViewById(R.id.operand_two_edit_text)
    }

    fun onAdd(view: View?) {
        compute(Calculator.Operator.ADD)
    }

    fun onSub(view: View?) {
        compute(Calculator.Operator.SUB)
    }

    /**
     * OnClick method called when the divide Button is pressed.
     */
    fun onDiv(view: View?) {
        try {
            compute(Calculator.Operator.DIV)
        } catch (iae: IllegalArgumentException) {
            Log.e(TAG, "IllegalArgumentException", iae)
            mResultTextView!!.text = getString(R.string.computationError)
        }
    }

    fun onMul(view: View?) {
        compute(Calculator.Operator.MUL)
    }

    private fun compute(operator: Calculator.Operator) {
        val operandOne: Double
        val operandTwo: Double
        try {
            operandOne = getOperand(mOperandOneEditText)
            operandTwo = getOperand(mOperandTwoEditText)
        } catch (nfe: NumberFormatException) {
            Log.e(TAG, "NumberFormatException", nfe)
            mResultTextView!!.text = getString(R.string.computationError)
            return
        }
        val result: String
        result = when (operator) {
            Calculator.Operator.ADD  -> java.lang.String.valueOf(
                mCalculator!!.add(operandOne, operandTwo)
            )
            Calculator.Operator.SUB -> java.lang.String.valueOf(
                mCalculator!!.sub(operandOne, operandTwo)
            )
            Calculator.Operator.DIV -> java.lang.String.valueOf(
                mCalculator!!.div(operandOne, operandTwo)
            )
            Calculator.Operator.MUL -> java.lang.String.valueOf(
                mCalculator!!.mul(operandOne, operandTwo)
            )
            else -> getString(R.string.computationError)
        }
        mResultTextView!!.text = result
    }

    private fun getOperand(operandEditText: EditText?): Double {
        val operandText = getOperandText(operandEditText)
        return java.lang.Double.valueOf(operandText)
    }

    private fun getOperandText(operandEditText: EditText?): String {
        return operandEditText!!.text.toString()
    }
}