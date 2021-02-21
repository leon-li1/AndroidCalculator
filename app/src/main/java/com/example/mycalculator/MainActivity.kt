package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycalculator.databinding.ActivityMainBinding
import android.view.View
import android.widget.Button
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastNum = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNum = true
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNum = false
        lastDot = false
    }

    fun onDecimal(view: View) {
        if (lastNum && !lastDot) {
            binding.tvInput.append(".")
            lastNum = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNum) {
            var tvVal = binding.tvInput.text.toString()
            var prefix = ""
            val match = Regex("[-+*/]").find(tvVal.substring(1))?.value ?: return

            try {
                if (tvVal.startsWith("-")) {
                    prefix = "-"
                    tvVal = tvVal.substring(1)
                }

                var (a, b) = tvVal.split(match)
                if (prefix.isNotEmpty()) a = prefix + a
                var ans = when (match) {
                    "+" -> (a.toDouble() + b.toDouble()).toString()
                    "-" -> (a.toDouble() - b.toDouble()).toString()
                    "*" -> (a.toDouble() * b.toDouble()).toString()
                    "/" -> (a.toDouble() / b.toDouble()).toString()
                    else -> "Won't happen"
                }
                if (ans.endsWith(".0")) ans = ans.substring(0, ans.length - 2)
                binding.tvInput.text = ans

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view: View) {
        if (lastNum && !isOpInInput(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNum = false
            lastDot = true
        }
    }

    private fun isOpInInput(s: String): Boolean {
        val regex = Regex("[+\\-*/]")
        return s.substring(1).contains(regex)
    }
}