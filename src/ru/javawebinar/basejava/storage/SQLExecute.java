package ru.javawebinar.basejava.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLExecute<T> {
   T  execute(PreparedStatement preparedStatement) throws SQLException;

}
