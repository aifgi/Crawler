package org.aifgi.crawler

import org.aifgi.crawler.impl.CrawlerFacade
import java.net.URL
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.logging.Logger
import java.util.logging.Level
import org.aifgi.crawler.impl.HostFilter
import org.aifgi.crawler.api.CrawlerException

/**
 * @author aifgi
 */

fun main(args: Array<String>) {
    val log = Logger.getLogger("Main")
    val threadsNumber = Runtime.getRuntime().availableProcessors() * 50
    val executor = Executors.newFixedThreadPool(threadsNumber)
    val host = "ru.wikipedia.org"
    CrawlerFacade.addLink(URL("http", host, "/"))
    CrawlerFacade.addUrlFilter(HostFilter(host))

    // TODO: better way
    val semaphore = Semaphore(threadsNumber, true)
    semaphore.acquire()
    while (true) {
        semaphore.release()
        executor.execute(object: Runnable {
            public override fun run() {
                semaphore.acquire()
                try {
                    CrawlerFacade.processNextPage()
                }
                catch (e: CrawlerException) {
                    log.log(Level.INFO, "${e.getMessage()}\nUrl: ${e.url}", e)
                }
                finally {
                    semaphore.release()
                }
            }
        })
        semaphore.acquire()
    }
}