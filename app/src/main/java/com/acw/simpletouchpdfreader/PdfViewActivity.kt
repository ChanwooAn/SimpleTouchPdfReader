package com.acw.simpletouchpdfreader

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView


class PdfViewActivity: AppCompatActivity() {

    lateinit var pdfView:PDFView
    lateinit var pdfUri: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        pdfUri = intent?.getStringExtra("pdfUri") ?: run {
            Toast.makeText(this, "PDF Uri not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        pdfView=findViewById(R.id.pdfView)

        pdfView.fromUri(Uri.parse(pdfUri))
            .enableSwipe(true)
            .defaultPage(0)
            .swipeHorizontal(true)
            .pageSnap(true)
            .autoSpacing(true)
            .pageFling(true)

            .load()

    }
}