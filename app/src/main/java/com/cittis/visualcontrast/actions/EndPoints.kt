package com.cittis.visualcontrast.actions

object EndPoints {
    // Firebase - Login
    @kotlin.jvm.JvmField
    var FireBasePath: String = "locations"
    // Users
    var FireBaseID = ""
    // Server
    // Server Main 172.20.1.14 172.20.1.19 192.168.43.137 172.20.1.13
    private const val SERVER_MAIN = "ivs.cittis.com.co"
    private const val PATH_SOURCE = "https://$SERVER_MAIN/isv/source/"
    private const val PATH_MAIN = "${PATH_SOURCE}controller/isv/"
}