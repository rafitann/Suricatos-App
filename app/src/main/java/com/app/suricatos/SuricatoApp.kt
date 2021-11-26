package com.app.suricatos

import io.paperdb.Paper

class SuricatoApp: android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        Paper.init(applicationContext)
    }

}