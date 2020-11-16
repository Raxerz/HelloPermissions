package com.raxerz.hellopermissions

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.raxerz.hello.Hello
import com.raxerz.hello.Permission
import com.raxerz.hello.PermissionsGranted
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            Hello.iNeedPermissionsFor(this).pleaseCheckAndAskThesePermissions(
                    listOf(android.Manifest.permission.CAMERA),
                    {
                        it.forEach {
                            Log.d(TAG, "Permission : Name " + it.name +" Status " + it.status)
                        }
                    })
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}