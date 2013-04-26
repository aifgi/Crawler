package org.aifgi.crawler.impl

import org.aifgi.crawler.api.Page
import java.net.URL
import java.util.regex.Pattern
import java.util.Collections
import java.util.ArrayList
import org.aifgi.crawler.api.LinkHolder
import java.util.concurrent.Executors
import org.aifgi.crawler.api.PageHandler

/**
 * @author aifgi
 */

private val LINK = Pattern.compile(".*<a.*href\\s*=\\s*[\"\']([\\w\\s\\./\\?=]+)[\"\'].*>(.*)</a>.*")!!

public class LinkSearcher(val linkHolder: LinkHolder): PageHandler {
    public override fun handle(page: Page) {
        val url = page.url
        for (line in page.lines) {
            val matcher = LINK.matcher(line)
            if (matcher.matches()) {
                val address = matcher.group(1)
                if (address != null && !address.isEmpty()) {
                    linkHolder.addLink(resolve(url, address))
                }
            }
        }
    }
}