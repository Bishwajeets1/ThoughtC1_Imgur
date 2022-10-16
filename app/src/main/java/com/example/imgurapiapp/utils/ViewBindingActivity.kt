package com.example.imgurapiapp.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class ViewBindingActivity<Binding : ViewBinding> : AppCompatActivity() {


    private var _binding: Binding? = null
    protected val binding get() = _binding!!
    abstract fun provideBinding(inflater: LayoutInflater): Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = provideBinding(LayoutInflater.from(this))
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
