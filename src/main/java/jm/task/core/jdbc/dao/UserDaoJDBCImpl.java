package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    //создать таблицу
    public void createUsersTable() {

        try(Connection connection = Util.getConnection();
            Statement stmnt = connection.createStatement()) {
            stmnt.execute("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(20), lastName VARCHAR(20), age TINYINT)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// удаляем таблицу
    public void dropUsersTable() {

        try(Connection connection = Util.getConnection();
            Statement stmnt = connection.createStatement()){
            stmnt.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //Добавление User в таблицу

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO users(firstName, lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    //удаление юзера по id

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;


    }
    //вывод всех данных из таблицы

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                users.add(new User(name, lastName, age));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  users;
    }
    //Очистка содержания таблицы

    public void cleanUsersTable() {
        try( Connection connection = Util.getConnection();
        Statement stmnt = connection.createStatement()) {
            stmnt.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;


    }
}
