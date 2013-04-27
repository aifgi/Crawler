package org.aifgi.crawler.impl

import org.aifgi.crawler.api.LinkHolder
import java.net.URL
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.Condition
import org.aifgi.crawler.api.Filter
import java.util.HashSet
import java.util.ArrayList
import java.util.LinkedList
import java.util.logging.Logger

/**
 * @author aifgi
 */
public class LinkHolderImpl: LinkHolder {
    private val log = Logger.getLogger("LinkHolder")
    private val filters = ArrayList<Filter<URL>>()
    private val known = HashSet<URL>()
    private val links = ArrayList<URL>()
    private val lock = ReentrantLock()
    private val notEmpty: Condition = lock.newCondition()

    public override fun addLink(url: URL, title: String): Boolean {
        if (doFilter(url)) {
            if (!known.contains(url)) {
                lock.lock()
                try {
                    if (!known.contains(url)) {
                        log.info("Adding url to holder: ${url}")
                        links.add(url)
                        notEmpty.signal()
                        known.add(url)
                        return true
                    }
                }
                finally {
                    lock.unlock()
                }
            }
        }
        return false
    }

    private fun doFilter(url: URL): Boolean {
        for (f in filters) {
            if (!f.filter(url)) {
                return false
            }
        }
        return true
    }

    public override fun next(): URL {
        lock.lock()
        try {
            while (links.isEmpty()) {
                notEmpty.await()
            }
            val url = links.remove(links.size - 1)
            log.info("Getting url from holder: ${url}")
            return url
        }
        finally {
            lock.unlock()
        }
    }

    public override fun hasNext(): Boolean = !links.isEmpty()

    public override fun addFilter(filter: Filter<URL>) {
        filters.add(filter)
    }

    public override fun removeFilter(filter: Filter<URL>) {
        filters.remove(filter)
    }
}