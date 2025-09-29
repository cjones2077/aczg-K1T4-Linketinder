export function atualizarVisao(
  radioCandidato: HTMLInputElement,
  radioEmpresa: HTMLInputElement,
  containerEmpresas: HTMLElement,
  containerCandidatos: HTMLElement,
  containerGrafico: HTMLElement
) {
  if (radioCandidato.checked) {
    containerEmpresas.style.display = "block";
    containerCandidatos.style.display = "none";
    containerGrafico.style.display = "none";
  } else if (radioEmpresa.checked) {
    containerEmpresas.style.display = "none";
    containerCandidatos.style.display = "block";
    containerGrafico.style.display = "block";
  }
}