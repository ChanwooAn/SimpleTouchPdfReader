package com.acw.simpletouchpdfreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import java.io.File
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adView: AdView
    lateinit var adapter: RecyclerView.Adapter<PdfItemHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.pdf_recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(this)
        adView = findViewById(R.id.adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, MANAGE_EXTERNAL_STORAGE_REQUEST_CODE)
        } else {
            getPdfList(this)
            Log.d("pdfListLog",pdfList.joinToString(" "))
            Log.d("pdfListLog",pdfList.size.toString())

            adapter = PdfRecyclerViewAdapter({uri->
                val intent=Intent(applicationContext,PdfViewActivity::class.java).putExtra(
                    "pdfUri",uri.toString()
                )
                startActivity(intent)
            },pdfList)
            recyclerView.adapter = adapter
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MANAGE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (Environment.isExternalStorageManager()) {
                getPdfList(this)
                adapter = PdfRecyclerViewAdapter({uri->
                    val intent=Intent(applicationContext,PdfViewActivity::class.java).putExtra(
                        "pdfUri",uri.toString()
                    )

                    startActivity(intent)
                },pdfList)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "외부저장소 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    val pdfList = mutableListOf<PdfItem>()
    fun getPdfList(context: Context) {
        val rootDir = File(Environment.getExternalStorageDirectory().absolutePath)
        rootDir.walk().forEach { file ->
            // 파일이 PDF 파일인 경우, 리스트에 추가
            if (file.isFile && file.extension.toLowerCase(Locale.ROOT) == "pdf") {
                pdfList.add(PdfItem(file.name, Uri.parse(file.path)))
            }
        }

    }

    companion object {
        const val MANAGE_EXTERNAL_STORAGE_REQUEST_CODE = 101
    }
}