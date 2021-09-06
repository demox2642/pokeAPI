package com.skilbox.mypokemons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skilbox.mypokemons.database.Database

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Database.init(context = this)
    }
}
