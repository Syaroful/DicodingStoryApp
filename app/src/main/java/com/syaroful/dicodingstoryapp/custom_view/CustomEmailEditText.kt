package com.syaroful.dicodingstoryapp.custom_view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.syaroful.dicodingstoryapp.R

class CustomEmailEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = ContextCompat.getString(context, R.string.email_hint)
        background = ContextCompat.getDrawable(context, R.drawable.custom_text_input)
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                no Action
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = s.toString().trim()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
                val isValidEmail = email.matches(emailPattern.toRegex())

                if (!isValidEmail) {
                    setError(context.getString(R.string.type_email_format_correctly), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
//                no action
            }
        })
    }

}