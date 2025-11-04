package model.DAO


import model.Entity.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CompetenciaDAO {

    private final Connection conexao

    CompetenciaDAO(Connection conexao) {
        this.conexao = conexao
    }

    void criarCompetencia(Competencia competencia) {
        String sql = "INSERT INTO competencias (nome) VALUES (?)"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, competencia.nome)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

    void associarCompetenciaVaga(int vaga_id, int comp_id) {
        String sql = "INSERT INTO vagas_competencias (id_vaga, id_competencia) VALUES (?, ?)"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setInt(1, vaga_id)
            comando.setInt(2, comp_id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

    void associarCompetenciaCandidato(String cpf, int id) {
        String sql = "INSERT INTO candidatos_competencias (cpf_candidato, id_competencia) VALUES (?, ?)"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cpf)
            comando.setInt(2, id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }


     List<Competencia> buscarCompetencias() {
        String sql = "SELECT * FROM competencias"
        ArrayList<Competencia> competencias = new ArrayList<Competencia>()

        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            ResultSet competenciasResult = comando.executeQuery()
            while (competenciasResult.next()) {
                competencias << new Competencia(
                        id: competenciasResult.getInt("id"),
                        nome: competenciasResult.getString("nome")
                )
            }
            fecharRecursos(comando, competenciasResult)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
        
        return competencias
    }

    List<Competencia> buscarPorCandidato(String cpf) {
        String sql = """
            SELECT c.id, c.nome
            FROM competencias c
            JOIN candidatos_competencias cc ON cc.id_competencia = c.id
            WHERE cc.cpf_candidato = ?
        """
        List<Competencia> competencias = []
        PreparedStatement comando = conexao.prepareStatement(sql)
        comando.setString(1, cpf)
        ResultSet competenciasResult = comando.executeQuery()

        while (competenciasResult.next()) {
            competencias << new Competencia(
                    id: competenciasResult.getInt("id"),
                    nome: competenciasResult.getString("nome")
            )
        }

        competenciasResult.close()
        comando.close()
        return competencias
    }

    List<Competencia> buscarPorVaga(int idVaga) {
        String sql = """
            SELECT c.id, c.nome
            FROM competencias c
            JOIN vagas_competencias vc ON vc.id_competencia = c.id
            WHERE vc.id_vaga = ?
        """
        List<Competencia> competencias = []
        PreparedStatement comando = conexao.prepareStatement(sql)
        comando.setInt(1, idVaga)
        ResultSet competenciasResult = comando.executeQuery()

        while (competenciasResult.next()) {
            competencias << criarObjetoCompetencia(competenciasResult)
        }

        fecharRecursos(comando, competenciasResult)
        return competencias
    }
    
    private static Competencia criarObjetoCompetencia(ResultSet competenciasResult) {
        return new Competencia(
                id: competenciasResult.getInt("id"),
                nome: competenciasResult.getString("nome")
        )
    }

     void atualizarCompetencia(Competencia competencia) {
        String sql = "UPDATE competencias SET nome = ? WHERE id = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, competencia.nome)
            comando.setInt(2, competencia.id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }
    }

     void deletarCompetencia(int id) {
        String sql = "DELETE FROM competencias WHERE id = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setInt(1, id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }

    }
    
    static void fecharRecursos(PreparedStatement statement, ResultSet competenciasResult = null) {
        statement.close()
        competenciasResult?.close()
    }
}
