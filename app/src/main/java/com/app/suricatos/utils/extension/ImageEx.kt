package com.app.suricatos.utils.extension

import android.widget.ImageView
import com.app.suricatos.repository.BASE_URL
import com.app.suricatos.repository.Repository
import com.squareup.picasso.Callback
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import java.lang.Exception

fun ImageView.loadByUrl(url: String?) {
    url ?: return
    Picasso.get().load(url).into(this)
}

//fun ImageView.load(id: String?) {
//    id ?: return
//
//    val referece = this
//
//    val builder = Picasso.Builder(this.context)
//    builder.downloader(OkHttp3Downloader(Repository.httpClient()))
//
//    val picasso = builder.build()
//    picasso.load("${BASE_URL}feed//$id/attach").into(referece, object: Callback {
//        override fun onSuccess() {}
//
//        override fun onError(e: Exception?) {
//            Picasso.get().load("http://placekitten.com/g/200/300").into(referece)
//        }
//
//    })
//}