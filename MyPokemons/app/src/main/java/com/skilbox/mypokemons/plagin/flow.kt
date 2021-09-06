package com.skilbox.flowsearchmovie

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {

    return callbackFlow<String> {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangeListener)
        awaitClose {
            Log.e("textlisinner", "awaitClose")
            this@textChangedFlow.removeTextChangedListener(textChangeListener)
        }
    }
}

fun CheckBox.checkChangeesFlow(): Flow<Boolean> {
    return callbackFlow {
        val chekedChangeListenner = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            sendBlocking(isChecked)
        }
        setOnCheckedChangeListener(chekedChangeListenner)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }

}
