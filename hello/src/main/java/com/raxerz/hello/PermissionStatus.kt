package com.raxerz.hello

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class PermissionStatus: Parcelable {
    PERMISSION_GRANTED,
    PERMISSION_DENIED
}