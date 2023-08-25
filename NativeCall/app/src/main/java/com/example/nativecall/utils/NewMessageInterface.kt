package com.example.nativecall.utils

import com.example.nativecall.models.MessageModel

interface NewMessageInterface {
    fun onNewMessage(message: MessageModel)
}