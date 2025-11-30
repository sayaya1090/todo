package dev.sayaya.client.interfaces;

import dev.sayaya.client.usecase.TodoStore;

import javax.inject.Singleton;

@Singleton
@dagger.Component
interface TodoCardElementTestComponent {
    TodoStore.TodoStoreFactory storeFactory();
    TodoCardElement.TodoCardElementFactory elementFactory();
    static TodoCardElementTestComponent create() {
        return DaggerTodoCardElementTestComponent.create();
    }
}
