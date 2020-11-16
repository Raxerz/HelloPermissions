package com.raxerz.hello

interface PermissionsDenied {
    fun onResult(permissions: List<Permission>)
}