package com.example.tutorial3ex2

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorial3ex2.ui.theme.Tutorial3Ex2Theme
import java.io.Serializable


class SecondActivity : ComponentActivity() {

    private lateinit var result: TextView

    fun calculate(data: CustomData): Int{
        return when (data.mode){
            CustomData.Mode.ADD -> data.number1 + data.number2
            CustomData.Mode.MULTIPLY -> data.number1 * data.number2
            CustomData.Mode.SUBTRACT -> data.number1 - data.number2
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val customData = intent.getSerializableExtra("CUSTOM DATA") as? CustomData

        result = findViewById(R.id.textView2)
        if (customData != null) {
            val finalResult = calculate(customData)
            result.text = finalResult.toString()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tutorial3Ex2Theme {
        Greeting("Android")
    }
}