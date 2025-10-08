package Persistence
@Grab(group='org.postgresql', module='postgresql', version='42.6.0')

import java.sql.Connection
import java.sql.DriverManager

class DBConnection {
    static final url = 'jdbc:postgresql://localhost:5432/linketinder'
    static final user = 'admin'
    static final password = 'admin'
    public static Connection conexao = null;

    static Connection conectar() {
        conexao = DriverManager.getConnection(url, user, password)
    }
    static void fechaConexao(){
        conexao.close()
    }
}
