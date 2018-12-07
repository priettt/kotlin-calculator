package com.globant.franciscoprieto.kotlincalculator.mvp.presenter

import com.globant.franciscoprieto.kotlincalculator.R
import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import com.globant.franciscoprieto.kotlincalculator.mvp.view.CalculatorView
import java.text.DecimalFormat

class CalculatorPresenter(private val model: CalculatorModel, private val view: CalculatorView) {

    fun numberPressed(number: String) {
        when {
            model.result != "" -> {
                model.clean()
                model.operand_1 = number
                view.setVisor(model.operand_1)
                view.setFormula("")
            }
            model.operator == "" -> {
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
        if (model.operand_1 != "") {
            if (model.operand_2 != "") {
                calculateResult()
            }
            if (model.result != "") {
                model.operand_1 = model.result
                model.clean()
            }
            view.setFormula(model.operand_1 + operator)
            view.setVisor("")
            model.operator = operator
        }
    }


    fun backspacePressed() {

    }

    private fun calculateResult() {
        var auxResult: Float? = null
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
                if (model.operand_2 != "0") {
                    auxResult = model.operand_1.toFloat() / model.operand_2.toFloat()
                }
            }
        }
        if (auxResult != null) {
            val decimalFormat = DecimalFormat("#.###")
            model.result = decimalFormat.format(auxResult)
            view.setFormula(model.operand_1 + model.operator + model.operand_2)
            view.setVisor(model.result)
        }
    }


    fun resultPressed() {
        if (model.operand_1 != "" && model.operand_2 != "") {
            calculateResult()
        }
    }

    fun pointPressed() {

    }

}

