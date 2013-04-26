package org.aifgi.crawler

import org.aifgi.crawler.impl.CrawlerFacade
import java.net.URL
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.logging.Logger
import java.util.logging.Level
import org.aifgi.crawler.impl.HostFilter

/**
 * @author aifgi
 */

fun main(args: Array<String>) {
    val log = Logger.getLogger("Main")
    val threadsNumber = Runtime.getRuntime().availableProcessors() * 50
    val executor = Executors.newFixedThreadPool(threadsNumber)
    val linkHolder = CrawlerFacade.linkHolder
    val host = "ru.wikipedia.org"
    linkHolder.addLink(URL("http", host, "/"))
    linkHolder.addFilter(HostFilter(host))

    // TODO: better way
    val semaphore = Semaphore(threadsNumber, true)
    semaphore.acquire()
    while (true) {
        semaphore.release()
        executor.execute(object: Runnable {
            public override fun run() {
                val url = linkHolder.next()
                semaphore.acquire()
                try {
                    val page = CrawlerFacade.loadPage(url)
                    CrawlerFacade.search(page)
                    CrawlerFacade.save(page)
                }
                catch (e: IOException) {
                    log.log(Level.INFO, "Url: ${url}", e)
                }
                finally {
                    semaphore.release()
                }
            }
        })
        semaphore.acquire()
    }
}