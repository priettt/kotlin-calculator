package com.globant.franciscoprieto.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.globant.franciscoprieto.kotlincalculator.mvp.model.CalculatorModel
import com.globant.franciscoprieto.kotlincalculator.mvp.presenter.CalculatorPresenter
import com.globant.franciscoprieto.kotlincalculator.mvp.view.CalculatorView
import com.globant.franciscoprieto.kotlincalculator.utils.bus.RxBus
import kotlinx.android.synthetic.main.activity_main.button_0
import kotlinx.android.synthetic.main.activity_main.button_1
import kotlinx.android.synthetic.main.activity_main.button_2
import kotlinx.android.synthetic.main.activity_main.button_3
import kotlinx.android.synthetic.main.activity_main.button_4
import kotlinx.android.synthetic.main.activity_main.button_5
import kotlinx.android.synthetic.main.activity_main.button_6
import kotlinx.android.synthetic.main.activity_main.button_7
import kotlinx.android.synthetic.main.activity_main.button_8
import kotlinx.android.synthetic.main.activity_main.button_9
import kotlinx.android.synthetic.main.activity_main.button_backspace
import kotlinx.android.synthetic.main.activity_main.button_divide
import kotlinx.android.synthetic.main.activity_main.button_equal
import kotlinx.android.synthetic.main.activity_main.button_minus
import kotlinx.android.synthetic.main.activity_main.button_plus
import kotlinx.android.synthetic.main.activity_main.button_point
import kotlinx.android.synthetic.main.activity_main.button_product


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var presenter: CalculatorPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = CalculatorPresenter(CalculatorModel(), CalculatorView(this))

        button_0.setOnClickListener(this)
        button_1.setOnClickListener(this)
        button_2.setOnClickListener(this)
        button_3.setOnClickListener(this)
        button_4.setOnClickListener(this)
        button_5.setOnClickListener(this)
        button_6.setOnClickListener(this)
        button_7.setOnClickListener(this)
        button_8.setOnClickListener(this)
        button_9.setOnClickListener(this)
        button_plus.setOnClickListener(this)
        button_minus.setOnClickListener(this)
        button_product.setOnClickListener(this)
        button_divide.setOnClickListener(this)
        button_equal.setOnClickListener(this)
        button_backspace.setOnClickListener(this)
        button_point.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.clear(this)
    }

    override fun onClick(v: View) {
        val pressed: String = (v as Button).text.toString()
        when (v.id) {
            R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_0 -> {
                presenter?.numberPressed(pressed)
            }
            R.id.button_plus, R.id.button_minus, R.id.button_product, R.id.button_divide -> {
                presenter?.operatorPressed(pressed)
            }
            R.id.button_equal -> {
                presenter?.resultPressed()
            }
            R.id.button_point -> {
                presenter?.pointPressed()
            }
            R.id.button_backspace -> {
                presenter?.backspacePressed()
            }
        }
    }
}
