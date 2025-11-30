package dev.sayaya.client.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class TodoTest : StringSpec({

    "create() should return a valid Todo with default state" {
        val todo = Todo.create("id", "Task 1")

        todo.id shouldNotBe null
        todo.title shouldBe "Task 1"
        todo.state shouldBe State.ACTIVE
    }

    "constructor should throw exception when required fields are null" {
        shouldThrow<IllegalArgumentException> { Todo(null, "Title", State.ACTIVE) }
        shouldThrow<IllegalArgumentException> { Todo("id", null, State.ACTIVE) }
        shouldThrow<IllegalArgumentException> { Todo("id", "Title", null) }
    }

    "constructor should throw exception when title is less than 2 trimmed characters" {
        shouldThrow<IllegalArgumentException> { Todo("id", "", State.ACTIVE) }
        shouldThrow<IllegalArgumentException> { Todo("id", "A", State.ACTIVE) }
        shouldThrow<IllegalArgumentException> { Todo("id", " A ", State.ACTIVE) }
    }

    "constructor should accept title with exactly 2 characters" {
        val todo = Todo("id", "AB", State.ACTIVE)
        todo.title shouldBe "AB"
    }

    "constructor should accept title with whitespace if trimmed length is at least 2" {
        val todo = Todo("id", "  AB  ", State.ACTIVE)
        todo.title shouldBe "  AB  "
    }

    "withTitle() should update only the title" {
        val todo = Todo.create("id", "Old Title")
        val updated = todo.withTitle("New Title")

        updated.title shouldBe "New Title"
        updated.id shouldBe todo.id
        updated.state shouldBe todo.state
    }
})