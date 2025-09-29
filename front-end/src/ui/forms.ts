import { Candidato } from "../models/candidato";
import { Empresa } from "../models/empresa";
import { salvarLocalStorage } from "../storage/localStorage";
import { atualizaListaCandidatos, atualizaListaEmpresas } from "./lists";

// inputs
const cpfInput = document.getElementById("cpf") as HTMLInputElement;
const nomeInputCandidato = document.getElementById("nome") as HTMLInputElement;
const nomeInputEmpresa = document.getElementById("nome_empresa") as HTMLInputElement;
const linkedinInput = document.getElementById("linkedin") as HTMLInputElement;
const numeroInput = document.getElementById("numero") as HTMLInputElement;
const cepInputCandidato = document.getElementById("cep_candidato") as HTMLInputElement;
const cepInputEmpresa = document.getElementById("cep_empresa") as HTMLInputElement;
const cnpjInput = document.getElementById("cnpj") as HTMLInputElement;
const emailInputCandidato = document.getElementById("email_candidato") as HTMLInputElement;
const emailInputEmpresa = document.getElementById("email_empresa") as HTMLInputElement;

function criaInstanciaEmpresa(dados: FormData): Empresa{
  const nome: string = dados.get('nome') as string;
  const email: string = dados.get('email_empresa') as string;
  const cep: string = dados.get('CEP') as string;
  const descricao: string = dados.get('descricao') as string;
  const estado: string = dados.get('estado') as string;
  const competencias: string[] = (dados.get('competencias') as string).split(',').map(s => s.trim());
  const cnpj: string = dados.get('cnpj') as string;
  const pais: string = dados.get('pais') as string;
  const nome_vaga: string = dados.get('nome_vaga') as string;
  return new Empresa(nome, email, cep, descricao, estado, competencias, cnpj, pais, nome_vaga)
}

function criaInstanciaCandidato(dados: FormData): Candidato {
  const nome: string = dados.get('nome') as string;
  const email: string = dados.get('email') as string;
  const cep: string = dados.get('CEP') as string;
  const descricao: string = dados.get('descricao') as string;
  const estado: string = dados.get('estado') as string;
  const competencias: string[] = (dados.get('competencias') as string).split(',').map(s => s.trim());
  const formacao: string = dados.get('formacao') as string;
  const cpf: string = dados.get('cpf') as string;
  const idade: number = Number(dados.get('idade'));
  const numero: string = dados.get('numero') as string;
  const linkedin: string = dados.get('linkedin') as string;

  return new Candidato(nome, email, cep, descricao, estado, competencias, formacao, cpf, idade, numero, linkedin)
}

export function handleFormSubmitEmpresa(form: HTMLFormElement, listaEmpresas: HTMLUListElement, dialog: HTMLDialogElement, empresas: Empresa[]) {
  form.addEventListener("submit", e => {
    e.preventDefault()
    const formData = new FormData(form)
    const empresa: Empresa = criaInstanciaEmpresa(formData)
    empresas.push(empresa)
    atualizaListaEmpresas(listaEmpresas, empresas)
    salvarLocalStorage("empresas", empresas)
    dialog.close()
    form.reset()
  });
}

export function handleFormSubmitCandidato(form: HTMLFormElement, listaCandidatos: HTMLUListElement, dialog: HTMLDialogElement, candidatos: Candidato[]) {
  form.addEventListener("submit", e => {
    e.preventDefault()
    const formData = new FormData(form)
    const candidato: Candidato = criaInstanciaCandidato(formData)
    candidatos.push(candidato)
    atualizaListaCandidatos(listaCandidatos, candidatos)
    salvarLocalStorage("candidatos", candidatos)
    dialog.close()
    form.reset()
  });
}

cpfInput.addEventListener("invalid", function () {
  this.setCustomValidity("CPF inválido!");
});

cepInputCandidato.addEventListener("invalid", function () {
  this.setCustomValidity("CEP inválido!");
});

cepInputEmpresa.addEventListener("invalid", function () {
  this.setCustomValidity("CEP inválido!");
});

cnpjInput.addEventListener("invalid", function () {
  this.setCustomValidity("CNPJ inválido!");
});

emailInputCandidato.addEventListener("invalid", function () {
  this.setCustomValidity("E-mail inválido!");
});

emailInputEmpresa.addEventListener("invalid", function () {
  this.setCustomValidity("E-mail inválido!");
});

numeroInput.addEventListener("invalid", function () {
  this.setCustomValidity("Número inválido!");
});

linkedinInput.addEventListener("invalid", function () {
  this.setCustomValidity("Link do perfil inválido!");
});

nomeInputCandidato.addEventListener("invalid", function () {
  this.setCustomValidity("Nome inválido!");
});

nomeInputEmpresa.addEventListener("invalid", function () {
  this.setCustomValidity("Nome inválido!");
});
