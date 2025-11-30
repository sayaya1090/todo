package dev.sayaya.client.usecase;


import javax.inject.Singleton;

@Singleton
@dagger.Component
interface TodoListStoreTestComponent {
    TodoStore.TodoStoreFactory storeFactory();
    static TodoListStoreTestComponent create() {
        return DaggerTodoListStoreTestComponent.create();
    }
}
