import { atualizarGraficoCompetencias } from "../graphs/competencias"
import type { Candidato } from "../models/candidato"
import type { Empresa } from "../models/empresa"

export function criarItemListaCandidato(candidato: Candidato, privado: boolean = true): HTMLLIElement {
  const li = document.createElement('li')
    let texto = ""
    if(privado)
      texto += "Nome do Candidato: **********<br>"
    else
      texto += `Nome do Candidato: ${candidato.nome};<br>`
    texto += `Formação: ${candidato.formacao}; Competências: ${candidato.competencias};`
    texto += `<div class="botoes">
              <button class="botoes_acoes excluir_candidato">
                <i class="fa-solid fa-xmark"></i>
              </button>
            </div>`
    li.innerHTML = texto
    return li;
}

export function criarItemListaEmpresa(empresa: Empresa, privado: boolean = true): HTMLLIElement {
  const li = document.createElement('li');
    let texto = ""

    texto += `Nome da Vaga: ${empresa.nome_vaga}<br>`
    if(privado)
      texto += "Nome da Empresa: **********; CNPJ: **********; "
    else
      texto += `Nome da Empresa: ${empresa.nome}; CNPJ: ${empresa.cnpj}`
    texto += `Competências: ${empresa.competencias};`
    texto += `<div class="botoes">
              <button class="botoes_acoes excluir_empresa">
                <i class="fa-solid fa-xmark"></i>
              </button>
            </div>`
    li.innerHTML = texto
    return li;
}

// atualizar dom
export function atualizaListaCandidatos(listaCandidatos: HTMLUListElement, candidatos: Candidato[]) {
  listaCandidatos.innerHTML = ""
  candidatos.forEach(c => listaCandidatos.appendChild(criarItemListaCandidato(c)))
  atualizarGraficoCompetencias(candidatos); 
}

export function atualizaListaEmpresas(listaEmpresas: HTMLUListElement, empresas: Empresa[]) {
  listaEmpresas.innerHTML = ""
  empresas.forEach(e => listaEmpresas.appendChild(criarItemListaEmpresa(e)))
}