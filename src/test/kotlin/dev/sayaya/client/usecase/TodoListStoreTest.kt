package dev.sayaya.client.usecase

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.collections.shouldContain

@GwtHtml("src/test/webapp/todoListStoreTest.html")
class TodoListStoreTest : GwtTestSpec({
    Given("TodoStore application is running") {
        When("TodoStore is initialized and items are added") {
            val logs = document.getConsoleLogs()
            Then("Subscription should receive updates") {
                logs shouldContain "TodoStore Updated: Count=1"
                logs shouldContain "Last Item: New Task via Delegate"
            }
        }
    }
})