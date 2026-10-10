package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.data.local.ChatMessageEntity
import com.example.ui.chat.SpeechToTextManager
import com.example.ui.theme.JannatGold
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowSpectrumColors
import com.example.ui.theme.RainbowViolet
import com.example.ui.viewmodel.JannatViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatScreen(
    viewModel: JannatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activeChannelId by viewModel.activeChannelId.collectAsState()
    val messages by viewModel.activeChannelMessages.collectAsState()
    val isAiTyping by viewModel.isAiTyping.collectAsState()
    val pendingImageUri by viewModel.pendingImageUri.collectAsState()
    val isRecordingVoice by viewModel.isRecordingVoice.collectAsState()
    val recordingDuration by viewModel.voiceRecordingDurationSeconds.collectAsState()
    val userGovernorate by viewModel.userGovernorate.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var isListeningSpeech by remember { mutableStateOf(false) }
    var showGovernorateDialog by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    // Scroll to bottom when new messages arrive
    LaunchedEffect(messages.size, isAiTyping) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Photo picker launcher (Google Play Policy zero-permission contract)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setPendingImageUri(uri)
        }
    }

    // Speech to text manager
    val speechManager = remember {
        SpeechToTextManager(
            context = context,
            onResult = { recognizedText ->
                inputText = if (inputText.isBlank()) recognizedText else "$inputText $recognizedText"
            },
            onError = { errMsg ->
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
            },
            onListeningStateChanged = { listening ->
                isListeningSpeech = listening
            }
        )
    }

    // Microphone audio permission launcher
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Start recording voice note
            viewModel.startVoiceRecording()
        } else {
            Toast.makeText(context, "يلزم السماح بالميكروفون لتسجيل الصوت", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("chat_screen")
            .background(Color(0xFFF8FAFC))
    ) {
        // Channel Tabs (اسأل جنات الذكية، ملتقى الطلاب، غرف المواد)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.availableChannels) { channel ->
                val isSelected = channel.id == activeChannelId
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) {
                        if (channel.isAi) RainbowIndigo else RainbowCyan
                    } else {
                        Color(0xFFF1F5F9)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.selectChannel(channel.id) }
                        .testTag("chat_channel_${channel.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = channel.iconEmoji, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = channel.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else Color(0xFF334155)
                            )
                        }
                    }
                }
            }
        }

        // Governorate Indicator & Quick Switch Strip (إظهار وتغيير محافظة الطالب: البحر الأحمر وباقي المحافظات)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFF1F5F9),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showGovernorateDialog = true }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "📍 محافظتك في المحادثة:", fontSize = 11.5.sp, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RainbowIndigo.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = userGovernorate,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = RainbowIndigo,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "تغيير المحافظة ▾",
                    fontSize = 11.sp,
                    color = RainbowCyan,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Governorate Selection Dialog
        if (showGovernorateDialog) {
            AlertDialog(
                onDismissRequest = { showGovernorateDialog = false },
                title = {
                    Text(
                        text = "اختر محافظتك 🇪🇬",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = RainbowIndigo
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Text(
                            text = "اختر محافظتك لتظهر بجانب اسمك في غرف محادثات طلاب أولى إعدادي:",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(viewModel.allGovernorates) { gov ->
                                val isChosen = gov == userGovernorate
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isChosen) RainbowCyan.copy(alpha = 0.15f) else Color(0xFFF8FAFC),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isChosen) RainbowCyan else Color(0xFFE2E8F0)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setUserGovernorate(gov)
                                            showGovernorateDialog = false
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = gov,
                                            fontSize = 13.sp,
                                            fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isChosen) RainbowCyan else Color(0xFF1E293B)
                                        )
                                        if (isChosen) {
                                            Text(text = "✔", color = RainbowCyan, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showGovernorateDialog = false }) {
                        Text("إغلاق", color = RainbowCyan, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }

        // Active Channel Header Banner
        val currentChannel = viewModel.availableChannels.firstOrNull { it.id == activeChannelId }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (currentChannel?.isAi == true) RainbowIndigo.copy(alpha = 0.15f)
                                else RainbowCyan.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentChannel?.iconEmoji ?: "💬", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = currentChannel?.title ?: "المحادثة",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = currentChannel?.subtitle ?: "",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFECFDF5)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(RainbowGreen)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "متصل الآن 🟢",
                            fontSize = 10.5.sp,
                            color = RainbowGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Suggested Prompts Chips if in "اسأل جنات"
        if (activeChannelId == "jannat_ai") {
            val suggestions = listOf(
                "قانون الكثافة ومسائلها 🔬",
                "همزة القطع وألف الوصل 📖",
                "حالات تطابق المثلثين 📐",
                "عصر بناة الأهرام 🏛️",
                "Unit 1 New Hello 🇬🇧"
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(suggestions) { suggestion ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                inputText = suggestion
                                viewModel.sendChatMessage(suggestion)
                            }
                    ) {
                        Text(
                            text = suggestion,
                            fontSize = 11.sp,
                            color = RainbowIndigo,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { message ->
                ChatMessageBubble(message = message)
            }

            // AI Typing Indicator
            if (isAiTyping) {
                item {
                    AiTypingIndicator()
                }
            }
        }

        // Pending Image Preview Chip
        if (pendingImageUri != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = pendingImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "تم إرفاق صورة المسألة 📸",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = RainbowIndigo
                            )
                            Text(
                                text = "ستقوم جنات بتحليلها وشرحها خطوة بخطوة",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    IconButton(onClick = { viewModel.setPendingImageUri(null) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Bottom Input Area
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                if (isRecordingVoice) {
                    // Voice Recording Banner
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFEF2F2), RoundedCornerShape(14.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(RainbowRed)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "جارٍ تسجيل رسالة صوتية: 00:${String.format("%02d", recordingDuration)}",
                                color = RainbowRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.cancelVoiceRecording() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "إلغاء",
                                    tint = Color.Gray
                                )
                            }
                            IconButton(onClick = {
                                viewModel.stopVoiceRecording { duration ->
                                    viewModel.sendChatMessage(
                                        text = "رسالة صوتية 🎙️",
                                        isVoice = true,
                                        voiceDurationSeconds = duration
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "إرسال الصوت",
                                    tint = RainbowIndigo
                                )
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Media / Photo Picker Button
                        IconButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.testTag("attach_media_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = "رفع وسائط وصورة",
                                tint = RainbowIndigo,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Microphone Button
                        val infiniteTransition = rememberInfiniteTransition()
                        val micScale by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = if (isListeningSpeech) 1.25f else 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(600),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "mic_scale"
                        )

                        IconButton(
                            onClick = {
                                val hasAudioPermission = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED

                                if (hasAudioPermission) {
                                    if (isListeningSpeech) {
                                        speechManager.stopListening()
                                    } else {
                                        speechManager.startListening()
                                    }
                                } else {
                                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            },
                            modifier = Modifier
                                .scale(micScale)
                                .testTag("mic_btn")
                        ) {
                            Icon(
                                imageVector = if (isListeningSpeech) Icons.Default.MicOff else Icons.Default.Mic,
                                contentDescription = "تسجيل صوتي أو إملاء",
                                tint = if (isListeningSpeech) RainbowRed else RainbowGreen,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Text Field
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chat_input_field"),
                            placeholder = {
                                Text(
                                    text = if (isListeningSpeech) "تحدث الآن بصوتك..." else "اكتب سؤالك أو استفسارك هنا...",
                                    fontSize = 13.sp
                                )
                            },
                            maxLines = 3,
                            shape = RoundedCornerShape(18.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RainbowIndigo,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color(0xFFF8FAFC),
                                unfocusedContainerColor = Color(0xFFF8FAFC)
                            )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Send Button
                        Surface(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (inputText.isNotBlank() || pendingImageUri != null) {
                                        val textToSend = inputText
                                        inputText = ""
                                        viewModel.sendChatMessage(text = textToSend)
                                    }
                                }
                                .testTag("send_chat_btn"),
                            shape = CircleShape,
                            color = RainbowIndigo
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "إرسال",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessageEntity) {
    val isUser = message.isUser
    val isAi = message.isAi
    val dateFormat = SimpleDateFormat("h:mm a", Locale("ar"))
    val timeFormatted = dateFormat.format(Date(message.timestamp))

    var isVoicePlaying by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.Start else Arrangement.End
    ) {
        if (!isUser) {
            // Sender Avatar
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(if (isAi) RainbowIndigo.copy(alpha = 0.15f) else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = message.senderAvatarEmoji, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.weight(1f, fill = false),
            horizontalAlignment = if (isUser) Alignment.Start else Alignment.End
        ) {
            // Sender Name & Tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (isUser) Arrangement.Start else Arrangement.End
            ) {
                Text(
                    text = message.senderName,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isAi -> RainbowIndigo
                        isUser -> Color(0xFF334155)
                        else -> RainbowOrange
                    }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = timeFormatted,
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Bubble Card
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 4.dp else 16.dp,
                    bottomEnd = if (isUser) 16.dp else 4.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        isUser -> Color(0xFFEEF2FF)
                        isAi -> Color.White
                        else -> Color.White
                    }
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    when {
                        isAi -> RainbowHorizontalGradient
                        isUser -> Brush.linearGradient(listOf(RainbowIndigo.copy(alpha = 0.3f), RainbowIndigo.copy(alpha = 0.3f)))
                        else -> Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFE2E8F0)))
                    }
                ),
                modifier = Modifier.widthIn(max = 310.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // If Message has an Attached Image
                    if (!message.mediaUri.isNullOrEmpty()) {
                        AsyncImage(
                            model = message.mediaUri,
                            contentDescription = "صورة مرفقة",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF1F5F9)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // If Message is Voice Note
                    if (message.isVoiceNote) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isUser) Color.White else Color(0xFFF8FAFC),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = RainbowIndigo,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable { isVoicePlaying = !isVoicePlaying }
                                ) {
                                    Icon(
                                        imageVector = if (isVoicePlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "تشغيل الصوت",
                                        tint = Color.White,
                                        modifier = Modifier.padding(6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Soundwave visualization bars
                                Row(
                                    modifier = Modifier.weight(1f),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val barHeights = listOf(14.dp, 22.dp, 8.dp, 18.dp, 26.dp, 12.dp, 20.dp, 15.dp, 24.dp, 10.dp)
                                    barHeights.forEachIndexed { index, h ->
                                        val color = RainbowSpectrumColors[index % RainbowSpectrumColors.size]
                                        Box(
                                            modifier = Modifier
                                                .width(3.5.dp)
                                                .height(h)
                                                .clip(RoundedCornerShape(2.dp))
                                                .background(if (isVoicePlaying) color else Color(0xFFCBD5E1))
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "00:${String.format("%02d", message.voiceDurationSeconds)}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Text content
                    if (message.messageText.isNotBlank()) {
                        Text(
                            text = message.messageText,
                            fontSize = 13.5.sp,
                            color = Color(0xFF1E293B),
                            lineHeight = 21.sp
                        )
                    }
                }
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(RainbowIndigo),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⭐", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun AiTypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, RainbowHorizontalGradient),
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "جنات تفكر وتكتب لك الإجابة النموذجية... 🌈✨",
                    fontSize = 12.sp,
                    color = RainbowIndigo,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(RainbowOrange)
                )
            }
        }
    }
}
