
package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain

@GwtHtml("src/test/webapp/todoCardListElementTest.html")
class TodoCardListElementTest : GwtTestSpec({
    Given("TodoCardListElement application is running") {
        When("Todos are added to the store") {
            val logs = document.getConsoleLogs().map { it.toString() }
            Then("TodoCardListElement should render active and completed todos") {
                logs.any { it.contains("List Test: Added active todos:") }.shouldBeTrue()
                logs.any { it.contains("List Test: Added completed todo:") }.shouldBeTrue()
                logs.any { it.contains("List Test: Added removable todo:") }.shouldBeTrue()
                logs shouldContain "TodoCardListElement attached to body"
            }
        }
    }
})