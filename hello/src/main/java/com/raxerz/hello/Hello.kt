package com.raxerz.hello

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import java.util.ArrayList

class Hello {

    companion object {

        private var mActivity: WeakReference<Activity>? = null

        fun iNeedPermissionsFor(activity: Activity): Hello.Companion{
            mActivity = WeakReference(activity)
            return this
        }

        fun iNeedPermissionsFor(fragment: Fragment): Hello.Companion{
            mActivity = WeakReference(fragment.requireActivity())
            return this
        }

        fun pleaseCheckAndAskThesePermissions(permissions: List<String>, onPermissionsResult: (permissionStatus: List<Permission>) -> Unit) {
            val permissionEntities = getPermissionEntities(permissions)
            mActivity?.get().let { context ->
                val permissionsStatus = requestPermissions(context!!, permissionEntities)
                onPermissionsResult(permissionsStatus)
            }
        }

        private fun getPermissionEntities(permissions: List<String>): MutableList<Permission>{
            val entities = mutableListOf<Permission>()
            permissions.forEach {
                entities.add(Permission(it))
            }
            return entities
        }

        fun doWeHavePermissionFor(context: Context, permission: Permission): Boolean {
            return PermissionChecker.checkSelfPermission(context, permission.name) == PermissionChecker.PERMISSION_GRANTED
        }

        private fun requestPermissions(activity: Activity, permissionEntities: List<Permission>): List<Permission> {
            val pendingPermissions = permissionEntities.filter { !doWeHavePermissionFor(activity, it) }
            if(pendingPermissions.isEmpty()){
                mActivity?.clear()
                mActivity = null
                permissionEntities.forEach {
                    it.status = PermissionStatus.PERMISSION_GRANTED
                }
                return permissionEntities
            } else {
                val intent = Intent(activity, PlaceholderActivity::class.java)
                intent.putParcelableArrayListExtra(Constants.EXTRA_REQUESTED_PERMISSIONS, pendingPermissions as ArrayList<out Parcelable>)
                activity.startActivity(intent)
            }
            return permissionEntities
        }

    }
}