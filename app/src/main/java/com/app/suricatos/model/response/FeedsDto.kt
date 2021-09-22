package com.app.suricatos.model.response

import com.app.suricatos.model.Author
import com.app.suricatos.model.Categories
import com.app.suricatos.model.Comment

data class FeedsDto(
    var id: String? = "",
    val title: String,
    val category: String,
    val description: String?,
    val author: AuthorDto,
    val favorited: Boolean? = false,
    val comments: Array<Comment>? = null
)