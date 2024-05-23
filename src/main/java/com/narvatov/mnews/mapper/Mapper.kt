package com.narvatov.mnews.mapper

import com.narvatov.mnews.dto.response.chat.ChatNotification
import com.narvatov.mnews.dto.response.comment.CommentDTO
import com.narvatov.mnews.dto.response.news.SimpleNewsDTO
import com.narvatov.mnews.model.Comment
import com.narvatov.mnews.model.News
import com.narvatov.mnews.model.chat.message.ChatMessage

fun List<News>.mapToSimpleNewsDTO() = map { SimpleNewsDTO(it) }

fun List<Comment>.mapToCommentDTO() = map { CommentDTO(it) }

fun List<ChatMessage>.mapToChatNotifications() = map { ChatNotification(it) }
