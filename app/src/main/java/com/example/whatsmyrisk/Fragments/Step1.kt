package com.example.whatsmyrisk.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.R

class Step1 :  Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
               R.layout.welcome_dynamic_text, container, false
        )
    }
    // Here "layout_login" is a name of layout file
    // created for LoginFragment
}