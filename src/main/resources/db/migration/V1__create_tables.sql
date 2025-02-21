-- V1__create_tables.sql

-- Tabela Endereco
CREATE TABLE endereco (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(100),
    cep VARCHAR(20),
    pais VARCHAR(100),
    referencia VARCHAR(255)
);

-- Tabela Pessoa (classe abstrata com Inheritance JOINED)
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
    endereco_id BIGINT,
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

-- Tabela Role
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela de relacionamento Pessoa x Role
CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES pessoa(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tabela Medico (extends Pessoa)
CREATE TABLE medicos (
    id BIGINT PRIMARY KEY,
    crm VARCHAR(50) NOT NULL,
    valor_consulta DECIMAL(19,2) NOT NULL,
    especialidade VARCHAR(50),
    CONSTRAINT fk_medico_pessoa FOREIGN KEY (id) REFERENCES pessoa(id)
);

-- Tabela Paciente (extends Pessoa)
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

-- Tabela HistoricoTransacoes
CREATE TABLE historico_transacoes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    status VARCHAR(50),
    valor DOUBLE,
    remetente BIGINT,
    data_transacao DATETIME,
    CONSTRAINT fk_transacao_paciente FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    CONSTRAINT fk_transacao_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

-- Tabela HistoricoDoenca
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

-- Valores iniciais Roles

INSERT INTO roles (role_name) VALUES ('ADMIN'), ('MEDICO'), ('PACIENTE');
