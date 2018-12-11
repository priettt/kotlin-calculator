package com.globant.franciscoprieto.kotlincalculator

import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CalculatorModelTest {

    private var model: CalculatorModel? = null

    @Before
    fun setup() {
        model = CalculatorModel()
    }

    @Test
    fun cleanModelTest() {
        model?.operand_2 = NUMBER_TWO
        model?.operator = PLUS_OPERATOR
        model?.result = NUMBER_TWO
        model?.clean()
        assertEquals(model?.operand_2, EMPTY_STRING)
        assertEquals(model?.operator, EMPTY_STRING)
        assertEquals(model?.result, EMPTY_STRING)
    }

}