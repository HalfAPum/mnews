package com.narvatov.mnews.model.dto.response

import java.util.*

interface SimpleNewsDTO {

    var id: Int
    var headline: String?
    var content: String?
    var author: String?
    var creation_date: Date?
    var category: String?

}