package Factory.Connections

import java.sql.Connection
import java.sql.DriverManager

class MySQLConnection implements DBConnection {
    static final url = 'jdbc:postgresql://localhost:5432/linketinder'
    static final user = 'admin'
    static final password = 'admin'
    static public Connection conexao = null;
    private static MySQLConnection instancia

    private MySQLConnection() {}

    static MySQLConnection getInstancia() {
        if (instancia == null) {
            instancia = new MySQLConnection()
        }
        return instancia
    }

    @Override
     void abrirConexao() {
        conexao = DriverManager.getConnection(url, user, password)
    }

    @Override
    void fecharConexao() {
        conexao.close()
    }

    @Override
    Connection getConexao() {
        return conexao
    }
}
