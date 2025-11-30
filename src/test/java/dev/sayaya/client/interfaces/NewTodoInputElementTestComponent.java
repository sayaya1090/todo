package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoListStore;

import javax.inject.Singleton;

@Singleton
@dagger.Component
interface NewTodoInputElementTestComponent {
    TodoListStore store();
    static NewTodoInputElementTestComponent create() {
        return DaggerNewTodoInputElementTestComponent.create();
    }
}
