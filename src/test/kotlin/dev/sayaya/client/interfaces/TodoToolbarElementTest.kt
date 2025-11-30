package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@GwtHtml("src/test/webapp/todoToolbarElementTest.html")
class TodoToolbarElementTest : GwtTestSpec({

    Given("StateFilterElement is rendered") {
        val wait = WebDriverWait(document, Duration.ofSeconds(10))

        When("the filter buttons are displayed") {
            val buttons = wait.until(
                presenceOfAllElementsLocatedBy(
                    By.cssSelector(".todo-toolbar .filter-button")
                )
            )

            Then("it should render 4 buttons: All, Active, Completed, Clear Completed") {
                buttons.shouldHaveSize(4)
                buttons[0].text shouldBe "All"
                buttons[1].text shouldBe "Active"
                buttons[2].text shouldBe "Completed"
                buttons[3].text shouldBe "Clear Completed"
            }

            Then("initially 'All' should be selected") {
                val allClasses = buttons[0].getDomAttribute("class")
                val activeClasses = buttons[1].getDomAttribute("class")
                val completedClasses = buttons[2].getDomAttribute("class")

                allClasses shouldContain "selected"
                activeClasses shouldNotContain "selected"
                completedClasses shouldNotContain "selected"
            }
        }

        When("user clicks 'Active' filter") {
            val buttons = document.findElements(
                By.cssSelector(".todo-toolbar .filter-button")
            )
            val allBtn = buttons[0]
            val activeBtn = buttons[1]
            val completedBtn = buttons[2]

            activeBtn.click()

            Then("'Active' should be selected and others not selected") {
                allBtn.getDomAttribute("class") shouldNotContain "selected"
                activeBtn.getDomAttribute("class") shouldContain "selected"
                completedBtn.getDomAttribute("class") shouldNotContain "selected"
            }
        }

        When("user clicks 'Completed' filter") {
            val buttons = document.findElements(
                By.cssSelector(".todo-toolbar .filter-button")
            )
            val allBtn = buttons[0]
            val activeBtn = buttons[1]
            val completedBtn = buttons[2]

            completedBtn.click()

            Then("'Completed' should be selected and others not selected") {
                allBtn.getDomAttribute("class") shouldNotContain "selected"
                activeBtn.getDomAttribute("class") shouldNotContain "selected"
                completedBtn.getDomAttribute("class") shouldContain "selected"
            }
        }

        When("user clicks 'All' filter again") {
            val buttons = document.findElements(
                By.cssSelector(".todo-toolbar .filter-button")
            )
            val allBtn = buttons[0]
            val activeBtn = buttons[1]
            val completedBtn = buttons[2]

            allBtn.click()

            Then("'All' should be selected again") {
                allBtn.getDomAttribute("class") shouldContain "selected"
                activeBtn.getDomAttribute("class") shouldNotContain "selected"
                completedBtn.getDomAttribute("class") shouldNotContain "selected"
            }
        }
    }
})