package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDaoJDBC = new UserDaoJDBCImpl();

    @Override
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBC.saveUser(name, lastName, age);
        System.out.println("Пользователь сохранен");
    }

    @Override
    public void removeUserById(long id) {
        userDaoJDBC.removeUserById(id);
        System.out.println("Пользователь удален");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDaoJDBC.getAllUsers();
        users.stream().forEach(a -> System.out.println(a.toString()));
        return users;
    }

    @Override
    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
        System.out.println("Таблица очищена");
    }
}
