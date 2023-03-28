package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var billAmount by remember { mutableStateOf(1.00) }
    var tipPercent by remember { mutableStateOf(15.0) }
    var tipAmount = calculateTip(billAmount, tipPercent)
    var totalAmount = billAmount + tipAmount

    Column(
        modifier = Modifier.background(color = Color(0xFF36403f)),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            color = Color(0xFFFFFFFF),
            fontSize = 36.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                               .padding(top = 24.dp)
        )
        Spacer(Modifier.height(16.dp))
        RandomBillButton(
            billAmount = billAmount,
            onBillAmountChange = { newBillAmount -> billAmount = newBillAmount }
        )
        Spacer(Modifier.height(24.dp))
        ServiceCostField(billAmount)
        Spacer(Modifier.height(24.dp))
        TipButtonContainer(tipPercent, { newTipPercent -> tipPercent = newTipPercent })
        Spacer(Modifier.height(24.dp))
        TipCalculatorTotals(billAmount, tipAmount, tipPercent, totalAmount)
    }
}

@Composable
fun RandomBillButton(billAmount: Double, onBillAmountChange: (Double) -> Unit) {
    Button(onClick = { onBillAmountChange((1..400).random().toDouble()) },
    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF31ebd8)),
    modifier = Modifier.fillMaxWidth()
                       .padding(horizontal = 24.dp)
                       .height(60.dp),
    )
    {
        Text(text = stringResource(R.string.random_button_text),
             color = Color(0xFF36403f))
    }
}

@Composable
fun ServiceCostField(value: Double) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
                           .padding(start = 84.dp, end = 84.dp)
                           .background(color = Color(0xFFFFFFFF))
                           .height(60.dp),

    ) {
        Text(
            text = "$%.2f".format(value),
            color = Color(0xFF36403f),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TipButtonContainer(tipPercent: Double, onTipPercentChange: (Double) -> Unit,
                       modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(color = Color(0xFF36403f))
            .padding(top = 40.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TipPercentageButton(10.0, tipPercent, onTipPercentChange
                )
                TipPercentageButton(15.0, tipPercent, onTipPercentChange
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TipPercentageButton(18.0, tipPercent, onTipPercentChange
                )
                TipPercentageButton(
                    20.0,
                    tipPercent,
                    onTipPercentChange
                )
            }
        }
    }
}

@Composable
fun TipPercentageButton(percent: Double, tipPercent: Double, onTipPercentChange: (Double) -> Unit, modifier: Modifier = Modifier) {

    val buttonText = when (percent) {
        10.0 -> stringResource(R.string.percent_10)
        15.0 -> stringResource(R.string.percent_15)
        18.0 -> stringResource(R.string.percent_18)
        20.0 -> stringResource(R.string.percent_20)
        else -> stringResource(R.string.percent_15)
    }

    Button(
        onClick = { onTipPercentChange(percent) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFFFFF)),
        modifier = modifier.padding(horizontal = 24.dp)
                           .width(80.dp)
                           .height(60.dp),
    ) {
        Text(text = buttonText,
             color = Color(0xFF36403f))
    }
}

@Composable
fun TipCalculatorTotals(billAmount: Double, tipAmount: Double, tipPercent: Double,
                        totalAmount: Double, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.fillMaxWidth()
                           .padding(top = 40.dp)
    ) {
        Row() {
            Text(
                text = stringResource(R.string.bill_amount),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 15.dp)
            )
            Text(
                text = NumberFormat.getCurrencyInstance().format(billAmount),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 24.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Row() {
            Text(
                text = String.format("(%.0f%%)  ", tipPercent) + stringResource(R.string.tip_amount),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 15.dp)
            )
            Text(
                text = NumberFormat.getCurrencyInstance().format(tipAmount),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 24.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Divider(
            color = Color(0xFF828787),
            thickness = 3.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 24.dp, end = 24.dp)
        )
        Row() {
            Text(
                text = stringResource(R.string.total),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 15.dp)
            )
            Text(
                text = NumberFormat.getCurrencyInstance().format(totalAmount),
                color = Color(0xFF31ebd8),
                textAlign = TextAlign.End,
                fontSize = 22.sp,
            //    fontWeight = FontWeight.Bold,
                modifier = modifier.padding(end = 24.dp)
            )
        }
    }
}

private fun calculateTip(amount: Double, tipPercent: Double): Double {
    val tip = tipPercent / 100 * amount
    return tip
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}