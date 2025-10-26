package Persistence
@Grab(group='org.postgresql', module='postgresql', version='42.6.0')

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class DBConnection {
    static final url = 'jdbc:postgresql://localhost:5432/linketinder'
    static final user = 'admin'
    static final password = 'admin'
    static public Connection conexao = null;

    static Connection abrirConexao() {
        conexao = DriverManager.getConnection(url, user, password)
    }
    static void fecharConexao(){
        conexao.close()
    }

}
