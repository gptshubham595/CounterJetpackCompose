package com.oops.counterjc

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
                        context = LocalContext.current,
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
fun Greeting(context: Context, viewModel: MainViewModel, modifier: Modifier = Modifier) {
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
//                onClick = { viewModel.decrement.invoke() },
//            ) {
//                Text("Decrement")
//            }


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
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            var text = remember { mutableStateOf("") }
            val isValid = text.value.length >= 5

            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                trailingIcon = {
                    if (text.value.isNotEmpty()) {
                        IconButton(onClick = { text.value = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear Icon")
                        }
                    }
                },
                modifier = modifier,
                isError = !isValid
            )

            Button(
                onClick = {
                    Toast.makeText(context, "Button clicked ${text.value}", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.counter.value = text.value.length
                }, modifier = modifier.background(Color.Blue),
                shape = RoundedCornerShape(10.dp) // CircleShape
            ) {
                Text("Submit")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row() {
            //ShowFlag()
            ConstraintLayoutExample(viewModel)

        }
    }
}


@Composable
fun ConstraintLayoutExample(viewModel: MainViewModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        // Create references for the components
        val (decrementButton, incrementButton, counterText) = createRefs()

        // Decrement Button
        Button(
            onClick = { viewModel.decrement.invoke() },
            modifier = Modifier.constrainAs(decrementButton) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        ) {
            Text("Decrement")
        }

        // Counter Text
        Text(
            text = "${viewModel.counter.value}",
            modifier = Modifier.constrainAs(counterText) {
                top.linkTo(decrementButton.top)
                start.linkTo(decrementButton.end, margin = 16.dp)
                bottom.linkTo(decrementButton.bottom)
            }
        )

        // Increment Button
        Button(
            onClick = { viewModel.increment.invoke() },
            modifier = Modifier.constrainAs(incrementButton) {
                top.linkTo(decrementButton.top)
                start.linkTo(counterText.end, margin = 16.dp)
            }
        ) {
            Text("Increment")
        }
    }
}


//@Preview(showBackground = true)
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
                .background(color = Color.Red)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.Blue),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(color = Color.Green),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = Color.Magenta),
                    ) {
                        val imageResId = R.drawable.baseline_4g_mobiledata_24
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
                        .background(color = Color.Green)
                ) {
                    Text("hi2")
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(color = Color.Green)
                ) {
                    Text("hi2")
                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxHeight()
                .width((screenHeight * 0.8f).dp)
                .background(color = Color.Yellow)
        ) {
            Text("Hi2")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CounterJCTheme {
        Greeting(LocalContext.current, MainViewModel())
    }
}