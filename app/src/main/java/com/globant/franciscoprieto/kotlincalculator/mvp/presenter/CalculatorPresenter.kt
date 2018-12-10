package com.globant.franciscoprieto.kotlincalculator.mvp.presenter

import com.globant.franciscoprieto.kotlincalculator.R
import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import com.globant.franciscoprieto.kotlincalculator.mvp.view.CalculatorView
import java.lang.NumberFormatException
import java.text.DecimalFormat

const val EMPTY_STRING = ""
const val DECIMAL_FORMAT = "#.###"

class CalculatorPresenter(private val model: CalculatorModel, private val view: CalculatorView) {

    fun numberPressed(number: String) {
        when {
            model.result != EMPTY_STRING -> {
                model.clean()
                model.operand_1 = number
                view.setVisor(model.operand_1)
                view.setFormula(EMPTY_STRING)
            }
            model.operator == EMPTY_STRING -> {
                model.operand_1 += number
                view.setVisor(model.operand_1)
            }
            else -> {
                model.operand_2 += number
                view.setVisor(model.operand_2)
            }
        }
    }

    fun operatorPressed(operator: String) {
        if (model.operand_1 != EMPTY_STRING) {
            if (model.operand_2 != EMPTY_STRING) {
                calculateResult()
                model.operand_1 = model.result
                model.clean()
            }
            view.setFormula(model.operand_1 + operator)
            view.setVisor(EMPTY_STRING)
            model.operator = operator
        }
    }


    fun backspacePressed() {

    }

    private fun calculateResult() {
        var auxResult: Float? = null

        try {
            when (model.operator) {
                view.activity?.getString(R.string.plus_sign) -> {
                    auxResult = model.operand_1.toFloat() + model.operand_2.toFloat()
                }
                view.activity?.getString(R.string.minus_sign) -> {
                    auxResult = model.operand_1.toFloat() - model.operand_2.toFloat()
                }
                view.activity?.getString(R.string.product_sign) -> {
                    auxResult = model.operand_1.toFloat() * model.operand_2.toFloat()
                }
                view.activity?.getString(R.string.divide_sign) -> {
                    auxResult = model.operand_1.toFloat() / model.operand_2.toFloat()
                }
            }
        } catch (e: NumberFormatException) {
            view.showError()
            model.clean()
            model.operand_1 = EMPTY_STRING
            view.setVisor(EMPTY_STRING)
            view.setFormula(EMPTY_STRING)
        }
        if (auxResult != null) {
            val decimalFormat = DecimalFormat(DECIMAL_FORMAT)
            model.result = decimalFormat.format(auxResult)
            view.setFormula(model.operand_1 + model.operator + model.operand_2)
            view.setVisor(model.result)
        }
    }


    fun resultPressed() {
        if (model.operand_1 != EMPTY_STRING && model.operand_2 != EMPTY_STRING) {
            calculateResult()
        }
    }

    fun pointPressed() {

    }

}

