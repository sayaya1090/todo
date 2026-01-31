package dev.sayaya.client.usecase

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.collections.shouldContain

@GwtHtml("src/test/webapp/cryptoTest.html")
class CryptoTest : GwtTestSpec({
    Given("Crypto utility is available in browser") {
        When("randomUUID() is called") {
            val logs = page.getConsoleLogs()
            Then("It should return a valid UUID string") {
                logs shouldContain "UUID Format Valid"
                logs shouldContain "UUID Uniqueness Valid"
            }
        }
    }
})