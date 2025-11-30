package dev.sayaya.client;

import dev.sayaya.client.interfaces.NewTodoInputElement;
import dev.sayaya.client.interfaces.TodoToolbarElement;
import dev.sayaya.client.interfaces.TodoCardListElement;
import dev.sayaya.client.interfaces.ToggleAllButton;

import javax.inject.Singleton;

@Singleton
@dagger.Component
interface Component {
    NewTodoInputElement newTodo();
    ToggleAllButton toggle();
    TodoCardListElement list();
    TodoToolbarElement toolbar();
    static Component create() {
        return DaggerComponent.create();
    }
}
