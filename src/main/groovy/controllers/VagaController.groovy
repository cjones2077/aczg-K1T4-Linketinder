package controllers

import DAO.EmpresaDAO
import DAO.VagaDAO
import org.Entity.Empresa
import org.Entity.Vaga

class VagaController {
    VagaDAO vagaDAO = new VagaDAO()
    EmpresaDAO empresaDAO = new EmpresaDAO()
    EmpresaController empresaController = new EmpresaController()
    void cadastrarVaga() {
        def reader = System.in.newReader()
        List<Empresa> empresas = empresaDAO.listarTodasEmpresas()
        empresaController.listarEmpresas()
        print "Escolha a empresa dona da vaga (índice): "
        int idx = reader.readLine().toInteger() - 1
        Empresa empresa = empresas.get(idx)

        print "Nome da vaga: "; String nome = reader.readLine()
        print "Descrição: "; String descricao = reader.readLine()
        print "Local da vaga: "; String local = reader.readLine()

        def vaga = new Vaga(nome: nome, descricao: descricao, local_vaga: local, empresa_cnpj: empresa.cnpj)
        vagaDAO.criarVaga(vaga)
        println "\nVaga cadastrada com sucesso!"
    }

    void listarVagas() {
        println "\n-- Vagas --"
        vagaDAO.listarTodasVagas().eachWithIndex { v, idx ->
            println "${idx + 1} - ${v.nome} | Empresa CNPJ: ${v.empresa_cnpj} | Local: ${v.local_vaga}"
        }
    }

    void atualizarVaga() {
        def reader = System.in.newReader()
        List<Vaga> vagas = vagaDAO.listarTodasVagas()
        listarVagas()
        print "Escolha a vaga a atualizar (índice): "
        int idx = reader.readLine().toInteger() - 1
        Vaga vaga = vagas.get(idx)

        print "Nome [${vaga.nome}]: "; String nome = reader.readLine(); if (nome) vaga.nome = nome
        print "Descrição [${vaga.descricao}]: "; String desc = reader.readLine(); if (desc) vaga.descricao = desc
        print "Local [${vaga.local_vaga}]: "; String local = reader.readLine(); if (local) vaga.local_vaga = local

        vagaDAO.atualizarVaga(vaga)
        println "\nVaga atualizada com sucesso!"
    }

    void deletarVaga() {
        def reader = System.in.newReader()
        List<Vaga> vagas = vagaDAO.listarTodasVagas()
        listarVagas()
        print "Escolha a vaga a deletar (índice): "
        int idx = reader.readLine().toInteger() - 1
        Vaga vaga = vagas.get(idx)
        vagaDAO.deletarVaga(vaga.id)
        println "Vaga deletada!"
    }
}
