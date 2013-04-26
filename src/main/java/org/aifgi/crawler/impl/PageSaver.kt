package org.aifgi.crawler.impl

import java.io.File
import java.util.concurrent.Executors
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.IOException
import org.aifgi.crawler.api.Page
import org.aifgi.crawler.api.PageHandler

/**
 * @author aifgi
 */
internal class PageSaver(val directory: File): PageHandler {
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1)

    public override fun handle(page: Page) {
        executor.execute(object: Runnable {
            public override fun run() {
                doSave(page)
            }
        })
    }

    private fun doSave(page: Page) {
        val url = page.url
        val file = File(directory, "${url.getHost()}${url.getFile()?.replaceAll("[/\\?]", "_")}")
        try {
            val output = BufferedWriter(FileWriter(file))
            try {
                for (st in page.lines) {
                    output.write(st)
                }
            }
            finally {
                output.close()
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
}