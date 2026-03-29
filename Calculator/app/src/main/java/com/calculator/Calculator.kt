package com.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calculator.databinding.ActivityCalculatorBinding
import java.text.DecimalFormat

class Calculator : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private var lastTheme: String? = null

    private var currentNumber = "0"

    private var lastNumber = 0.0
    private var currentOperation = ""
    private var toggleOperation = false
    private var toggleResult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("theme", MODE_PRIVATE)
        lastTheme = prefs.getString("theme", "amber")

        when (lastTheme) {
            "amber" -> setTheme(R.style.Theme_Calculator_Amber)
            "baw" -> setTheme(R.style.Theme_Calculator_BlackAndWhite)
            "bag" -> setTheme(R.style.Theme_Calculator_BlueAndGray)
            "green" -> setTheme(R.style.Theme_Calculator_Green)
            "cyan" -> setTheme(R.style.Theme_Calculator_Cyan)
            "gray" -> setTheme(R.style.Theme_Calculator_Gray)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Toast.makeText(this, "On Create Calculator", Toast.LENGTH_SHORT).show()

        binding.changeTheme.setOnClickListener {
            val intent = Intent(this, ChangeTheme::class.java)
            startActivity(intent)
        }

        binding.btnAc.setOnClickListener {
            currentNumber = "0"
            toggleOperation = false
            toggleResult = false
            lastNumber = 0.0
            currentOperation = ""
            binding.displayText.text = currentNumber
        }

        binding.btnDel.setOnClickListener {
            currentNumber = if (currentNumber.length == 1) "0"
            else currentNumber.dropLast(1)
            binding.displayText.text = currentNumber
        }

        binding.btn0.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "0"
            binding.displayText.text = currentNumber
        }

        binding.btn1.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "1"
            binding.displayText.text = currentNumber
        }

        binding.btn2.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "2"
            binding.displayText.text = currentNumber
        }

        binding.btn3.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "3"
            binding.displayText.text = currentNumber
        }

        binding.btn4.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "4"
            binding.displayText.text = currentNumber
        }

        binding.btn5.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "5"
            binding.displayText.text = currentNumber
        }

        binding.btn6.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "6"
            binding.displayText.text = currentNumber
        }

        binding.btn7.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "7"
            binding.displayText.text = currentNumber
        }

        binding.btn8.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "8"
            binding.displayText.text = currentNumber
        }

        binding.btn9.setOnClickListener {
            if (currentNumber == "0" || toggleResult) {
                currentNumber = ""
                toggleResult = false
            }
            currentNumber += "9"
            binding.displayText.text = currentNumber
        }

        binding.btnDot.setOnClickListener {
            currentNumber += "."
            binding.displayText.text = currentNumber
        }

        binding.btnPerse.setOnClickListener {
            currentNumber = (fixSyntax(currentNumber).toDouble() / 100).toString()
            binding.displayText.text = currentNumber
        }

        binding.btnChia.setOnClickListener {
            if (toggleOperation) calculate()
            lastNumber = currentNumber.toDouble()
            currentOperation = "/"
            currentNumber = ""
            toggleOperation = true
        }

        binding.btnMulti.setOnClickListener {
            if (toggleOperation) calculate()
            lastNumber = currentNumber.toDouble()
            currentOperation = "*"
            currentNumber = ""
            toggleOperation = true
        }

        binding.btnMinus.setOnClickListener {
            if (toggleOperation) calculate()
            lastNumber = currentNumber.toDouble()
            currentOperation = "-"
            toggleOperation = true
            currentNumber = ""
        }

        binding.btnPlus.setOnClickListener {
            if (toggleOperation) calculate()
            lastNumber = currentNumber.toDouble()
            currentOperation = "+"
            currentNumber = ""
            toggleOperation = true
        }

        binding.btnEqual.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        var result = fixSyntax(currentNumber).toDouble()

        when (currentOperation) {
            "+" -> result += lastNumber
            "-" -> result = lastNumber - result
            "*" -> result *= lastNumber
            "/" -> result = lastNumber / result
        }

        toggleOperation = false
        toggleResult = true

        binding.displayText.text = formatResult(result)
        currentNumber = formatResult(result)
    }

    private fun formatResult(result: Double): String {
        val df = DecimalFormat("#.####")
        if (result % 1 == 0.0) return result.toInt().toString()
        return df.format(result)
    }

    private fun fixSyntax(currentNumber: String): String {
        if (currentNumber.contains(","))
            return currentNumber.replace(",", ".")
        return currentNumber
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(this, "On Resume Calculator", Toast.LENGTH_SHORT).show()

        val prefs = getSharedPreferences("theme", MODE_PRIVATE)
        val current = prefs.getString("theme", "amber")

        if (current != lastTheme) {
            //Log.d("CurrentTheme", "Current theme: $current")
            //Log.d("CurrentTheme", "Last theme: $lastTheme")
            recreate()
        }
    }
}