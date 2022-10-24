package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.service.UserService;
public class Main {
    public static void main(String[] args) {
       // Util.getConnection();
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Dubov", (byte)33);
        userService.saveUser("Vadim", "Enikeev", (byte)27);
        userService.saveUser("Dina", "Zainet", (byte)26);
        userService.saveUser("Dima", "Markov", (byte)23);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
