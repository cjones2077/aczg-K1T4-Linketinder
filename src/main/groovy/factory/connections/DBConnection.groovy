package factory.connections

import java.sql.Connection

interface DBConnection {
     void abrirConexao()
     void fecharConexao()
    Connection getConexao()
}