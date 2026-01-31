
package dev.sayaya.client.interfaces

import com.microsoft.playwright.Locator
import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/todoCardElementTest.html")
class TodoCardElementTest : GwtTestSpec({
    Given("TodoCardElement is rendered") {
        When("Todo cards are displayed") {
            val todoItems = page.locator(".todo-item")
            todoItems.first().waitFor()

            Then("It should render 3 todo items") {
                todoItems.count() shouldBe 3
            }

            Then("First todo should show correct title") {
                val firstTodo = todoItems.nth(0)
                val title = firstTodo.locator(".todo-title")
                title.innerText() shouldBe "Buy groceries"
            }

            Then("First todo should not be completed") {
                val firstTodo = todoItems.nth(0)
                val classes = firstTodo.getAttribute("class")
                classes shouldNotContain "completed"
            }

            Then("Second todo should be completed") {
                val secondTodo = todoItems.nth(1)
                val classes = secondTodo.getAttribute("class")
                classes shouldContain "completed"
            }

            Then("Second todo checkbox should be selected") {
                val secondTodo = todoItems.nth(1)
                val checkbox = secondTodo.locator(".toggle")
                checkbox.evaluate("element => element.checked") shouldBe true
            }
        }

        When("User toggles checkbox") {
            val todoItems = page.locator(".todo-item")
            val firstTodo = todoItems.nth(0)
            val checkbox = firstTodo.locator(".toggle")

            checkbox.click()

            Then("Todo should become completed") {
                val classes = firstTodo.getAttribute("class")
                classes shouldContain "completed"
            }

            And("Toggling again should make it active") {
                checkbox.click()
                val classes = firstTodo.getAttribute("class")
                classes shouldNotContain "completed"
            }
        }

        When("User double-clicks title to edit") {
            val todoItems = page.locator(".todo-item")
            val firstTodo = todoItems.nth(0)
            val titleLabel = firstTodo.locator(".todo-title")

            titleLabel.dblclick()

            Then("It should enter editing mode") {
                val classes = firstTodo.getAttribute("class")
                classes shouldContain "editing"
            }

            Then("Edit input should be visible") {
                val editInput = firstTodo.locator(".edit")
                editInput.isVisible shouldBe true
            }

            And("edits title and presses Enter") {
                val editInput = firstTodo.locator(".edit")
                editInput.click(Locator.ClickOptions().setClickCount(3))
                editInput.press("Backspace")
                editInput.pressSequentially("Updated task title")
                editInput.press("Enter")

                Then("Title should be updated") {
                    val updatedTitle = firstTodo.locator(".todo-title")
                    updatedTitle.innerText() shouldBe "Updated task title"
                }

                Then("Should exit editing mode") {
                    val classes = firstTodo.getAttribute("class")
                    classes shouldNotContain "editing"
                }
            }
        }

        When("User presses Escape while editing") {
            val todoItems = page.locator(".todo-item")
            val secondTodo = todoItems.nth(1)
            val titleLabel = secondTodo.locator(".todo-title")
            val originalTitle = titleLabel.innerText()

            // Enter edit mode
            titleLabel.dblclick()

            // Edit but cancel
            val editInput = secondTodo.locator(".edit")
            editInput.click(Locator.ClickOptions().setClickCount(3))
            editInput.press("Backspace")
            editInput.pressSequentially("This will be cancelled")
            editInput.press("Escape")

            Then("Should keep original title") {
                val currentTitle = secondTodo.locator(".todo-title")
                currentTitle.innerText() shouldBe originalTitle
            }

            Then("Should exit editing mode") {
                val classes = secondTodo.getAttribute("class")
                classes shouldNotContain "editing"
            }
        }

        When("User tries to save invalid title") {
            val todoItems = page.locator(".todo-item")
            val firstTodo = todoItems.nth(0)
            val titleLabel = firstTodo.locator(".todo-title")
            val originalTitle = titleLabel.innerText()

            // Enter edit mode
            titleLabel.dblclick()

            // Try invalid input
            val editInput = firstTodo.locator(".edit")
            editInput.click(Locator.ClickOptions().setClickCount(3))
            editInput.press("Backspace")
            editInput.pressSequentially("X")
            editInput.press("Enter")

            Then("Should keep original title") {
                val currentTitle = firstTodo.locator(".todo-title")
                currentTitle.innerText() shouldBe originalTitle
            }
        }

        When("User clicks destroy button") {
            val initialCount = page.locator(".todo-item").count()
            val todoItems = page.locator(".todo-item")
            val firstTodo = todoItems.nth(0)
            val destroyBtn = firstTodo.locator(".destroy")

            destroyBtn.click()

            Then("Todo should be removed") {
                val currentCount = page.locator(".todo-item").count()
                currentCount shouldBe (initialCount - 1)
            }
        }
    }
})