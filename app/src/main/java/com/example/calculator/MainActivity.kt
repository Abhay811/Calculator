package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var num1: Double = 0.0
    var num2: Double = 0.0
    private var sign = ""
    var val1 = ""
    var val2 = ""
    var hasDot = false
    var stack = Stack<Double>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val input = binding.tvInput.text.toString()
        Log.d("Main", input)

        binding.btnZero.setOnClickListener {
            evaluateExpression("0", true)
        }

        binding.btnOne.setOnClickListener {
            evaluateExpression("1", true)
        }

        binding.btnTwo.setOnClickListener {
            evaluateExpression("2", true)
        }

        binding.btnThree.setOnClickListener {
            evaluateExpression("3", true)
        }

        binding.btnFour.setOnClickListener {
            evaluateExpression("4", true)
        }

        binding.btnFive.setOnClickListener {
            evaluateExpression("5", true)
        }

        binding.btnSix.setOnClickListener {
            evaluateExpression("6", true)
        }

        binding.btnSeven.setOnClickListener {
            evaluateExpression("7", true)
        }

        binding.btnEight.setOnClickListener {
            evaluateExpression("8", true)
        }

        binding.btnNine.setOnClickListener {
            evaluateExpression("9", true)
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
            binding.tvResult.text = value.toString()
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnMinus.setOnClickListener {
            sign = "-"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = value.toString()
            evaluateExpression(sign, true)
            Log.d("Main", sign)
            hasDot = false
        }

        binding.btnMultiply.setOnClickListener {
            sign = "×"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = value.toString()
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnDivide.setOnClickListener {
            sign = "/"
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = value.toString()
            evaluateExpression(sign, true)
            hasDot = false
        }

        binding.btnEqual.setOnClickListener {
            val value = calculate(binding.tvInput.text.toString())
            binding.tvResult.text = value.toString()
        }

        binding.btnBackspace.setOnClickListener {
            if(binding.tvInput.text.toString() == "")
                binding.tvInput.text = null
            else
            {
                val str = binding.tvInput.text.toString()
                val len = str.length
                if (str[len - 1] == '.') {
                    hasDot = false
                    binding.tvInput.text = str.subSequence(0, len - 1)

                }
                else
                    binding.tvInput.text = str.subSequence(0, len - 1)
            }
        }


        binding.btnClear.setOnClickListener {
            binding.tvInput.text = ""
            binding.tvResult.text = ""
            hasDot = false
//            val1 = 0
//            val2 = 0
        }

//        binding.btnEqual.setOnClickListener {
//            val value = calculate(binding.tvInput.text.toString())
//            binding.tvResult.text = value.toString()
//        }

//        calculate("10 + 2 * 6")
    }

    private fun calculate(tokens: String):Double {

        val values = Stack<Double>()
        val operators = Stack<Char>()
        var i = 0
        while(i in tokens.indices) {
            if (tokens[i].isDigit()) {
                var value = 0
                while (i < tokens.length && tokens[i].isDigit()) {
                    value = value * 10 + tokens[i].digitToInt()
                    i++
                }
                values.push(value.toDouble())
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
        Log.d("Main", values.toString())
        Log.d("Main", operators.toString())

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
        return res
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



