package dev.sayaya.client.interfaces

import dev.sayaya.gwt.test.GwtHtml
import dev.sayaya.gwt.test.GwtTestSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain

@GwtHtml("src/test/webapp/todoToolbarElementTest.html")
class TodoToolbarElementTest : GwtTestSpec({
    Given("StateFilterElement is rendered") {
        When("the filter buttons are displayed") {
            val buttons = page.locator(".todo-toolbar .filter-button")

            Then("it should render 4 buttons: All, Active, Completed, Clear Completed") {
                buttons.count() shouldBe 4
                buttons.nth(0).innerText() shouldBe "All"
                buttons.nth(1).innerText() shouldBe "Active"
                buttons.nth(2).innerText() shouldBe "Completed"
                buttons.nth(3).innerText() shouldBe "Clear Completed"
            }

            Then("initially 'All' should be selected") {
                val allClasses = buttons.nth(0).getAttribute("class")
                val activeClasses = buttons.nth(1).getAttribute("class")
                val completedClasses = buttons.nth(2).getAttribute("class")

                allClasses shouldContain "selected"
                activeClasses shouldNotContain "selected"
                completedClasses shouldNotContain "selected"
            }
        }

        When("user clicks 'Active' filter") {
            val buttons = page.locator(".todo-toolbar .filter-button")
            val allBtn = buttons.nth(0)
            val activeBtn = buttons.nth(1)
            val completedBtn = buttons.nth(2)

            activeBtn.click()

            Then("'Active' should be selected and others not selected") {
                allBtn.getAttribute("class") shouldNotContain "selected"
                activeBtn.getAttribute("class") shouldContain "selected"
                completedBtn.getAttribute("class") shouldNotContain "selected"
            }
        }

        When("user clicks 'Completed' filter") {
            val buttons = page.locator(".todo-toolbar .filter-button")
            val allBtn = buttons.nth(0)
            val activeBtn = buttons.nth(1)
            val completedBtn = buttons.nth(2)

            completedBtn.click()

            Then("'Completed' should be selected and others not selected") {
                allBtn.getAttribute("class") shouldNotContain "selected"
                activeBtn.getAttribute("class") shouldNotContain "selected"
                completedBtn.getAttribute("class") shouldContain "selected"
            }
        }

        When("user clicks 'All' filter again") {
            val buttons = page.locator(".todo-toolbar .filter-button")
            val allBtn = buttons.nth(0)
            val activeBtn = buttons.nth(1)
            val completedBtn = buttons.nth(2)

            allBtn.click()

            Then("'All' should be selected again") {
                allBtn.getAttribute("class") shouldContain "selected"
                activeBtn.getAttribute("class") shouldNotContain "selected"
                completedBtn.getAttribute("class") shouldNotContain "selected"
            }
        }
    }
})