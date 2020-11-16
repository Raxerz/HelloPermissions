package com.raxerz.hello

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class PlaceholderActivity: AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val PERMISSION_REQUEST_ID = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onPermissionsRequestHandled()
    }

    fun List<Permission>.asArrayOfStrings(): Array<String>{
        return this.map {
            it.name
        }.toTypedArray()
    }

    inline fun getRequestedPermissions(): List<Permission>{
        return intent.getParcelableArrayListExtra(Constants.EXTRA_REQUESTED_PERMISSIONS)
    }

    private fun requestPermissions(){
        val pendingPermissions = getRequestedPermissions().asArrayOfStrings()
        ActivityCompat.requestPermissions(
                this@PlaceholderActivity,
                pendingPermissions,
                PERMISSION_REQUEST_ID)
    }

    private fun shouldShowRationaleDialogFor(pendingPermissions: List<Permission>): Boolean {
        return pendingPermissions.firstOrNull {
                    ActivityCompat.shouldShowRequestPermissionRationale(this@PlaceholderActivity, it.name)
                } is Permission
    }

    private fun onPermissionsRequestHandled(){
        val requestedPermissions = getRequestedPermissions()
        val pendingPermissions = getPendingPermissions(requestedPermissions)
        requestPermissionsAgain(pendingPermissions)
    }

    private fun getPendingPermissions(pendingPermissions: List<Permission>): List<Permission>{
        return pendingPermissions.filter {permission ->
            !Hello.doWeHavePermissionFor(this@PlaceholderActivity, permission)
        }
    }

    private fun requestPermissionsAgain(pendingPermissions: List<Permission>){
        if(pendingPermissions.isNotEmpty()){
            if(shouldShowRationaleDialogFor(pendingPermissions)){
                showMessageOKCancel("You need to allow access to both the permissions",
                        DialogInterface.OnClickListener { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions()
                                finish()
                            }
                        })
                return
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@PlaceholderActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

}