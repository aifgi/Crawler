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

/**
 * @author aifgi
 */
public object CrawlerFacade {
    public val linkHolder: LinkHolder = LinkHolderImpl()
    private val searcher = LinkSearcher(linkHolder)
    private val saver: PageSaver

    {
        val dir = File("${System.getProperty("user.home")}/.crawler/")
        dir.mkdir()
        saver = PageSaver(dir)
    }

    public fun loadPage(url: URL): Page {
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

    public fun search(page: Page) {
        val res = searcher.handle(page)
        return res
    }

    public fun save(page: Page) {
        saver.handle(page)
    }
}