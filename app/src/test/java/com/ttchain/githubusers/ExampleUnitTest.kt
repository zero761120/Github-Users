package com.ttchain.githubusers

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun sha512() {
        val expect = "66ff3ab1595a6191ed343c56d1a5e50cf9596d62f1f3f83d92af7db7a1068a1d2b99149fc11a288759811634cb6dbbe00a51c35e6905cbe92a86b9b46a260cd7"
        val actual = "albert_ac6216602076993163544AAAAAAAAAAA586f09c3-a39e-477c-b4ee-8e47736b1dab".getSHA512()
        assertEquals(expect, actual)
    }
}
