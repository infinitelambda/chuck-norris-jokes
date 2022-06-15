package com.infinitelambda.chuck.util

import kotlin.js.Promise
import kotlin.test.assertEquals

internal inline fun <T> Promise<T>.assertValue(expectedValue: T) = then { assertEquals(expectedValue, it) }

internal inline fun <T> Promise<T>.assertCatch(expectedException: Throwable) =
    catch { assertEquals(expectedException, it) }