package web

import app.AppContainer
import controller.CandidatoController
import model.Candidato

import javax.servlet.http.*
import javax.servlet.annotation.WebServlet
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

import com.fasterxml.jackson.databind.ObjectMapper

@WebServlet("/candidatos")
class CandidatoServlet extends HttpServlet {

    private final CandidatoController candidatoController = AppContainer.getInstancia().candidatoController

    static {
        try {
            Class.forName("org.postgresql.Driver")
            println "Driver PostgreSQL registrado com sucesso"
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
            throw new RuntimeException("Driver PostgreSQL não encontrado")
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Candidato> lista = candidatoController.buscarCandidatos()

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(lista)

        resp.getWriter().write(json)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        def candidato = new Candidato(
                cpf: req.getParameter("cpf"),
                nome: req.getParameter("nome"),
                sobrenome: req.getParameter("sobrenome"),
                email: req.getParameter("email"),
                estado: req.getParameter("estado"),
                descricao: req.getParameter("descricao"),
                cep: req.getParameter("cep"),
                senha: req.getParameter("senha"),
                data_nascimento: java.sql.Date.valueOf(req.getParameter("data_nascimento"))
        )

        try {
            candidatoController.cadastrarCandidato(candidato)
            resp.getWriter().write("Candidato criado!")
        } catch (SQLException e) {
            e.printStackTrace()
            resp.getWriter().write("Erro ao acessar o banco: " + e.message)
        }
    }
}
