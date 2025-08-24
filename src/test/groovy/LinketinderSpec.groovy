import spock.lang.Specification

class LinketinderSpec extends Specification {
    def "deve criar candidato corretamente"() {
        given:
            def linketinder = new Linketinder()
        when:
        linketinder.criarCandidato(
                "João", "joao@mail.com", "90000-000", "Dev Backend",
                "11111111111", 25, "RS", ["Java", "Spring"]
        )
        then:
        linketinder.candidatos.size() == 1
        linketinder.candidatos.get(0).nome == "João"
        linketinder.candidatos.get(0).competencias.contains("Spring")
    }

    def "deve criar empresa corretamente"() {
        given:
        def linketinder = new Linketinder()
        when:
        linketinder.criarEmpresa(
                "TechRS", "contato@techrs.com", "90000-000", "Consultoria em TI",
                "11111111000101", "Brasil", "RS", ["Java", "Spring"]
        )
        then:
        linketinder.empresas.size() == 1
        linketinder.empresas.get(0).nome == "TechRS"
        linketinder.empresas.get(0).competencias.contains("Spring")
    }
}
