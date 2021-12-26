package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiactivityBinding
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiactivityBinding? = null
    companion object {
        private const val  METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding!!.toolbarBmiScreen)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBmiScreen?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.rgUnits?.setOnCheckedChangeListener{ _, checkId: Int ->
            if(checkId == R.id.rbMetric) {
                makeVisibleMetricUnitsView()
            }
            else {
                makeVisibleUsUnitsView()
            }
        }
        btnCalculate.setOnClickListener {
            calculateUnits()
        }
    }
    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue : Float = binding?.enterHeight?.text.toString().toFloat() / 100
                val weightValue : Float = binding?.enterWeight?.text.toString().toFloat()
                val bmi = weightValue/(heightValue * heightValue)
                displayBMI(bmi)

            }
            else {
                Toast.makeText(this,"You should enter weight and height",
                    Toast.LENGTH_SHORT).show()
            }
        }
        else {
            if (validateUsUnits()) {
                val weightValue: Float = binding?.enterWeight1?.text.toString().toFloat()
                val feetValue: Float = binding?.enterFeet?.text.toString().toFloat()
                val inchValue: Float = binding?.enterInch?.text.toString().toFloat()
                val heightValue: Float = inchValue + feetValue*12
                val bmi = 703 * (weightValue/ (heightValue * heightValue))
                displayBMI(bmi)
            }
            else {
                Toast.makeText(this,"You should enter weight and inch and feet",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        binding?.edtHeight?.visibility = View.VISIBLE // METRIC  Height UNITS VIEW is Visible
        binding?.edtWeight?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
        binding?.edtWeight1?.visibility = View.GONE // make weight view Gone.
        binding?.edtFeet?.visibility = View.GONE // make height feet view Gone.
        binding?.edtInch?.visibility = View.GONE // make height inch view Gone.

        binding?.enterHeight?.text!!.clear() // height value is cleared if it is added.
        binding?.enterWeight?.text!!.clear() // weight value is cleared if it is added.

        binding?.llBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.edtHeight?.visibility = View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.edtWeight?.visibility = View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.edtWeight1?.visibility = View.VISIBLE // make weight view visible.
        binding?.edtFeet?.visibility = View.VISIBLE // make height feet view visible.
        binding?.edtInch?.visibility = View.VISIBLE // make height inch view visible.

        binding?.enterWeight1?.text!!.clear() // weight value is cleared.
        binding?.enterFeet?.text!!.clear() // height feet value is cleared.
        binding?.enterInch?.text!!.clear() // height inch is cleared.

        binding?.llBMIResult?.visibility = View.INVISIBLE
    }
    private fun displayBMI(bmi: Float) {

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        tvResult.text = bmiValue.toString()
        if (bmi < 18.5) {
            tvStatus.text = "skinny body"
        }
        else if (bmi in 18.5..22.9) {
            tvStatus.text = "balanced bodybuilding"
        }
        else if (bmi > 22.8) {
            tvStatus.text = "Over weight"
        }
        if (bmi < 18.5) {
            tvComment.text = "Oh, you should eat more "
        }
        else if (bmi in 18.5..22.9) {
            tvComment.text = "Great. Keep eating bro"
        }
        else if (bmi > 22.8) {
            tvComment.text = "Opps. You should reduce your food"
        }
        binding?.llBMIResult?.visibility = View.VISIBLE
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.enterHeight?.text.toString().isEmpty()) {
            isValid = false
        }else if (binding?.enterWeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
    private fun validateUsUnits() : Boolean {
        var isValid = true
        if (binding?.enterWeight1?.text.toString().isEmpty()) {
            isValid = false
        }else if (binding?.enterFeet?.text.toString().isEmpty()) {
            isValid = false
        }
        else if (binding?.enterInch?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
}