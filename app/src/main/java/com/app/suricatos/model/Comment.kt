package com.app.suricatos.model

import java.io.Serializable

data class Comment(
    val author: Author,
    val comment: String
): Serializable