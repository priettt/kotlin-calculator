package com.globant.franciscoprieto.kotlincalculator.mvp.presenter

import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import com.globant.franciscoprieto.kotlincalculator.mvp.view.CalculatorView
import com.globant.franciscoprieto.kotlincalculator.utils.bus.RxBus
import com.globant.franciscoprieto.kotlincalculator.utils.bus.observer.*

class CalculatorPresenter(val model: CalculatorModel, val view: CalculatorView) {

    fun numberPressed(number: String) {
        if (model.activeOperand == 1) {
            when (model.operand_1) {
                "", "0" -> {
                    model.operand_1 = number
                }
                else -> {
                    model.operand_1 += number
                }
            }
            view.setVisor(model.operand_1)
        }
    }

    fun operatorPressed(operator: String) {

    }

    fun backspacePressed() {

    }

    fun resultPressed() {

    }

    fun pointPressed() {

    }

}

