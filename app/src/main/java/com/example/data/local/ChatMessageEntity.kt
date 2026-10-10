package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val channelId: String, // "jannat_ai", "group_general", "group_science", "group_humanities"
    val senderName: String,
    val senderAvatarEmoji: String = "👤",
    val isUser: Boolean = false,
    val isAi: Boolean = false,
    val messageText: String = "",
    val mediaUri: String? = null,
    val isVoiceNote: Boolean = false,
    val voiceDurationSeconds: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
