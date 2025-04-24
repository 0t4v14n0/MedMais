-- Tabela Endereco (sem chave estrangeira para pessoa inicialmente)
CREATE TABLE endereco (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(100),
    cep VARCHAR(20),
    pais VARCHAR(100),
    referencia VARCHAR(255)
);

-- Tabela Pessoa (sem a foreign key inicialmente)
CREATE TABLE pessoa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    data_nascimento DATE,
    sexo VARCHAR(20),
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    saldo DECIMAL(19,2) DEFAULT 0.00,
    email_confirmado BOOLEAN DEFAULT FALSE,
    token_confirmacao VARCHAR(255),
    endereco_id BIGINT
);

-- Agora adicionamos as foreign keys para resolver o ciclo
ALTER TABLE pessoa
ADD CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id);

ALTER TABLE endereco
ADD COLUMN pessoa_id BIGINT,
ADD CONSTRAINT fk_endereco_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES pessoa(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE medicos (
    id BIGINT PRIMARY KEY,
    crm VARCHAR(50) NOT NULL,
    valor_consulta DECIMAL(19,2) NOT NULL,
    especialidade VARCHAR(50),
    CONSTRAINT fk_medico_pessoa FOREIGN KEY (id) REFERENCES pessoa(id)
);

CREATE TABLE agenda_medico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medico_id BIGINT NOT NULL,
    horario DATETIME NOT NULL,
    disponivel VARCHAR(50),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_agenda_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

CREATE TABLE paciente (
    id BIGINT PRIMARY KEY,
    tipo_sanguineo VARCHAR(10),
    numero_carteira_plano VARCHAR(100),
    contato_emergencia VARCHAR(100),
    peso DOUBLE,
    altura DOUBLE,
    tipo_plano VARCHAR(50),
    CONSTRAINT fk_paciente_pessoa FOREIGN KEY (id) REFERENCES pessoa(id)
);

CREATE TABLE consulta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,    
    valor_consulta DECIMAL(10,2),
    status_consulta VARCHAR(50),
    data DATETIME,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    observacoes TEXT,
    
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

CREATE TABLE historico_transacoes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    status VARCHAR(50),
    valor DOUBLE,
    remetente BIGINT,
    data_transacao DATETIME,
    CONSTRAINT fk_hist_trans_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    CONSTRAINT fk_hist_trans_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

CREATE TABLE historico_doenca (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    nome_da_doenca VARCHAR(255),
    descricao TEXT,
    data_diagnostico DATE,
    data_recuperacao DATE,
    estado_atual VARCHAR(50),
    tratamento TEXT,
    medicamentos TEXT,
    observacoes_medicas TEXT,
    gravidade VARCHAR(50),
    CONSTRAINT fk_doenca_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    CONSTRAINT fk_doenca_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

-- Tabela Plano
CREATE TABLE planos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    tipo_plano VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    desconto DECIMAL(10,2) NOT NULL,
    taxas_adicionais DECIMAL(10,2) NOT NULL,
    duracao INT,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE plano_beneficios (
    plano_id BIGINT NOT NULL,
    beneficio VARCHAR(255),
    FOREIGN KEY (plano_id) REFERENCES planos(id)
);

CREATE TABLE plano_limitacoes (
    plano_id BIGINT NOT NULL,
    limitacao VARCHAR(255),
    FOREIGN KEY (plano_id) REFERENCES planos(id)
);

CREATE TABLE plano_metodos_pagamento (
    plano_id BIGINT NOT NULL,
    metodo_pagamento VARCHAR(255),
    FOREIGN KEY (plano_id) REFERENCES planos(id)
);

-- Tabela Assinatura
CREATE TABLE assinatura (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    plano_id BIGINT NOT NULL,
    data_inicio DATE NOT NULL,
    data_expiracao DATE NOT NULL,
    ativo BOOLEAN NOT NULL,
    orderId VARCHAR(100),
    status_pagamento VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES paciente(id),
    FOREIGN KEY (plano_id) REFERENCES planos(id)
);

-- Valores iniciais Roles
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('MEDICO'), ('PACIENTE');
