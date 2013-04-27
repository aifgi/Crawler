package org.aifgi.crawler.impl

import org.aifgi.crawler.api.PageLoader
import java.net.URL
import org.aifgi.crawler.api.Page
import java.util.ArrayList
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.logging.Logger

/**
 * @author aifgi
 */
public class PageLoaderImpl: PageLoader {
    private val log = Logger.getLogger("PageLoader")

    override fun loadPage(url: URL): Page {
        log.info("Loading page: ${url}")
        val connection = url.openConnection()!!
        val inputStream = connection.getInputStream()!!
        val reader = BufferedReader(InputStreamReader(inputStream))
        val list = ArrayList<String>()
        var line = reader.readLine()
        while (line != null) {
            list.add(line!!)
            line = reader.readLine()
        }
        return PageImpl(url, list)
    }
}