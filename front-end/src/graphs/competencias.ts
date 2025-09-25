import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

import { Candidato } from "../models/candidato";

let graficoCompetencias: Chart | null = null;

function contarCompetencias(candidatos: Candidato[]) {
  const contador = new Map<string, number>();
  candidatos.forEach((c) => {
    c.competencias?.forEach((comp) => {
      const chave = comp.trim().toLowerCase();
      contador.set(chave, (contador.get(chave) || 0) + 1);
    });
  });
  return contador;
}

export function atualizarGraficoCompetencias(candidatos: Candidato[]) {
  const competenciasCount = contarCompetencias(candidatos);
  const labels = Array.from(competenciasCount.keys());
  const data = Array.from(competenciasCount.values());

  const ctx = (
    document.getElementById("grafico_competencias") as HTMLCanvasElement
  ).getContext("2d")!;

  if (graficoCompetencias && graficoCompetencias.data.datasets![0]) {
    graficoCompetencias.data.labels = labels;
    graficoCompetencias.data.datasets![0].data = data;
    graficoCompetencias.update();
  } else {
    graficoCompetencias = new Chart(ctx, {
      type: "bar", 
      data: {
        labels,
        datasets: [
          {
            label: "Candidatos por competência",
            data,
            backgroundColor: "rgba(54, 162, 235, 0.6)",
            borderColor: "rgba(54, 162, 235, 1)",
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      } as any
    });
  } 
}
