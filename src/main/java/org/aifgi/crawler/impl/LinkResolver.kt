package org.aifgi.crawler.impl

import java.net.URL

/**
 * @author aifgi
 */

// TODO: support <a href="../file.html">text</a>
internal fun resolve(pageUrl: URL, link: String): URL {
    val protocol = pageUrl.getProtocol()
    val host = pageUrl.getHost()
    val file = pageUrl.getFile()!!

    if (link.startsWith("/")) {
        return URL(protocol, host, link)
    }
    else if (link.startsWith(".")) {
        return URL(protocol, host, link)
    }
    else {
        return URL(protocol, host, "${file.substring(0, file.lastIndexOf('/') + 1)}${link}")
    }
}
