CREATE TABLE candidatos(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    senha varchar(255) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recrutadores(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    senha varchar(255) NOT NULL,
    empresa varchar(255)
);

CREATE TABLE vagas(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo varchar(255) NOT NULL,
    descricao TEXT NOT NULL,
    requisitos TEXT NOT NULL,
    recrutador_id BIGINT NOT NULL,
    status varchar(50) DEFAULT 'ABERTA',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_vaga_recrutador FOREIGN KEY (recrutador_id) REFERENCES recrutadores (id)
);

CREATE TABLE candidaturas(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    candidato_id BIGINT NOT NULL,
    vaga_id BIGINT NOT NULL,

    curriculo_url varchar(255) NOT NULL,
    ia_feedback JSONB,
    status varchar(50) DEFAULT 'RECEBIDO',

    data_aplicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    match_porcentagem int

    CONSTRAINT fk_candidatura_candidato FOREIGN KEY(candidato_id) REFERENCES candidatos(id),
    CONSTRAINT fk_candidatura_vaga FOREIGN KEY(vaga_id) REFERENCES vagas(id),
    CONSTRAINT fk_candidatura_candidato FOREIGN KEY(candidato_id) REFERENCES candidatos(id)
);