package factory.connections
@Grab(group='org.postgresql', module='postgresql', version='42.6.0')

import java.sql.Connection
import java.sql.DriverManager

class PostgreSQLConnection implements DBConnection {
    static final url = 'jdbc:postgresql://localhost:5432/linketinder'
    static final user = 'admin'
    static final password = 'admin'
    static public Connection conexao = null;
    private static PostgreSQLConnection instancia

    private PostgreSQLConnection() {}

    static PostgreSQLConnection getInstancia() {
        if (instancia == null) {
            instancia = new PostgreSQLConnection()
        }
        return instancia
    }

    @Override
    void abrirConexao() {
        conexao = DriverManager.getConnection(url, user, password)
    }

    @Override
    void fecharConexao(){
        conexao.close()
    }

    @Override
    Connection getConexao() {
        return conexao
    }
}
