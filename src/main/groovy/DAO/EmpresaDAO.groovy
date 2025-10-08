package DAO

import Persistence.DBConnection

import org.Entity.Empresa

import java.sql.PreparedStatement
import java.sql.ResultSet

class EmpresaDAO {
    static void criarEmpresa(Empresa empresa) {
        String sql = """INSERT INTO empresas
            (cnpj, nome, email, data_criacao, pais, cep, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, empresa.cnpj)
        comando.setString(2, empresa.nome)
        comando.setString(3, empresa.email)
        comando.setDate(4, new java.sql.Date(empresa.data_criacao.time))
        comando.setString(5, empresa.pais)
        comando.setString(6, empresa.cep)
        comando.setString(7, empresa.descricao)
        comando.setString(8, empresa.senha)
        comando.executeUpdate()
        comando.close()
    }

    static List<Empresa> listarTodasEmpresas() {
        String sql = "SELECT * FROM empresas"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        ResultSet rs = comando.executeQuery()
        def lista = []
        while(rs.next()) {
            lista << new Empresa(
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
        rs.close()
        comando.close()
        return lista
    }

    static void atualizarEmpresa(Empresa empresa) {
        String sql = """UPDATE empresas SET 
                            nome = ?, email = ?, data_criacao = ?, pais = ?, cep = ?, descricao = ?, senha = ? 
                         WHERE cnpj = ?"""
        def comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, empresa.nome)
        comando.setString(2, empresa.email)
        comando.setDate(3, new java.sql.Date(empresa.data_criacao.time))
        comando.setString(4, empresa.pais)
        comando.setString(5, empresa.cep)
        comando.setString(6, empresa.descricao)
        comando.setString(7, empresa.senha)
        comando.setString(8, empresa.cnpj)
        comando.executeUpdate()
        comando.close()
    }

    static void deletarEmpresa(cnpj) {
        String sql = "DELETE FROM empresas WHERE cnpj = ?"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, cnpj)
        comando.executeUpdate()
        comando.close()
    }
}
