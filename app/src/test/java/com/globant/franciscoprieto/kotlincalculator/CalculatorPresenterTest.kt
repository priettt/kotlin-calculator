package com.globant.franciscoprieto.kotlincalculator

import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import com.globant.franciscoprieto.kotlincalculator.mvp.presenter.CalculatorPresenter
import com.globant.franciscoprieto.kotlincalculator.mvp.view.CalculatorView
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

const val NUMBER_TWO = "2"
const val NUMBER_FOUR = "4"
const val NUMBER_SIX = "6"
const val NUMBER_EIGHT = "8"
const val PLUS_OPERATOR = "+"
const val MINUS_OPERATOR = "-"
const val PRODUCT_OPERATOR = "×"
const val DIVIDE_OPERATOR = "/"
const val EMPTY_STRING = ""
const val POINT_SIGN = "."


class CalculatorPresenterTest {

    private var presenter: CalculatorPresenter? = null

    @Mock
    lateinit var model: CalculatorModel
    @Mock
    lateinit var view: CalculatorView
    @Mock
    lateinit var activity: MainActivity

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(view.activity).thenReturn(activity)
        presenter = CalculatorPresenter(model, view)
    }

    @Test
    fun numberPressedPreviousResult() {
        whenever(model.result).thenReturn(NUMBER_TWO)
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        presenter?.numberPressed(NUMBER_TWO)
        verify(model).clean()
        verify(model).operand_1 = NUMBER_TWO
        verify(view).setVisor(NUMBER_FOUR)
        verify(view).setFormula(EMPTY_STRING)
    }

    @Test
    fun numberPressedNoPreviousResultNoOperator() {
        whenever(model.result).thenReturn(EMPTY_STRING)
        whenever(model.operator).thenReturn(EMPTY_STRING)
        whenever(model.operand_1).thenReturn(NUMBER_TWO)
        presenter?.numberPressed(NUMBER_TWO)
        verify(model, times(2)).operand_1
        verify(view).setVisor(model.operand_1)
    }

    @Test
    fun numberPressedNoPreviousResultWithOperator() {
        whenever(model.result).thenReturn(EMPTY_STRING)
        whenever(model.operator).thenReturn(PLUS_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        presenter?.numberPressed(NUMBER_TWO)
        verify(view).setVisor(NUMBER_TWO)
        verify(model, times(2)).operand_2
    }

    @Test
    fun operatorPressedWithoutSecondOperandNorResult() {
        whenever(model.result).thenReturn(EMPTY_STRING)
        whenever(model.operand_2).thenReturn(EMPTY_STRING)
        whenever(model.operand_1).thenReturn(NUMBER_TWO)
        presenter?.operatorPressed(PLUS_OPERATOR)
        verify(view).setVisor(EMPTY_STRING)
        verify(view).setFormula("$NUMBER_TWO $PLUS_OPERATOR")
        verify(model).operator = PLUS_OPERATOR
    }

    @Test
    fun operatorPressedWithSecondOperand() {
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        whenever(model.operator).thenReturn(PLUS_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_FOUR)
        whenever(model.result).thenReturn(NUMBER_TWO)

        presenter?.operatorPressed(PLUS_OPERATOR)
        verify(model).clean()
        verify(model).operand_1 = NUMBER_TWO

    }

    // The following tests are done using the resultPressed() method, but they could also be done
    // with the operatorPressed() when there's already a second operand.
    @Test
    fun calculateResultOfASum() {
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        whenever(model.operator).thenReturn(PLUS_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(NUMBER_SIX)
        whenever(view.activity?.getString(R.string.plus_sign)).thenReturn(PLUS_OPERATOR)
        presenter?.resultPressed()
        verify(model).result = NUMBER_SIX
        verify(view).setVisor(NUMBER_SIX)
        verify(view).setFormula("$NUMBER_FOUR $PLUS_OPERATOR $NUMBER_TWO")
    }

    @Test
    fun calculateResultOfASubtraction() {
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        whenever(model.operator).thenReturn(MINUS_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(NUMBER_TWO)
        whenever(view.activity?.getString(R.string.minus_sign)).thenReturn(MINUS_OPERATOR)
        presenter?.resultPressed()
        verify(model).result = NUMBER_TWO
        verify(view).setVisor(NUMBER_TWO)
        verify(view).setFormula("$NUMBER_FOUR $MINUS_OPERATOR $NUMBER_TWO")
    }

    @Test
    fun calculateResultOfAProduct() {
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        whenever(model.operator).thenReturn(PRODUCT_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(NUMBER_EIGHT)
        whenever(view.activity?.getString(R.string.product_sign)).thenReturn(PRODUCT_OPERATOR)
        presenter?.resultPressed()
        verify(model).result = NUMBER_EIGHT
        verify(view).setVisor(NUMBER_EIGHT)
        verify(view).setFormula("$NUMBER_FOUR $PRODUCT_OPERATOR $NUMBER_TWO")
    }

    @Test
    fun calculateResultOfADivision() {
        whenever(model.operand_1).thenReturn(NUMBER_FOUR)
        whenever(model.operator).thenReturn(DIVIDE_OPERATOR)
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(NUMBER_TWO)
        whenever(view.activity?.getString(R.string.divide_sign)).thenReturn(DIVIDE_OPERATOR)
        presenter?.resultPressed()
        verify(model).result = NUMBER_TWO
        verify(view).setVisor(NUMBER_TWO)
        verify(view).setFormula("$NUMBER_FOUR $DIVIDE_OPERATOR $NUMBER_TWO")
    }

    @Test
    fun pointPressedFirstOperand() {
        whenever(model.operand_1).thenReturn(NUMBER_TWO)
        whenever(model.operator).thenReturn(EMPTY_STRING)
        presenter?.pointPressed()
        verify(model).operand_1 = "$NUMBER_TWO$POINT_SIGN"
        verify(view).setVisor(anyString())
    }

    @Test
    fun pointPressedSecondOperand() {
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(EMPTY_STRING)
        whenever(model.operator).thenReturn(PLUS_OPERATOR)
        presenter?.pointPressed()
        verify(model).operand_2 = "$NUMBER_TWO$POINT_SIGN"
        verify(view).setVisor(anyString())
    }

    @Test
    fun backspacePressedFirstOperand(){
        whenever(model.operand_1).thenReturn(NUMBER_TWO)
        whenever(model.operator).thenReturn(EMPTY_STRING)
        presenter?.backspacePressed()
        verify(model).operand_1 = EMPTY_STRING
        verify(view).setVisor(anyString())
    }

    @Test
    fun backspacePressedSecondOperand(){
        whenever(model.operand_2).thenReturn(NUMBER_TWO)
        whenever(model.result).thenReturn(EMPTY_STRING)
        whenever(model.operator).thenReturn(PLUS_OPERATOR)
        presenter?.backspacePressed()
        verify(model).operand_2 = EMPTY_STRING
        verify(view).setVisor(anyString())
    }

}