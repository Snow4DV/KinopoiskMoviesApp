package ru.snowadv.kinopoiskfeaturedmovies.data

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import okhttp3.Request
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.HeaderAuthenticator


class HeaderAuthenticatorTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var headerAuthenticator: HeaderAuthenticator
    private lateinit var client: OkHttpClient

    @Before
    fun setup() {
        headerAuthenticator = HeaderAuthenticator()
        client = OkHttpClient.Builder()
            .addInterceptor(headerAuthenticator)
            .build()
        mockServer = MockWebServer()
        mockServer.start()
    }

    @After
    fun cleanUp() {
        mockServer.shutdown()
    }

    @Test
    fun testAuthenticatorWithAuthToken() {
        headerAuthenticator.token = "some-token-123-test"
        mockServer.enqueue(MockResponse().setResponseCode(200)) // 200 response with empty body
        val request = Request.Builder()
            .url(mockServer.url("/"))
            .build()
        val response = client.newCall(request).execute()
        assertEquals("some-token-123-test", response.request.header("x-api-key"))
    }

    @Test
    fun testAuthenticatorWithNoAuthToken() {
        headerAuthenticator.token = null
        mockServer.enqueue(MockResponse().setResponseCode(200))
        val request = Request.Builder()
            .url(mockServer.url("/"))
            .build()
        val response = client.newCall(request).execute()
        assert("x-api-key" !in response.request.headers.map { it.first })
    }



}