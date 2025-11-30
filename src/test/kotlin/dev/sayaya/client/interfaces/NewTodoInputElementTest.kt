package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@GwtHtml("src/test/webapp/newTodoInputElementTest.html")
class NewTodoInputElementTest : GwtTestSpec({
    Given("NewTodoInputElement is rendered") {
        val wait = WebDriverWait(document, Duration.ofSeconds(10))
        val input = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector(".new-todo"))
        )
        When("User types 'Task via Selenium' and presses Enter") {
            input.sendKeys("Task via Selenium")
            input.sendKeys(Keys.ENTER)

            Then("It should add the task to TodoList") {
                document.shouldContainLog("TODO ADDED: Task via Selenium")
            }

            Then("The input field should be empty") {
                val value = input.getDomProperty("value")
                value shouldBe ""
            }
        }
    }
})