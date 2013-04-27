package org.aifgi.crawler.api

import java.net.URL

/**
 * @author aifgi
 */
public trait PageLoader {
    public fun loadPage(url: URL): Page
}