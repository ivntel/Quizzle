package com.tman.quizzle.utils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
