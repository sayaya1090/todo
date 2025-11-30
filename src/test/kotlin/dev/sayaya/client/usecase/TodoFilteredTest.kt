package dev.sayaya.client.usecase

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.collections.shouldContain

@GwtHtml("src/test/webapp/todoFilteredTest.html")
class TodoFilteredTest : GwtTestSpec({
    Given("TodoFiltered use case is running") {
        When("todos are added and filter state changes") {
            val logs = document.getConsoleLogs().map { it.toString() }

            Then("TodoFiltered should emit all todos when filter is null") {
                logs shouldContain "TodoFiltered After Null Filter"
                // ACTIVE 2개 + COMPLETED 1개 = 3개
                logs shouldContain "TodoFiltered Emitted: 3"
            }

            Then("TodoFiltered should emit only COMPLETED todos when filter is COMPLETED") {
                logs shouldContain "TodoFiltered After COMPLETED Filter"
                // COMPLETED 1개만 남아야 함
                logs shouldContain "TodoFiltered Emitted: 1"
            }
        }
    }
})