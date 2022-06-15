package com.infinitelambda.chuck.util

import platform.Foundation.NSError
import kotlin.native.concurrent.isFrozen
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class TestCompletionHandler<T>(
    private val expectedData: T? = null,
    private val expectedError: NSError? = null
) : CompletionHandler<T> {

    override fun invoke(data: T?, error: NSError?) {
        if (expectedData == null) {
            assertNull(data, "Expected error, but got $data instead")
            assertEquals(expectedError, error, "Expected $expectedError, but got $error instead")
            assertTrue(error.isFrozen)
        } else {
            assertNull(error, "Expected data, but got $error instead")
            assertEquals(expectedData, data, "Expected $expectedData, but got $data instead")
            assertTrue(data.isFrozen)
        }
    }

}