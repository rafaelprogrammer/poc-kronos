-- MOCK SIMPLES DOS DADOS
-- DROP TABLE IF EXISTS

CREATE SCHEMA IF NOT EXISTS KRONOS;
DROP TABLE IF EXISTS KRONOS.USUARIO;
DROP TABLE IF EXISTS KRONOS.AUTHORITIES;


CREATE TABLE KRONOS.USUARIO (
	id int NOT NULL IDENTITY(1,1),
	username varchar(200) unique,
	password varchar(250),
	dataCriacao datetime

);

create table KRONOS.AUTHORITIES (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references KRONOS.USUARIO (username)
);

INSERT INTO KRONOS.USUARIO (id, username, password, dataCriacao) VALUES
(1, 'admin', '$2a$10$WSb6HFu/MsolSHARlwmAW.ldtppmXEPpzngzDDQbEjTCbx.rPLMIe', PARSEDATETIME('2018-09-10 15:00:00', 'yyyy-MM-dd HH:mm:ss')),
(2, 'teste', 'teste', PARSEDATETIME('2018-09-10 15:00:00', 'yyyy-MM-dd HH:mm:ss'));


INSERT INTO KRONOS.AUTHORITIES (username, authority) VALUES
('admin', 'ADMIN'),
('teste', 'ADMIN');
