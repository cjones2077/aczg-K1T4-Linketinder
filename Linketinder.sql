CREATE TABLE competencias (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100)
);

CREATE TABLE candidatos (
  cpf VARCHAR(14) PRIMARY KEY,
  nome VARCHAR(100),
  sobrenome VARCHAR(100),
  email VARCHAR(150),
  data_nascimento DATE,
  formacao TEXT,
  pais VARCHAR(20),
  cep VARCHAR(10),
  descricao TEXT,
  senha VARCHAR(255)
);

CREATE TABLE empresas (
  cnpj VARCHAR(18) PRIMARY KEY,
  nome VARCHAR(150),
  email VARCHAR(150),
  data_criacao DATE,
  pais VARCHAR(20),
  cep VARCHAR(10),
  descricao TEXT,
  senha VARCHAR(255)
);

CREATE TABLE vagas (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(150),
  descricao TEXT,
  local_vaga VARCHAR(100),
  empresa_cnpj VARCHAR(18) REFERENCES empresas(cnpj)
);

CREATE TABLE candidatos_competencias (
  cpf_candidato VARCHAR(14) REFERENCES candidatos(cpf),
  id_competencia INT REFERENCES competencias(id)
);

CREATE TABLE vagas_competencias (
  id_vaga int REFERENCES vagas(id),
  id_competencia INT REFERENCES competencias(id)
);

CREATE TABLE empresa_curte_candidato (
  id SERIAL PRIMARY KEY,
  cnpj_empresa VARCHAR(18) REFERENCES empresas(cnpj),
  cpf_candidato VARCHAR(14) REFERENCES candidatos(cpf),
  data_curtida DATE DEFAULT CURRENT_DATE
);

CREATE TABLE candidato_curte_vaga (
  id SERIAL PRIMARY KEY,
  cpf_candidato VARCHAR(14) REFERENCES candidatos(cpf),
  id_vaga INT REFERENCES vagas(id),
  data_curtida DATE DEFAULT CURRENT_DATE
);

INSERT INTO candidatos (cpf, nome, sobrenome, email, data_nascimento, formacao, pais, cep, descricao, senha)
VALUES
('123.456.789-00', 'Lucas', 'Mendes', 'lucas.mendes@email.com', '1995-04-12', 'Engenharia de Software', 'Brasil', '01310-000', 'Desenvolvedor backend com experiência em Python e Django.', 'senha123'),
('987.654.321-00', 'Mariana', 'Souza', 'mariana.souza@email.com', '1998-07-30', 'Ciência da Computação', 'Brasil', '04567-890', 'Analista de dados com foco em aprendizado de máquina.', 'senha456'),
('456.789.123-00', 'Rafael', 'Oliveira', 'rafael.oliveira@email.com', '1992-11-08', 'Design Gráfico', 'Brasil', '11015-020', 'Designer especializado em UI/UX e identidade visual.', 'senha789'),
('321.654.987-00', 'Beatriz', 'Lima', 'beatriz.lima@email.com', '1999-02-22', 'Administração', 'Brasil', '88010-200', 'Profissional organizada, com experiência em gestão de projetos.', 'senha321'),
('654.987.321-00', 'Carlos', 'Almeida', 'carlos.almeida@email.com', '1990-09-14', 'Sistemas de Informação', 'Brasil', '30140-100', 'Programador full stack com foco em Flutter e Firebase.', 'senha654');

INSERT INTO empresas (cnpj, nome, email, data_criacao, pais, cep, descricao, senha)
VALUES
('12.345.678/0001-00', 'TechNova Solutions', 'contato@technova.com', '2015-06-01', 'Brasil', '04567-000', 'Empresa de tecnologia especializada em soluções web e mobile.', 'admin123'),
('98.765.432/0001-11', 'EduSmart', 'rh@edusmart.com', '2018-03-15', 'Brasil', '70040-010', 'Startup voltada para inovação no setor educacional.', 'admin456'),
('11.222.333/0001-22', 'HealthPlus', 'contato@healthplus.com', '2012-10-20', 'Brasil', '20031-050', 'Clínica digital focada em telemedicina e análise de dados de saúde.', 'admin789'),
('44.555.666/0001-33', 'EcoBuild Engenharia', 'contato@ecobuild.com', '2016-12-05', 'Brasil', '88015-600', 'Empresa de engenharia civil sustentável.', 'admin321'),
('77.888.999/0001-44', 'ByteWave', 'suporte@bytewave.com', '2020-05-25', 'Brasil', '30120-040', 'Desenvolvedora de softwares corporativos e soluções em nuvem.', 'admin654');
