
package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@GwtHtml("src/test/webapp/todoCardElementTest.html")
class TodoCardElementTest : GwtTestSpec({
    fun WebElement.doubleClick() {
        (document as JavascriptExecutor).executeScript("""
            var evt = new MouseEvent('dblclick', { bubbles: true, cancelable: true, view: window });
            arguments[0].dispatchEvent(evt);
        """, this)
    }
    fun WebElement.setValueAndPressKey(value: String, key: String) {
        (document as JavascriptExecutor).executeScript("arguments[0].value = arguments[1];", this, value)
        (document as JavascriptExecutor).executeScript("""
            var event = new KeyboardEvent('keydown', {
                key: arguments[1],
                bubbles: true,
                cancelable: true
            });
            arguments[0].dispatchEvent(event);
        """, this, key)
    }
    Given("TodoCardElement is rendered") {
        val wait = WebDriverWait(document, Duration.ofSeconds(10))

        When("Todo cards are displayed") {
            val todoItems = wait.until(
                presenceOfAllElementsLocatedBy(By.cssSelector(".todo-item"))
            )

            Then("It should render 3 todo items") {
                todoItems.size shouldBe 3
            }

            Then("First todo should show correct title") {
                val firstTodo = todoItems[0]
                val title = firstTodo.findElement(By.cssSelector(".todo-title"))
                title.text shouldBe "Buy groceries"
            }

            Then("First todo should not be completed") {
                val firstTodo = todoItems[0]
                val classes = firstTodo.getDomAttribute("class")
                classes shouldNotContain "completed"
            }

            Then("Second todo should be completed") {
                val secondTodo = todoItems[1]
                val classes = secondTodo.getDomAttribute("class")
                classes shouldContain "completed"
            }

            Then("Second todo checkbox should be selected") {
                val secondTodo = todoItems[1]
                val checkbox = secondTodo.findElement(By.cssSelector(".toggle"))
                checkbox.getDomProperty("checked") shouldBe "true"
            }
        }

        When("User toggles checkbox") {
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val firstTodo = todoItems[0]
            val checkbox = firstTodo.findElement(By.cssSelector(".toggle"))

            checkbox.click()

            Then("Todo should become completed") {
                val classes = firstTodo.getDomAttribute("class")
                classes shouldContain "completed"
            }

            And("Toggling again should make it active") {
                checkbox.click()
                val classes = firstTodo.getDomAttribute("class")
                classes shouldNotContain "completed"
            }
        }

        When("User double-clicks title to edit") {
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val firstTodo = todoItems[0]
            val titleLabel = firstTodo.findElement(By.cssSelector(".todo-title"))

            val actions = Actions(document)
            actions.doubleClick(titleLabel).perform()

            Then("It should enter editing mode") {
                val classes = firstTodo.getDomAttribute("class")
                classes shouldContain "editing"
            }

            Then("Edit input should be visible") {
                val editInput = firstTodo.findElement(By.cssSelector(".edit"))
                editInput.isDisplayed shouldBe true
            }
        }

        When("User edits title and presses Enter") {
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val firstTodo = todoItems[0]
            val titleLabel = firstTodo.findElement(By.cssSelector(".todo-title"))

            // Enter edit mode
            titleLabel.doubleClick()
            // Edit and save
            val editInput = firstTodo.findElement(By.cssSelector(".edit"))
            editInput.setValueAndPressKey("Updated task title", "Enter")

            Then("Title should be updated") {
                val updatedTitle = firstTodo.findElement(By.cssSelector(".todo-title"))
                updatedTitle.text shouldBe "Updated task title"
            }

            Then("Should exit editing mode") {
                val classes = firstTodo.getDomAttribute("class")
                classes shouldNotContain "editing"
            }
        }

        When("User presses Escape while editing") {
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val secondTodo = todoItems[1]
            val titleLabel = secondTodo.findElement(By.cssSelector(".todo-title"))
            val originalTitle = titleLabel.text

            // Enter edit mode
            titleLabel.doubleClick()

            // Edit but cancel
            val editInput = secondTodo.findElement(By.cssSelector(".edit"))
            editInput.setValueAndPressKey("This will be cancelled", "Escape")

            Then("Should keep original title") {
                val currentTitle = secondTodo.findElement(By.cssSelector(".todo-title"))
                currentTitle.text shouldBe originalTitle
            }

            Then("Should exit editing mode") {
                val classes = secondTodo.getDomAttribute("class")
                classes shouldNotContain "editing"
            }
        }

        When("User tries to save invalid title") {
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val firstTodo = todoItems[0]
            val titleLabel = firstTodo.findElement(By.cssSelector(".todo-title"))
            val originalTitle = titleLabel.text

            // Enter edit mode
            titleLabel.doubleClick()

            // Try invalid input
            val editInput = firstTodo.findElement(By.cssSelector(".edit"))
            editInput.setValueAndPressKey("X", "Enter")

            Then("Should keep original title") {
                val currentTitle = firstTodo.findElement(By.cssSelector(".todo-title"))
                currentTitle.text shouldBe originalTitle
            }
        }

        When("User clicks destroy button") {
            val initialCount = document.findElements(By.cssSelector(".todo-item")).size
            val todoItems = document.findElements(By.cssSelector(".todo-item"))
            val firstTodo = todoItems[0]
            val destroyBtn = firstTodo.findElement(By.cssSelector(".destroy"))

            destroyBtn.click()

            Then("Todo should be removed") {
                val currentCount = document.findElements(By.cssSelector(".todo-item")).size
                currentCount shouldBe (initialCount - 1)
            }
        }
    }
})