package com.vityarthi.studyhub.dao;

import java.sql.*;

public final class Database {
    private static final String URL = "jdbc:h2:mem:studyhub;DB_CLOSE_DELAY=-1";
    private Database() { }
    public static Connection connect() throws SQLException { return DriverManager.getConnection(URL, "sa", ""); }
    public static void initialize() {
        String courses = "CREATE TABLE IF NOT EXISTS courses (id BIGINT AUTO_INCREMENT PRIMARY KEY, code VARCHAR(20) UNIQUE NOT NULL, name VARCHAR(120) NOT NULL, credits INT NOT NULL)";
        String tasks = "CREATE TABLE IF NOT EXISTS tasks (id BIGINT AUTO_INCREMENT PRIMARY KEY, course_id BIGINT NOT NULL, title VARCHAR(200) NOT NULL, due_date DATE NOT NULL, estimated_minutes INT NOT NULL, status VARCHAR(20) NOT NULL, FOREIGN KEY(course_id) REFERENCES courses(id))";
        try (Connection c = connect(); Statement s = c.createStatement()) { s.execute(courses); s.execute(tasks); }
        catch (SQLException e) { throw new IllegalStateException("Could not initialize database", e); }
    }
}
