package coeus.argus.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var numNumber: EditText
    private lateinit var result: TextView

    private lateinit var clear: Button
    private lateinit var percentage: Button
    private lateinit var division: Button
    private lateinit var multiply: Button
    private lateinit var addition: Button
    private lateinit var subst: Button
    private lateinit var equalTo: Button
    private lateinit var dot: Button

    private lateinit var zero: Button
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button
    private lateinit var negative: Button

    // Variables to hold the operands and type of calculation
    private var operand: Double? = null
    private var numOperator: String = "="
    private var value: String = ""

    //
    private val STATE_PENDING_OPERATION = "PendingOperation"
    private  val STATE_OPERAND1 = "operand"
    private  val STATE_OPERAND1_STORED = "Operand1_Stored"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numNumber = findViewById(R.id.operation)



        clear = findViewById(R.id.buttonC)
        percentage = findViewById(R.id.buttonP)
        division = findViewById(R.id.buttonD)
        multiply = findViewById(R.id.buttonX)
        addition = findViewById(R.id.buttonA)
        subst = findViewById(R.id.buttonS)
        equalTo = findViewById(R.id.buttonE)
        dot = findViewById(R.id.buttonDot)

        result = findViewById(R.id.result)


        //Number button
        zero = findViewById(R.id.button0)
        one = findViewById(R.id.button1)
        two = findViewById(R.id.button2)
        three = findViewById(R.id.button3)
        four = findViewById(R.id.button4)
        five = findViewById(R.id.button5)
        six = findViewById(R.id.button6)
        seven = findViewById(R.id.button7)
        eight = findViewById(R.id.button8)
        nine = findViewById(R.id.button9)
        negative = findViewById(R.id.buttonMD)
        val listener = View.OnClickListener { v ->
            val b = v as Button
            numNumber.append(b.text)

        }

        zero.setOnClickListener(listener)
        one.setOnClickListener(listener)
        two.setOnClickListener(listener)
        three.setOnClickListener(listener)
        four.setOnClickListener(listener)
        five.setOnClickListener(listener)
        six.setOnClickListener(listener)
        seven.setOnClickListener(listener)
        eight.setOnClickListener(listener)
        nine.setOnClickListener(listener)
        dot.setOnClickListener(listener)
        negative.setOnClickListener(listener)


        val oplistener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            value = numNumber.text.toString()

            if (value == "."){
                performClear()
            }

            if (value.isNotEmpty()) {
                numNumber.text.clear()
                result.text = value
                performOperation(value.toDouble(), op)
            }
            numOperator = op

        }

        division.setOnClickListener(oplistener)
        multiply.setOnClickListener(oplistener)
        addition.setOnClickListener(oplistener)
        subst.setOnClickListener(oplistener)
        equalTo.setOnClickListener(oplistener)
        percentage.setOnClickListener(oplistener)
        equalTo.setOnClickListener(oplistener)

        val opplistener = View.OnClickListener { v ->
            val opp = (v as Button).text.toString()
            if(opp.isNotEmpty()){
                performClear()
            }
        }
        clear.setOnClickListener(opplistener)


    }

    private fun performOperation(value: Double, operation: String) {
        if (operand == null) {
            operand = value
        } else {
            if (numOperator == "=") {
                numOperator = operation
            }
            when (numOperator) {
                "=" -> operand = value

                "/" -> operand = if (value == 0.0) {
                    Double.NaN   // handle attempt to divide by zero
                } else {
                    operand!! / value
                }
                "-" -> operand = operand!! - value
                "+" -> operand = operand!! + value
                "*" -> operand = operand!! * value
                "%" -> operand = operand!! % value
            }
        }
        result.text= operand.toString()

    }

    private fun performClear(){
        value = ""
        operand = null
        numNumber.text.clear()
        result.text = ""
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (operand != null) {
            val userText = result.text
            outState.putDouble(STATE_OPERAND1, operand!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
            outState.putString("savedText", userText.toString())
        }
        outState.putString(STATE_PENDING_OPERATION, numOperator)


    }



    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }
        val userText = savedInstanceState.getString("savedText")
        numOperator = savedInstanceState.getString(STATE_PENDING_OPERATION, "=")
        result.text = userText

    }
}







