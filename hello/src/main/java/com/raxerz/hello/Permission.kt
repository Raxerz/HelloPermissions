package com.raxerz.hello

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Permission (val name: String, var status: PermissionStatus = PermissionStatus.PERMISSION_DENIED): Parcelable