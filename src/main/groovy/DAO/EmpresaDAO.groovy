package DAO

import org.Entity.Empresa
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class EmpresaDAO {

    private final Connection conexao

    EmpresaDAO(Connection conexao) {
        this.conexao = conexao
    }

    void criarEmpresa(Empresa empresa) {
        String sql = """INSERT INTO empresas
            (cnpj, nome, email, data_criacao, pais, cep, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, empresa.cnpj)
            comando.setString(2, empresa.nome)
            comando.setString(3, empresa.email)
            comando.setDate(4, new java.sql.Date(empresa.data_criacao.time))
            comando.setString(5, empresa.pais)
            comando.setString(6, empresa.cep)
            comando.setString(7, empresa.descricao)
            comando.setString(8, empresa.senha)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    List<Empresa> buscarEmpresas() {
        String sql = "SELECT * FROM empresas"
        List<Empresa> empresas = []
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            ResultSet rs = comando.executeQuery()
            while (rs.next()) {
                empresas << criarObjetoEmpresa(rs)
            }
            fecharRecursos(comando, rs)
        } catch (SQLException e) {
            e.printStackTrace()
        }
        return empresas
    }

    private static Empresa criarObjetoEmpresa(ResultSet rs) {
        return new Empresa(
                cnpj: rs.getString("cnpj"),
                nome: rs.getString("nome"),
                email: rs.getString("email"),
                data_criacao: rs.getDate("data_criacao"),
                pais: rs.getString("pais"),
                cep: rs.getString("cep"),
                descricao: rs.getString("descricao"),
                senha: rs.getString("senha")
        )
    }

    void atualizarEmpresa(Empresa empresa) {
        String sql = """UPDATE empresas SET 
                            nome = ?, email = ?, data_criacao = ?, pais = ?, cep = ?, descricao = ?, senha = ? 
                         WHERE cnpj = ?"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, empresa.nome)
            comando.setString(2, empresa.email)
            comando.setDate(3, new java.sql.Date(empresa.data_criacao.time))
            comando.setString(4, empresa.pais)
            comando.setString(5, empresa.cep)
            comando.setString(6, empresa.descricao)
            comando.setString(7, empresa.senha)
            comando.setString(8, empresa.cnpj)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    void deletarPorCnpj(String cnpj) {
        String sql = "DELETE FROM empresas WHERE cnpj = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cnpj)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    static void fecharRecursos(PreparedStatement statement, ResultSet rs = null) {
        try {
            statement?.close()
            rs?.close()
        } catch (SQLException ignored) {}
    }
}
