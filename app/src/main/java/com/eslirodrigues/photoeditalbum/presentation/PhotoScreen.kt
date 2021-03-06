package com.eslirodrigues.photoeditalbum.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eslirodrigues.photoeditalbum.BuildConfig
import com.eslirodrigues.photoeditalbum.R
import com.eslirodrigues.photoeditalbum.presentation.viewmodel.PhotoViewModel
import com.eslirodrigues.photoeditalbum.ui.theme.DarkGray
import com.eslirodrigues.photoeditalbum.ui.theme.LightGray
import com.google.accompanist.glide.rememberGlidePainter
import java.io.File

@Composable
fun PhotoScreen(
    navController: NavController,
    viewModel: PhotoViewModel = hiltViewModel(),
    photoUri: String,
    photoName: String
) {
    var showMenu by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val inputRenameDialog = remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        contentColor = Color.White,
        topBar = {
            TopAppBar(
                backgroundColor = DarkGray,
                title = {
                    Text("")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.share_image),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showRenameDialog = !showRenameDialog
                            }
                        ) {
                            Text(text = stringResource(id = R.string.rename))
                            if (showRenameDialog) {
                                AlertDialog(
                                    backgroundColor = DarkGray,
                                    onDismissRequest = { showRenameDialog = false },
                                    text = {
                                        TextField(
                                            value = inputRenameDialog.value,
                                            onValueChange = {
                                                inputRenameDialog.value = it
                                            },
                                            placeholder = {
                                                Text(
                                                    text = photoName,
                                                    modifier = Modifier
                                                        .width(250.dp),
                                                    textAlign = TextAlign.Center,
                                                )
                                            },
                                            shape = RoundedCornerShape(14.dp),
                                            singleLine = true,
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.White,
                                                placeholderColor = Color.White,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                backgroundColor = LightGray
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    showRenameDialog = false
                                                }
                                            )
                                        )
                                    },
                                    confirmButton = {
                                        TextButton(
                                            modifier = Modifier
                                                .padding(
                                                    start = 5.dp,
                                                    end = 15.dp,
                                                    bottom = 5.dp
                                                ),
                                            onClick = {
                                                showRenameDialog = false
                                            }
                                        ) {
                                            Text(
                                                color = Color.White,
                                                text = stringResource(id = R.string.save).toUpperCase(Locale.current)
                                            )
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(
                                            modifier = Modifier
                                                .padding(
                                                    start = 5.dp,
                                                    end = 15.dp,
                                                    bottom = 5.dp
                                                ),
                                            onClick = {
                                                showRenameDialog = false
                                            }
                                        ) {
                                            Text(
                                                color = Color.White,
                                                text = stringResource(id = R.string.cancel).toUpperCase(Locale.current)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        stringResource(id = R.string.more),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                showMenu = !showMenu
                            }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(DarkGray)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = rememberGlidePainter(Uri.parse(photoUri)),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 15.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    stringResource(id = R.string.share_image),
                    modifier = Modifier.clickable {
                        val shareUri: Uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", File(photoUri.toUri().path))
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_STREAM, shareUri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            type = "image/jpg"
                        }
                        context.startActivity(intent)
                    }
                )
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    stringResource(id = R.string.edit_image),
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    stringResource(id = R.string.delete_image),
                    modifier = Modifier.clickable {
                        showDeleteDialog = true
                    }
                )
                if(showDeleteDialog) {
                    AlertDialog(
                        backgroundColor = DarkGray,
                        onDismissRequest = { showDeleteDialog = false},
                        text = {
                            Text(stringResource(id = R.string.delete_dialog_msg))
                        },
                        confirmButton = {
                            TextButton(
                                modifier = Modifier
                                    .padding(
                                        start = 5.dp,
                                        end = 15.dp,
                                        bottom = 5.dp
                                    ),
                                onClick = {
                                    viewModel.deletePhoto(photoUri)
                                    showDeleteDialog = false
                                    navController.navigate(ScreenNav.PhotoList.route)
                                }
                            ) {
                                Text(
                                    color = Color.White,
                                    text = stringResource(id = R.string.delete).toUpperCase(Locale.current)
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(
                                modifier = Modifier
                                    .padding(
                                        start = 5.dp,
                                        end = 15.dp,
                                        bottom = 5.dp
                                    ),
                                onClick = {
                                    showDeleteDialog = false
                                }
                            ) {
                                Text(
                                    color = Color.White,
                                    text = stringResource(id = R.string.cancel).toUpperCase(Locale.current)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}