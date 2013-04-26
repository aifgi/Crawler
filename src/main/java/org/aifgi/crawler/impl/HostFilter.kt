package org.aifgi.crawler.impl

import org.aifgi.crawler.api.Filter
import java.net.URL

/**
 * @author aifgi
 */
public class HostFilter(val host: String): Filter<URL> {
    public override fun filter(value: URL): Boolean {
        val anotherHost = value.getHost()
        return host.equalsIgnoreCase(anotherHost ?: "")
    }
}