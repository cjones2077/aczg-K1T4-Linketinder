package web

import app.AppContainer
import controller.CandidatoController
import model.Candidato

import javax.servlet.http.*
import javax.servlet.annotation.WebServlet
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
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String body = sb.toString();

        ObjectMapper mapper = new ObjectMapper();
        Candidato candidato = mapper.readValue(body, Candidato.class)

        try {
            candidatoController.cadastrarCandidato(candidato);
            resp.getWriter().write("{\"status\":\"ok\", \"msg\":\"Candidato criado!\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("{\"status\":\"erro\", \"msg\":\"" + e.getMessage() + "\"}");
        }
    }
}
