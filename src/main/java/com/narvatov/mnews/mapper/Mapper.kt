package com.narvatov.mnews.mapper

import com.narvatov.mnews.dto.response.news.SimpleNewsDTO
import com.narvatov.mnews.model.News

fun List<News>.mapToSimpleNewsDTO() = map { SimpleNewsDTO(it) }