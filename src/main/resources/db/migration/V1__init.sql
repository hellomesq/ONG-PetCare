-- Flyway migration: initial schema for ONG and Pet

CREATE TABLE IF NOT EXISTS ong (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255),
  email VARCHAR(255),
  senha VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS pet (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255),
  especie VARCHAR(100),
  raca VARCHAR(255),
  idade INTEGER,
  descricao TEXT,
  ong_id BIGINT,
  CONSTRAINT fk_ong FOREIGN KEY(ong_id) REFERENCES ong(id) ON DELETE CASCADE
);
