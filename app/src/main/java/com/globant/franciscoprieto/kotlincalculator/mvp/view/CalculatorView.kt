package com.globant.franciscoprieto.kotlincalculator.mvp.view

import android.app.Activity
import android.widget.Toast
import com.globant.franciscoprieto.kotlincalculator.R
import kotlinx.android.synthetic.main.activity_main.*

class CalculatorView(activity: Activity) : ActivityView(activity) {

    fun setVisor(visor: String) {
        activity!!.text_calculator_visor.text = visor
    }

    fun setFormula(formula: String) {
        activity!!.text_calculator_formula.text = formula
    }

    fun showError() {
        Toast.makeText(activity, activity?.getString(R.string.error_invalid_operation), Toast.LENGTH_SHORT).show()
    }
}