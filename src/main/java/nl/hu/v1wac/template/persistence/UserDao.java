package nl.hu.v1wac.template.persistence;

public interface UserDao {
    String findRoleForUser(String name, String pass);
}
