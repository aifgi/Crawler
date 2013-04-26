package org.aifgi.crawler.api

import java.net.URL
import java.util.ArrayList
import java.util.Collections

/**
 * @author aifgi
 */
public trait Page {
    public val url: URL
    public val lines: List<String>
}