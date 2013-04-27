package org.aifgi.crawler.api

import java.net.URL

/**
 * @author aifgi
 */
public class CrawlerException(val url: URL,
                              message: String,
                              cause: Throwable? = null): Exception(message, cause) {
}