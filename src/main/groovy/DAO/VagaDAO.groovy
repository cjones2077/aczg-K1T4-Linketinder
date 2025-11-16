package DAO


import model.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VagaDAO {
    private final Connection conexao
    private final CompetenciaDAO competenciaDAO

    VagaDAO(Connection conexao) {
        this.conexao = conexao
        this.competenciaDAO = new CompetenciaDAO(conexao)
    }

    void criarVaga(Vaga vaga) {
        String sql = """INSERT INTO vagas (nome, descricao, local_vaga, empresa_cnpj)
                        VALUES (?, ?, ?, ?)"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, vaga.nome)
            comando.setString(2, vaga.descricao)
            comando.setString(3, vaga.local_vaga)
            comando.setString(4, vaga.empresa_cnpj)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

    List<Vaga> buscarVagas() {
        String sql = "SELECT * FROM vagas"
        ArrayList<Vaga> vagas = new ArrayList<>()
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            ResultSet vagasResult = comando.executeQuery()

            while (vagasResult.next()) {
                vagas << criarObjetoVaga(vagasResult)
            }
            fecharRecursos(comando, vagasResult)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
        return vagas
    }

    private Vaga criarObjetoVaga(ResultSet vagasResult) {
        Vaga vaga = new Vaga(
                id: vagasResult.getInt("id"),
                nome: vagasResult.getString("nome"),
                descricao: vagasResult.getString("descricao"),
                local_vaga: vagasResult.getString("local_vaga"),
                empresa_cnpj: vagasResult.getString("empresa_cnpj")
        )
        vaga.competencias = competenciaDAO.buscarPorVaga(vaga.id)
        return vaga
    }

    void atualizarVaga(Vaga vaga) {
        String sql = """UPDATE vagas SET nome = ?, descricao = ?, local_vaga = ?, empresa_cnpj = ?
                        WHERE id = ?"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, vaga.nome)
            comando.setString(2, vaga.descricao)
            comando.setString(3, vaga.local_vaga)
            comando.setString(4, vaga.empresa_cnpj)
            comando.setInt(5, vaga.id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

    void deletarPorID(int id) {
        String sql = "DELETE FROM vagas WHERE id = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setInt(1, id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

    private static void fecharRecursos(PreparedStatement statement, ResultSet rs = null) {
        statement.close()
        rs?.close()
    }
}
