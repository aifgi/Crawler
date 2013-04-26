package org.aifgi.crawler.api

import java.net.URL

/**
 * @author aifgi
 */
public trait LinkHolder: Iterator<URL> {
    public fun addLink(url: URL, title: String = ""): Boolean

    public fun addFilter(filter: Filter<URL>)

    public fun removeFilter(filter: Filter<URL>)
}