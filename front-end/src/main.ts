
import { Empresa } from "./models/empresa";
import { Candidato } from "./models/candidato";
import { atualizarGraficoCompetencias } from "./graphs/competencias";

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


function recuperarLocalStorageCandidatos(): Candidato[] {
    const dados = localStorage.getItem("candidatos");
    if (!dados) return [];

    const array = JSON.parse(dados) as any[];
    return array.map(obj => new Candidato(
        obj.nome,
        obj.email,
        obj.cep,
        obj.descricao,
        obj.estado,
        obj.competencias,
        obj.formacao,
        obj.cpf,
        obj.idade
    ));
}

function recuperarLocalStorageEmpresas(): Empresa[] {
    const dados = localStorage.getItem("empresas");
    if (!dados) return [];

    const array = JSON.parse(dados) as any[];
    return array.map(obj => new Empresa(
        obj.nome_empresa,
        obj.email,
        obj.cep,
        obj.descricao,
        obj.estado,
        obj.competencias,
        obj.cnpj,
        obj.pais,
        obj.nome_vaga
    ));
}

function salvarLocalStorage<T>(chave: string, dados: T[]) {
  localStorage.setItem(chave, JSON.stringify(dados));
}

// atualizar dom
function atualizaListaCandidatos() {
  listaCandidatos.innerHTML = ""
  candidatos.forEach(c => listaCandidatos.appendChild(criarItemListaCandidato(c)))
  atualizarGraficoCompetencias(candidatos); 

}

function atualizaListaEmpresas() {
  listaEmpresas.innerHTML = ""
  empresas.forEach(e => listaEmpresas.appendChild(criarItemListaEmpresa(e)))
}

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


function criarItemListaCandidato(candidato: Candidato, privado: boolean = true): HTMLLIElement {
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

function criarItemListaEmpresa(empresa: Empresa, privado: boolean = true): HTMLLIElement {
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

function criaInstanciaEmpresa(dados: FormData): Empresa{
  const nome: string = dados.get('nome') as string;
  const email: string = dados.get('email') as string;
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

  return new Candidato(nome, email, cep, descricao, estado, competencias, formacao, cpf, idade)
}

function handleFormSubmitEmpresa(form: HTMLFormElement, lista: HTMLUListElement, dialog: HTMLDialogElement) {
  form.addEventListener("submit", e => {
    e.preventDefault()
    const formData = new FormData(form)
    const empresa: Empresa = criaInstanciaEmpresa(formData)
    empresas.push(empresa)
    atualizaListaEmpresas()
    salvarLocalStorage("empresas", empresas)
    dialog.close()
    form.reset()
  });
}

function handleFormSubmitCandidato(form: HTMLFormElement, lista: HTMLUListElement, dialog: HTMLDialogElement) {
  form.addEventListener("submit", e => {
    e.preventDefault()
    const formData = new FormData(form)
    const candidato: Candidato = criaInstanciaCandidato(formData)
    candidatos.push(candidato)
    atualizaListaCandidatos()
    salvarLocalStorage("candidatos", candidatos)
    dialog.close()
    form.reset()
  });
}

listaCandidatos.addEventListener("click", (e) => {
  const target = e.target as HTMLElement;
  if (target.closest(".excluir_candidato")) {
    const li = target.closest("li");
    if (!li) return;

    const index = Array.from(listaCandidatos.children).indexOf(li);
    if (index >= 0) {
      candidatos.splice(index, 1); 
      salvarLocalStorage("candidatos", candidatos);
      atualizaListaCandidatos();
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
      atualizaListaEmpresas();
    }
  }
});

function atualizarVisão() {
  if (radioCandidato.checked) {
    containerListaEmpresas.style.display = "block"; 
    containerListaCandidatos.style.display = "none"; 
    containerGrafico.style.display = "none"; 
  } else if (radioEmpresa.checked) {
    containerListaEmpresas.style.display = "none"; 
    containerListaCandidatos.style.display = "block";
    containerGrafico.style.display = "block"; 
  }
}


atualizarVisão();

radioCandidato.addEventListener("change", atualizarVisão);
radioEmpresa.addEventListener("change", atualizarVisão);

handleFormSubmitEmpresa(formEmpresa, listaEmpresas, dialogEmpresa);
handleFormSubmitCandidato(formCandidato, listaCandidatos, dialogCandidato);

atualizaListaCandidatos()
atualizaListaEmpresas()
