package com.globant.franciscoprieto.kotlincalculator.mvp.model

class CalculatorModel {

    var operand_1: String = ""
    var operand_2: String = ""
    var operator: String = ""
    var result: String = ""


    fun clean() {
        operand_2 = ""
        operator = ""
        result = ""
    }

}
