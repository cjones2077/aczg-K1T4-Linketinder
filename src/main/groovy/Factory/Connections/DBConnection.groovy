package Factory.Connections

import java.sql.Connection

interface DBConnection {
     void abrirConexao()
     void fecharConexao()
    Connection getConexao()
}