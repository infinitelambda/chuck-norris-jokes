package com.infinitelambda.chuck.android.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.infinitelambda.chuck.android.R
import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory

@Composable
fun JokeScreen(
    viewModelFactory: ViewModelFactory,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: JokeViewModel = viewModel(factory = viewModelFactory)
) {
    OnCreated(lifecycleOwner = lifecycleOwner) {
        viewModel.onViewCreated()
    }

    JokeScreenContent(viewModel = viewModel)
}

@Composable
private fun OnCreated(lifecycleOwner: LifecycleOwner, action: () -> Unit) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                action()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun JokeScreenContent(
    viewModel: JokeViewModel
) {
    val uiState = viewModel.uiState

    val context = LocalContext.current
    val shareSheetTitle = stringResource(id = R.string.joke_share_sheet_title)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onRefreshJokeClicked() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.joke_refresh_button_content_description)
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f, true))
                IconButton(onClick = {
                    context.startActivity(
                        Intent.createChooser(
                            Intent()
                                .setAction(Intent.ACTION_SEND)
                                .setType("text/plain")
                                .putExtra(Intent.EXTRA_TEXT, uiState.joke?.value ?: ""),
                            shareSheetTitle
                        )
                    )
                }) {
                    Icon(Icons.Filled.Share, contentDescription = stringResource(id = R.string.joke_share_button_content_description))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            JokeCategoryDropdownView(
                modifier = Modifier.fillMaxWidth(),
                selectedValue = uiState.jokeCategory,
                values = uiState.jokeCategories,
                onValueChanged = { viewModel.onJokeCategoryChanged(it) }
            )

            uiState.joke?.let {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
                JokeCard(joke = it)
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun JokeCategoryDropdownView(
    modifier: Modifier = Modifier,
    selectedValue: JokeCategory?,
    values: List<JokeCategory>,
    onValueChanged: (JokeCategory?) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = stringResource(id = selectedValue.mapToStringResource()),
            onValueChange = { },
            label = { Text(stringResource(id = R.string.joke_category_dropdown_label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            JokeCategoryDropdownItem(item = null, onClick = {
                onValueChanged(null)
                expanded = false
            })

            values.forEach { selectionOption ->
                JokeCategoryDropdownItem(
                    item = selectionOption,
                    onClick = {
                        onValueChanged(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun JokeCategoryDropdownItem(
    item: JokeCategory?,
    onClick: (JokeCategory?) -> Unit
) {
    DropdownMenuItem(
        onClick = {
            onClick(item)
        }
    ) {
        Text(text = stringResource(id = item.mapToStringResource()))
    }
}

@Composable
private fun JokeCard(
    joke: Joke
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = joke.value,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }
    }
}