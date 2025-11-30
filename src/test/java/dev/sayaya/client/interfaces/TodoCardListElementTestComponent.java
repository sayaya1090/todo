package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoFiltered;
import dev.sayaya.client.usecase.TodoListStore;

import javax.inject.Singleton;

@Singleton
@dagger.Component
interface TodoCardListElementTestComponent {
    TodoCardElement.TodoCardElementFactory factory();
    TodoListStore store();
    TodoFiltered filtered();
    static TodoCardListElementTestComponent create() {
        return DaggerTodoCardListElementTestComponent.create();
    }
}
