package com.github.ymikevich.service.assist;

import com.github.ymikevich.exceptions.DatabaseOperationException;
import com.github.ymikevich.service.connection.pool.HikariCPDatasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseRequestExecutor {

    static <R> R execute(DatabaseRequestFunction<Connection, R> function) {
        var connection = HikariCPDatasource.getConnection();

        try {
            connection.setAutoCommit(false);
            var result = function.apply(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            rollbackConnection(connection, e);
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseOperationException("Unexpected query execution error while closing connection", ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseOperationException("Error while closing the connection after commit", ex);
            }
        }
        return null;
    }

    static void execute(DatabaseRequestConsumer<Connection> consumer) {
        var connection = HikariCPDatasource.getConnection();
        try {
            connection.setAutoCommit(false);
            consumer.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            rollbackConnection(connection, e);
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseOperationException("Unexpected query execution error while closing connection", ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseOperationException("Error while closing the connection after commit", ex);
            }
        }
    }

    static void rollbackConnection(Connection connection, SQLException exception) {
        try {
            connection.rollback();
            throw new DatabaseOperationException("Query execution error, rollback successful", exception);
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Unexpected query execution error during rollback operation", exception);
        }
    }
}
