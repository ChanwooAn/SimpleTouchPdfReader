package com.acw.simpletouchpdfreader

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File


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

        pdfView.fromFile(File(pdfUri))
            .enableDoubletap(true)
            .enableSwipe(true)
            .defaultPage(0)
            .swipeHorizontal(false)
            .spacing(0)
            .onTap {
                val currentPage = pdfView.currentPage
                // 마지막 페이지 인덱스를 가져옴
                val lastPage = pdfView.pageCount - 1
                // 다음 페이지 인덱스를 계산
                val nextPage = if (currentPage < lastPage) currentPage + 1 else lastPage
                // 다음 페이지로 이동
                pdfView.jumpTo(nextPage)
                true
            }
            .autoSpacing(true)
            .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view

            .load()

    }
}