package ru.ivanishkin.javaee.task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseContext implements AutoCloseable {

    private final Connection connection;

    public DatabaseContext(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        Statement st = connection.createStatement();
        st.execute(nodeTableCreateStatement);
        st.execute(tagTableCreateStatement);
        connection.setAutoCommit(false);
        st.closeOnCompletion();
    }

    public Connection getConnection() {
        return connection;
    }

    private static String nodeTableCreateStatement =
//            "CREATE OR REPLACE FUNCTION create_nodetable ()\n" +
//                    "  RETURNS void AS\n" +
//                    "$func$\n" +
//                    "BEGIN\n" +
//                    "   IF EXISTS (SELECT FROM pg_catalog.pg_tables \n" +
//                    "              WHERE  schemaname = 'public'\n" +
//                    "              AND    tablename  = 'nodes') THEN\n" +
//                    "      RAISE NOTICE 'Table public.nodes already exists.';\n" +
//                    "   ELSE\n" +
                    "DROP TABLE IF EXISTS nodes CASCADE;\n" +
                    "CREATE TABLE nodes(" +
                    "id bigint primary key," +
                    "lon numeric," +
                    "lat numeric," +
                    "uid bigint," +
                    "\"user_name\" varchar);\n";// +
//                    "   END IF;\n" +
//                    "END\n" +
//                    "$func$ LANGUAGE plpgsql;\n" +
//                    "SELECT create_nodetable()";
    private static String tagTableCreateStatement =
//            "CREATE OR REPLACE FUNCTION create_tagtable ()\n" +
//                    "  RETURNS void AS\n" +
//                    "$func$\n" +
//                    "BEGIN\n" +
//                    "   IF EXISTS (SELECT FROM pg_catalog.pg_tables \n" +
//                    "              WHERE  schemaname = 'public'\n" +
//                    "              AND    tablename  = 'tags') THEN\n" +
//                    "      RAISE NOTICE 'Table public.tags already exists.';\n" +
//                    "   ELSE\n" +
                    "DROP TABLE IF EXISTS tags CASCADE;\n" +
                    "CREATE TABLE tags(" +
                    "id serial primary key," +
                    "key varchar," +
                    "value varchar," +
                    "node_id bigint);\n";// +
//                    "   END IF;\n" +
//                    "END\n" +
//                    "$func$ LANGUAGE plpgsql;" +
//                    "SELECT create_tagtable()";

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}

