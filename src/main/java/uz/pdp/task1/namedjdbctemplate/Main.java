package uz.pdp.task1.namedjdbctemplate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("ioc-settings.xml");
        TodoDAO todoDAO = container.getBean(TodoDAO.class);
        Todo todo = Todo.builder()
                .id(3)
                .title("March")
                .priority("MEDIUM")
                .build();
//        System.out.println(todoDAO.saveReturnId(todo));
//        System.out.println(todoDAO.findTodoById(1));
//        System.out.println(todoDAO.findTodoByTitle("March"));
//        todoDAO.findTodoAll().forEach(System.out::println);
//        todoDAO.updateTodo(todo);
        todoDAO.deleteTodo(1);
    }
}