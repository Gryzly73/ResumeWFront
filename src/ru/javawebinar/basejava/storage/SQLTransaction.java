package ru.javawebinar.basejava.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLTransaction<T> {
    T executeTransaction(Connection connection) throws SQLException;
}
