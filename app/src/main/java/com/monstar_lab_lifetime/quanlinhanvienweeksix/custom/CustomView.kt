package com.monstar_lab_lifetime.quanlinhanvienweeksix.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.monstar_lab_lifetime.quanlinhanvienweeksix.R


class CustomView : AppCompatEditText {
        constructor(context: Context?) : super(context)

        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

        constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr)
        val validateEmail: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var mImage : Drawable? = null
        init {
            mImage = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_check_24, null
            )
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence,
                    start: Int, before: Int, count: Int
                ) {
                }


                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                override fun afterTextChanged(s: Editable) {
                    if (s.matches(validateEmail.toRegex())) {
                        showImage()
                    }
                    else{
                        hideImage()
                    }
                }
            })
        }
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun showImage(){
            this.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, mImage, null
            )
            this.setTextColor(Color.BLACK)
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun hideImage(){
            this.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, null, null
            )
            this.setTextColor(Color.RED)
        }
    }
