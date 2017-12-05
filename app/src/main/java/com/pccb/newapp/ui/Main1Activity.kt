package com.pccb.newapp.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.pccb.newapp.R
import kotlinx.android.synthetic.main.activity_main1.*

class Main1Activity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_product -> {
                message.setText(R.string.title_product)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_consultant -> {
                message.setText(R.string.title_consultant)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_forum -> {
                message.setText(R.string.title_forum)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my -> {
                message.setText(R.string.title_my)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
