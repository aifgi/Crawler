package org.aifgi.crawler.api

/**
 * @author aifgi
 */
public trait Filter<T> {
    public fun filter(value: T): Boolean
}