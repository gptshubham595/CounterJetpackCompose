package com.oops.counterjc

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oops.counterjc.ui.theme.CounterJCTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate ${savedInstanceState?.keySet()}")
        savedInstanceState?.let {
            for (key: String in it.keySet()) {
                Log.d("MainActivity", "onCreate ${key} ${it.get(key)}")
            }
        }
        enableEdgeToEdge()
        setContent {
            CounterJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("MainActivity", "onSaveInstanceState ${outState.keySet()}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MainActivity", "onRestoreInstanceState ${savedInstanceState.keySet()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
}

@Composable
fun LoggingButton(label: String, onClick: () -> Unit) {
    Log.d("MainActivity", "Button '$label' recomposed")
    Button(onClick = onClick) {
        Text(text = label)
    }
}


@Composable
fun Greeting(viewModel: MainViewModel, modifier: Modifier = Modifier) {
//    Log.d("MainActivity", "Greeting ${counter.hashCode()}")

//    Rebugger(
//        trackMap = mapOf(
//            "counter" to counter
//        )
//    )

    viewModel.counter.observeAsState()?.value.let {
        Log.d("MainActivity", "Greeting ${it}")
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
//        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val decrement = remember { { viewModel.decrement.invoke() } }
            LoggingButton(
                "Decrement ",/*${viewModel.counter.value}"*/
                onClick = { decrement.invoke() },
            )
//            Button(
//            onClick = { viewModel.decrement.invoke() },
//            modifier = modifier.onPlaced { }
//        ) {
//            Text("Decrement")
//        }

//            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "${viewModel.counter.value}",
                modifier = modifier
            )

//            Spacer(modifier = Modifier.width(10.dp))

            val increment = remember { { viewModel.increment.invoke() } }
            LoggingButton(
                "Increment",
                onClick = { increment.invoke() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowFlagPreview() {
    CounterJCTheme {
        ShowFlag()
    }
}

@Composable
fun ShowFlag(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / density.density
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / density.density

    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(0.2f)
                .background(color = androidx.compose.ui.graphics.Color.Red)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = androidx.compose.ui.graphics.Color.Blue),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(color = androidx.compose.ui.graphics.Color.Green),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = androidx.compose.ui.graphics.Color.Magenta),
                    ) {
                        val imageResId = R.drawable.ic_launcher_background
                        val imagePainter = painterResource(id = imageResId)
                        Image(
                            painter = imagePainter,
                            contentDescription = null,
                            modifier = modifier
                                .padding(bottom = 10.dp)
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(color = androidx.compose.ui.graphics.Color.Green)
                ) {
                    Text("hi2")
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(color = androidx.compose.ui.graphics.Color.Green)
                ) {
                    Text("hi2")
                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxHeight()
                .width((screenHeight * 0.8f).dp)
                .background(color = androidx.compose.ui.graphics.Color.Yellow)
        ) {
            Text("Hi2")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CounterJCTheme {
//        Greeting(MainViewModel())
//    }
//}