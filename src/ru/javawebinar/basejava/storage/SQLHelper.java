package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper<T>  {

    private Connection connection;

    public SQLHelper(Connection connection) {
        this.connection = connection;
    }


    public <T> T execute(String str, SQLExecute<T> sqlExecute)  {

        try (PreparedStatement preparedStatement = connection.prepareStatement(str))
        {
           return sqlExecute.execute(preparedStatement);

        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), "");
        }
    }

    public <T> T executeTransaction( SQLTransaction<T> sqlTransaction)  throws SQLException
    {

        try{
            connection.setAutoCommit(false);
            T t = sqlTransaction.executeTransaction(connection);
            connection.commit();
            return t;

        } catch (SQLException e) {
            connection.rollback();
            throw new StorageException(e.getMessage(), " Откат! ");
        }
    }

    public void execute(String str) throws SQLException {
        execute(str, (PreparedStatement preparedStatement) -> {
            return preparedStatement.execute();
        });
    }
}
