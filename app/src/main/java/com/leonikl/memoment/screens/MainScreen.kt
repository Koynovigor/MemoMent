package com.leonikl.memoment.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.leonikl.memoment.R
import com.leonikl.memoment.database.Page
import com.leonikl.memoment.ui.theme.Blue
import com.leonikl.memoment.ui.theme.Blue1
import com.leonikl.memoment.ui.theme.Blue2
import com.leonikl.memoment.ui.theme.Gray
import com.leonikl.memoment.view.MainViewModel
import com.leonikl.memoment.view.MyViewModel
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    model: MyViewModel,
    onClick: (Page) -> Unit,
) {
    val myList by viewModel.allPages.observeAsState(listOf())

    Scaffold(
        containerColor = Gray,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .height((LocalConfiguration.current.screenHeightDp / 15).dp),
                title = {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Blue
                        )
                    }

                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.insertPage(
                                Page()
                            )
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "add",
                            tint = Blue
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            itemsIndexed(myList) { _, item ->
                if (!item.delete){
                    CardMemo(
                        page = item,
                        viewModel = viewModel,
                        onClick = { task -> onClick(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardMemo(
    page: Page,
    viewModel: MainViewModel,
    onClick: (Page) -> Unit
){
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    var image by remember {
        mutableStateOf(page.image)
    }
    if (image == null){
        image = getBitmapFromImage(
            context = context,
            drawable = R.drawable.add
        ).toByteArray()
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
        bitmap.value?.let { btm ->
            image =  btm.toByteArray()
            page.image = image
            viewModel.updatePage(page)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(
                top = 5.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 0.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 45.dp,
                    topEnd = 5.dp,
                    bottomStart = 45.dp,
                    bottomEnd = 45.dp
                )
            )
            .shadow(elevation = 3.dp)
            .background(Color.White),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .clip(
                    shape = RoundedCornerShape(45.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .weight(3f)
                        .width((LocalConfiguration.current.screenWidthDp / 4).dp)
                        .padding(top = 3.dp)
                ) {
                    Image(
                        bitmap = image!!.toBitmap().asImageBitmap(),
                        contentDescription = "add",
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = page.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 3.dp),
                    color = Blue
                )
            }
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Blue2,
                                Blue1
                            )
                        )
                    )
                    .weight(1f)
                    .heightIn(min = 100.dp)
                    .clickable { onClick(page) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = page.memo,
                    color = Color.White
                )
            }
        }
    }
}

fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {

    val db = ContextCompat.getDrawable(context, drawable)

    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bit)

    db.setBounds(0, 0, canvas.width, canvas.height)

    db.draw(canvas)

    return bit
}
fun Bitmap.toByteArray():ByteArray{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,100,this)
        return toByteArray()
    }
}
fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,size)
}
