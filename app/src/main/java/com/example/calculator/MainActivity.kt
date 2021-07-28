package com.example.calculator

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sign = ""
    var hasDot = false
    var clickedFirst = true


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val input = binding.tvInput.text.toString()
        Log.d("Main", input)

        binding.btnZero.setOnClickListener {
            if (binding.tvInput.text.toString() != "0") {
                Log.d("Main", binding.tvInput.text.toString())
                evaluateExpression("0", true)
            }
        }

        binding.btnOne.setOnClickListener {
            clickedFirst()
            evaluateExpression("1", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnTwo.setOnClickListener {
            clickedFirst()
            evaluateExpression("2", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnThree.setOnClickListener {
            clickedFirst()
            evaluateExpression("3", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnFour.setOnClickListener {
            clickedFirst()
            evaluateExpression("4", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnFive.setOnClickListener {
            clickedFirst()
            evaluateExpression("5", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnSix.setOnClickListener {
            clickedFirst()
            evaluateExpression("6", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnSeven.setOnClickListener {
            clickedFirst()
            evaluateExpression("7", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnEight.setOnClickListener {
            clickedFirst()
            evaluateExpression("8", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnNine.setOnClickListener {
            clickedFirst()
            evaluateExpression("9", true)
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
        }

        binding.btnDecimal.setOnClickListener {
            if(!hasDot)
                if(binding.tvInput.text.toString() == "")
                    binding.tvInput.text = "0."
                else
                    binding.tvInput.text = binding.tvInput.text.toString() + "."

            hasDot = true
        }

        binding.btnPlus.setOnClickListener {
            sign = "+"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnMinus.setOnClickListener {
            sign = "-"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnMultiply.setOnClickListener {
            sign = "×"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnDivide.setOnClickListener {
            sign = "/"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = "= $value"
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnEqual.setOnClickListener {
            val value = calculate(binding.tvInput.text.toString())
            if (value.length > 10)
                binding.tvResult.text = "= ${value.substring(1..9)}"
            else
                binding.tvResult.text = "= $value"
            binding.tvResult.textSize = 36F
            binding.tvResult.typeface = Typeface.DEFAULT
            binding.tvInput.textSize = 28F

        }

        binding.btnBackspace.setOnClickListener {
            if(binding.tvInput.text.toString() == "")
                binding.tvInput.text = null
            else
            {
                var str = binding.tvInput.text.toString()
                val len = str.length
                if (str[len - 1] == '.') {
                    hasDot = false
                    binding.tvInput.text = str.subSequence(0, len - 1)

                }
                else {
                    str = str.subSequence(0, len - 1).toString()
                    binding.tvInput.text = str
                    var value = ""
                    if (str.isEmpty())
                    {
                        binding.tvInput.text = ""
                        binding.tvResult.text = "0"
                    }
                    else if (str[str.length - 1] == '+' || str[str.length - 1] == '-' ||
                                            str[str.length - 1] == '/' || str[str.length - 1] == '×')
                        value = calculate(str.subSequence(0, len - 2).toString())
                    else
                        value = calculate(str)
                    if (value != "")
                        binding.tvResult.text = "= $value"
                }
            }
        }


        binding.btnClear.setOnClickListener {
            clickedFirst = true
            binding.tvInput.text = "0"
            binding.tvResult.text = ""
            hasDot = false
            binding.tvInput.textSize = 36F
            binding.tvResult.textSize = 28F
            binding.tvResult.typeface = Typeface.create("alpha",Typeface.NORMAL)

        }

    }
    private fun clickedFirst() {
        if (clickedFirst)
        {
            clickedFirst = false
            binding.tvInput.text = ""
        }
    }

    private fun calculate(tokens: String):String {

        val values = Stack<Double>()
        val operators = Stack<Char>()
        var i = 0
        while(i in tokens.indices) {
            var c = 0
            if (tokens[i].isDigit() || tokens[i] == '.') {
                var value = 0.0
                var flag = false
                while (i < tokens.length && (tokens[i].isDigit() || tokens[i] == '.')) {
                    if (tokens[i] == '.') {
                        flag = true
                        Log.d("Main", tokens)
                        i++
                        continue
                    }
                    if (flag) {
                        c++
                        var t = c
                        var s = 1
                        while(t > 0)
                        {
                            s *= 10
                            t--
                        }
                        value = value * s + tokens[i].digitToInt()
                        value /= s
                    }
                    else
                        value = value * 10 + tokens[i].digitToInt()
                    i++
                }
                values.push(value)
                i--
            } else {
                while (!operators.isEmpty() && precedence(operators.peek()!!) >= precedence(tokens[i])) {

                    val v2 = values.peek()!!
                    values.pop()
                    val v1 = values.peek()!!
                    values.pop()
                    val op = operators.peek()!!
                    operators.pop()
                    values.push(applyOp(v1, v2, op))
                }
                operators.push(tokens[i])

            }
            i++
        }

        while (!operators.isEmpty()) {
            val v2 = values.peek()!!
            values.pop()
            val v1 = values.peek()!!
            values.pop()
            val op = operators.peek()!!
            operators.pop()
            values.push(applyOp(v1, v2, op))
        }
        val res = values.peek()!!
        values.pop()
        return res.toString()
    }

    private fun applyOp(a: Double, b: Double, op: Char): Double {
        return when (op) {
            '+' -> {
                a + b
            }
            '-' -> {
                a - b
            }
            '×' -> {
                a * b
            }
            '/' -> {
                if (b == 0.0)
                    0.0
                else
                    a / b
            }
            else -> 0.0
        }
    }

    private fun precedence(op: Char): Int {
        if (op == '+' || op == '-')
            return 1
        if (op == '×' || op == '/')
            return 2
        return 0
    }

    private fun evaluateExpression(string: String, clear: Boolean) {
        if (clear) {

            binding.tvInput.append(string)

        }
    }

}



