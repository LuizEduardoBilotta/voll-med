CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL auto_increment,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY(id)
);