package org.aifgi.crawler.impl

import java.net.URL
import org.aifgi.crawler.api.Page
import java.util.ArrayList
import java.io.InputStreamReader
import java.io.BufferedReader
import org.aifgi.crawler.impl.LinkHolderImpl
import org.aifgi.crawler.api.LinkHolder
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.File
import java.io.InputStream
import org.aifgi.crawler.api.PageLoader
import org.aifgi.crawler.api.Filter
import java.io.IOException
import org.aifgi.crawler.api.CrawlerException
import java.util.logging.Logger

/**
 * @author aifgi
 */
public object CrawlerFacade {
    private val log = Logger.getLogger("CrawlerFacade")
    private val linkHolder = LinkHolderImpl()
    private val searcher = LinkSearcher(linkHolder)
    private val loader = PageLoaderImpl()
    private val saver: PageSaver

    {
        val dir = File("${System.getProperty("user.home")}/.crawler/")
        dir.mkdir()
        saver = PageSaver(dir)
    }

    public fun addLink(url: URL) {
        linkHolder.addLink(url)
    }

    public fun addUrlFilter(filter: Filter<URL>) {
        linkHolder.addFilter(filter)
    }

    public fun processNextPage() {
        val url = linkHolder.next()
        try {
            processPage(url)
        }
        catch(e: IOException) {
            throw CrawlerException(url, "Error while crawling page", e)
        }
    }

    private fun processPage(url: URL) {
        log.info("Process page: ${url}")
        val page = loader.loadPage(url)
        searcher.handle(page)
        saver.handle(page)
        log.info("Page ${url} successfully processed")
    }
}