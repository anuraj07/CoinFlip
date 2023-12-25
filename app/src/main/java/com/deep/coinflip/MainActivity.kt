package com.deep.coinflip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deep.coinflip.ui.theme.BlueCustom
import com.deep.coinflip.ui.theme.CoinFlipTheme
import com.deep.coinflip.ui.theme.GoldYellow
import com.deep.coinflip.ui.theme.GrayBackground
import com.deep.coinflip.ui.theme.Yellow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinFlipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var coinState by remember { mutableStateOf(CoinState.Heads) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = 0.5f)
                .background(BlueCustom),
        )
        Card(
            modifier = Modifier
                .fillMaxSize(fraction = 0.8f)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = GrayBackground,
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (coinState) {
                    CoinState.Heads -> HeadCoin()
                    CoinState.Tails -> TailCoin()
                    CoinState.Rotating -> RotatingCoin()
                }
                Row(modifier = Modifier.padding(bottom = 12.dp)) {
                    Button(
                        onClick = {
                                  coinState = CoinState.Rotating
                            GlobalScope.launch {
                                coinState = getCoinValue()
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = BlueCustom
                        )
                    ) {
                        Text(text = "Flip A Coin")

                    }
                }

            }
        }
    }
}

@Composable
fun Coin(coinFace: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = 150.dp)
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(shape = CircleShape)
                .background(Yellow)

        )
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(160.dp)
                .clip(shape = CircleShape)
                .background(GoldYellow)
        )
        Text(
            text = coinFace ?: "",
            color = Yellow,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 32.sp,
                shadow = Shadow(
                    color = Color.Black, offset = Offset(-5.0f, -2.0f), blurRadius = 1f
                )
            )

        )
    }
}

suspend fun getCoinValue(): CoinState {
    delay(3000)
    return if(Random().nextBoolean()) {
        CoinState.Heads
    } else {
        CoinState.Tails
    }
}

@Composable
fun RotatingCoin() {
    Coin(coinFace = null)
}

@Composable
fun HeadCoin() {
    Coin(coinFace = "Heads")
}

@Composable
fun TailCoin() {
    Coin(coinFace = "Tails")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoinFlipTheme {
        MainScreen()
    }
}