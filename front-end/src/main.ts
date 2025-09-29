
import { Empresa } from "./models/empresa";
import { Candidato } from "./models/candidato";

import { recuperarLocalStorageCandidatos } from "./storage/localStorage";
import { recuperarLocalStorageEmpresas } from "./storage/localStorage";
import { salvarLocalStorage } from "./storage/localStorage";

import { atualizaListaCandidatos } from "./ui/lists";
import { atualizaListaEmpresas } from "./ui/lists";
import { handleFormSubmitCandidato, handleFormSubmitEmpresa } from "./ui/forms";
import { atualizarVisao } from "./ui/view";

// botões
const boaoAbrirDialogEmpresa = document.getElementById("botao_adicionar_empresa") as HTMLButtonElement;
const boaoAbrirDialogCandidato = document.getElementById("botao_adicionar_candidato") as HTMLButtonElement;
const botaoFecharModalEmpresa = document.getElementById("fechar_modal_empresa") as HTMLButtonElement;
const botaoFecharModalCandidato = document.getElementById("fechar_modal_candidato") as HTMLButtonElement;

//listas
const listaEmpresas = document.getElementById('lista_empresas') as HTMLUListElement;
const listaCandidatos = document.getElementById('lista_candidatos') as HTMLUListElement;

// dialogs
const dialogEmpresa = document.getElementById('modal_dialog_empresa') as HTMLDialogElement;
const dialogCandidato = document.getElementById('modal_dialog_candidato') as HTMLDialogElement;

// forms
const formEmpresa = dialogEmpresa.querySelector('form') as HTMLFormElement;
const formCandidato = dialogCandidato.querySelector('form') as HTMLFormElement;

// radios
const radioCandidato = document.getElementById("radio_candidato") as HTMLInputElement;
const radioEmpresa = document.getElementById("radio_empresa") as HTMLInputElement;

// containers de listas
const containerListaCandidatos = document.getElementById("container_lista_candidatos")!;
const containerListaEmpresas = document.getElementById("container_lista_empresas")!;
const containerGrafico = document.getElementById("container_grafico")!;

// dados em memória
let empresas: Empresa[] = recuperarLocalStorageEmpresas()
let candidatos: Candidato[] = recuperarLocalStorageCandidatos()

boaoAbrirDialogEmpresa.addEventListener("click", () => {
  dialogEmpresa.showModal();
});

boaoAbrirDialogCandidato.addEventListener("click", () => {
  dialogCandidato.showModal(); 
});

botaoFecharModalCandidato.addEventListener("click", () => {
  dialogCandidato.close(); 
});

botaoFecharModalEmpresa.addEventListener("click", () => {
  dialogEmpresa.close(); 
});

listaCandidatos.addEventListener("click", (e) => {
  const target = e.target as HTMLElement;
  if (target.closest(".excluir_candidato")) {
    const li = target.closest("li");
    if (!li) return;

    const index = Array.from(listaCandidatos.children).indexOf(li);
    if (index >= 0) {
      candidatos.splice(index, 1); 
      salvarLocalStorage("candidatos", candidatos);
      atualizaListaCandidatos(listaCandidatos, candidatos);
    }
  }
});

listaEmpresas.addEventListener("click", (e) => {
  const target = e.target as HTMLElement;
  if (target.closest(".excluir_empresa")) {
    const li = target.closest("li");
    if (!li) return;

    const index = Array.from(listaEmpresas.children).indexOf(li);
    if (index >= 0) {
      empresas.splice(index, 1); 
      salvarLocalStorage("empresas", empresas);
      atualizaListaEmpresas(listaEmpresas, empresas);
    }
  }
});

atualizarVisao(radioCandidato, radioEmpresa, containerListaEmpresas, containerListaCandidatos, containerGrafico);

radioCandidato.addEventListener("change", () => { 
  atualizarVisao(radioCandidato, radioEmpresa, containerListaEmpresas, containerListaCandidatos, containerGrafico) });
radioEmpresa.addEventListener("change", () => { 
  atualizarVisao(radioCandidato, radioEmpresa, containerListaEmpresas, containerListaCandidatos, containerGrafico) });

handleFormSubmitEmpresa(formEmpresa, listaEmpresas, dialogEmpresa, empresas);
handleFormSubmitCandidato(formCandidato, listaCandidatos, dialogCandidato, candidatos);

atualizaListaCandidatos(listaCandidatos, candidatos)
atualizaListaEmpresas(listaEmpresas, empresas)
