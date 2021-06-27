package com.example.flighgearapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flighgearapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class FgActivity: AppCompatActivity() {

    private var vm:ViewModel = ViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // make the joystick change bind to aileron and elevator
        val joystick = findViewById<Joystick>(R.id.joiystick)
        joystick.onChange = { a :Float, e :Float ->
            vm.aileron = a
            vm.elevator = e
        }

        binding.viewModel = vm

    }

    override fun onStop() {
        super.onStop()
        vm.disconnect()
    }
}