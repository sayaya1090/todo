package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.shouldBe

@GwtHtml("src/test/webapp/newTodoInputElementTest.html")
class NewTodoInputElementTest : GwtTestSpec({
    Given("NewTodoInputElement is rendered") {
        val input = page.locator(".new-todo")
        When("User types 'Task via Playwright' and presses Enter") {
            input.pressSequentially("Task via Playwright")
            Then("The input field should be typed string") {
                val value = input.evaluate("element => element.value")
                value shouldBe "Task via Playwright"
            }

            And("User presses Enter") {
                input.press("Enter")
                Then("It should add the task to TodoList") {
                    page.shouldContainLog("TODO ADDED: Task via Playwright")
                }

                Then("The input field should be empty") {
                    val value = input.evaluate("element => element.value")
                    value shouldBe ""
                }
            }
        }
    }
})