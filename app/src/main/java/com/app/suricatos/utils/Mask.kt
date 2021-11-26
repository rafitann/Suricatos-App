package com.app.suricatos.utils

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher

object Mask {

    enum class Format(private val mask: String) {
        MOBILE_PHONE("9-9999-9999"),
        PHONE("9999-9999"),
        DATE("99/99/9999");

        override fun toString(): String = mask
    }

    class Formatter(private vararg val formats: Format) {

        fun format(text: String): String {
            val editable = SpannableStringBuilder()

            var defaultMask = formats.first()

            text.forEach {
                editable.append(it)

                val format = getFormat(editable, formats)
                if (format == defaultMask) {
                    applyFormat(editable, format)
                } else {
                    defaultMask = format
                    val text = unFormat(editable.toString())
                    editable.clear()
                    text.forEach {
                        editable.append(it)
                        applyFormat(editable, format)
                    }
                }
            }
            return editable.toString()
        }

        fun unFormat(text: String): String = text.replace("[^0-9]*".toRegex(), "")

        internal fun applyFormat(editable: Editable, format: Format): String {
            val mask = format.toString()
            val editableLength = editable.length

            if (editableLength < mask.length) {
                if (mask[editableLength] != '9') {
                    editable.append(mask[editableLength])
                } else if (mask[editableLength - 1] != '9') {
                    editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
                } else {
                    val char = editable.last()

                    if (mask[editableLength] == '9') {
                        if (char !in '0'..'9') {
                            val limit = editable.subSequence(0, editable.length - 1)
                            editable.clear()
                            editable.append(limit)
                        }
                    }

                }

            } else if (editableLength > mask.length) {
                val limit = editable.subSequence(0, mask.length)
                editable.clear()
                editable.append(limit)
            }

            return editable.toString()
        }

        internal fun getFormat(editable: Editable, formats: Array<out Format>): Format {
            var format: Format? = null //formats.first()
            formats.sortBy { it.toString().length }

            for (it in formats) {
                val mask = it.toString()
                if (editable.length <= mask.length) {
                    format = it
                    break
                }
            }

            return format ?: formats.last()
        }
    }

    class Watcher(private vararg val formats: Format) : TextWatcher {

        private var isRunning = false
        private var isDeleting = false

        private var defaultMask: Format
        private var formatter: Formatter

        init {
            formats.sortBy { it.toString().length }
            defaultMask = formats.first()

            formatter = Formatter(*formats)
        }

        override fun afterTextChanged(editable: Editable) {
            if (isRunning || isDeleting) {
                return
            }

            isRunning = true

            if (!editable.toString().isBlank())
                applyMask(editable)

            isRunning = false
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            isDeleting = count > after
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        private fun applyMask(editable: Editable) {
            val format = formatter.getFormat(editable, formats)

            if (format == defaultMask) {
                formatter.applyFormat(editable, format)
            } else {
                defaultMask = format

                val text = formatter.unFormat(editable.toString())//editable.toString()
                editable.clear()
                text.forEach {
                    editable.append(it)
                    formatter.applyFormat(editable, format)
                }
            }
        }
    }

}