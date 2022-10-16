package com.example.imgurapiapp.common

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun AppCompatEditText.afterTextChangedDebounce(delayMillis: Long = 300, input: (String) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    this.doAfterTextChanged { editable ->
        if (editable != null) {
            val newtInput = editable.toString()
            debounceJob?.cancel()
            if (lastInput != newtInput) {
                lastInput = newtInput
                debounceJob = uiScope.launch {
                    delay(delayMillis)
                    if (lastInput == newtInput) {
                        input(newtInput)
                    }
                }
            }
        }
    }
}