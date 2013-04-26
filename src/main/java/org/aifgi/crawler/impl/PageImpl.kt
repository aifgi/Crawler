package org.aifgi.crawler.impl

import java.net.URL
import java.util.ArrayList
import org.aifgi.crawler.api.Page

/**
 * @author aifgi
 */
internal class PageImpl(url: URL, lines: List<String>): Page {
    public override val url: URL = url
    public override val lines: List<String> = lines
}