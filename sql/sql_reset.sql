drop sequence ACTORES_FILME_SEQ ;
drop sequence ACTORES_SEQ ;
drop sequence FILMES_SEQ ;
drop sequence GENEROS_SEQ ;
drop sequence LISTA_GENEROS_SEQ ;
drop sequence PRODUTORAS_SEQ ;
drop sequence REALIZADORES_SEQ ;
drop sequence UTILIZADORES_SEQ ;
drop sequence Lista_Ratings_seq;
drop sequence Noticias_seq;
drop function idade;
drop function idade2;
drop view viewratingsfilmes ;
drop view viewtopbudgetfilmes ;
drop view viewtopgrossfilmes ;
drop view viewtopratingfilmes ;
drop view viewtopvotosfilmes ;
drop view viewtopprofitfilmes ;

drop table UTILIZADORES cascade constraints;
drop table PRODUTORAS cascade constraints;
drop table REALIZADORES cascade constraints;
drop table GENEROS cascade constraints;
drop table ACTORES cascade constraints;
drop table FILMES cascade constraints;
drop table NOTICIAS cascade constraints;
drop table TABELA_RATINGS cascade constraints;
drop table LISTA_GENEROS cascade constraints;
drop table ACTORES_FILME cascade constraints;
drop table CISE cascade constraints;

create table Utilizadores(
ID_UTILIZADOR integer primary key autoincrement, -- codigo do utilizador
USERNAME varchar2(20), -- username
EMAIL varchar2(32), -- email do utilizador
PASSWORD varchar2(32), -- password em MD5
DATA_NASCIMENTO varchar2(10), -- data de nascimento
AVATAR varchar(40), --imagem utilizador
ESTADO number(1) --estado do utilizador
);

create table Produtoras(
ID_PRODUTORA integer primary key autoincrement, -- codigo produtora
NOME varchar2(25), -- nome produtora
NACIONALIDADE varchar2(20), -- nacionalidade (sigla)
IMAGEM varchar2(30) -- imagem produtora
);

create table Realizadores(
ID_REALIZADOR integer primary key autoincrement, -- codigo realizador
NOME varchar2(25), -- nome director
DATA_NASCIMENTO varchar2(10), -- ano de nascimento -- formato 'dd-mm-aaaa'
NACIONALIDADE varchar2(20), -- nacionalidade (sigla)
SEXO varchar2(10), -- sexo do realizador
IMAGEM varchar2(30) -- imagem realizador
);

create table Generos(
ID_GENERO integer primary key autoincrement, -- c—digo genero
NOME varchar2(20) -- genero
);

create table Actores(
ID_ACTOR integer primary key autoincrement, -- codigo actor
NOME varchar2(25), -- nome actor
DATA_NASCIMENTO varchar2(10), -- ano de nascimento
DATA_MORTE varchar2(10), -- ano de falecimento
PREMIOS_NOMEADO number(3), -- numero de premios nomeado
PREMIOS_VENCIDOS number(3), -- numero de premios vencidos
OSCARES number(3), -- oscares vencidos
NACIONALIDADE varchar2(20), -- nacionalidade (sigla)
SEXO varchar2(10), -- sexo do actor
IMAGEM varchar2(30) -- imagem actor
);

create table Filmes(
ID_FILME integer primary key autoincrement, -- codigo filme
TITULO varchar2(50), -- titulo filme
ID_REALIZADOR number(6),
ID_PRODUTORA number(6),
ANO number(4), -- ano de produ‹o
BUDGET number(10), -- dinheiro gasto a produzir
GROSS number(10), -- dinheiro ganho
PREMIOS_NOMEADO number(3), -- premios nomeado
PREMIOS_VENCIDOS number(3), -- premies vencidos
OSCARES number(3), --oscares vencidos
RATING number(2), -- idade minima para ver o filme
SINOPSE varchar(300), --sinopse
TRAILER varchar2(15), -- link do trailer
POSTER varchar2(50), -- path poster
FOREIGN KEY (ID_REALIZADOR) references Realizadores(ID_REALIZADOR), --  codigo director
FOREIGN KEY (ID_PRODUTORA) references Produtoras(ID_PRODUTORA) -- codigo produtora
);

create table Tabela_Ratings(
ID_TABELA_RATINGS integer primary key autoincrement, -- codigo da tabela ratings
ID_FILME number(6),
ID_UTILIZADOR number(6),
PONTUACAO number(2),
FOREIGN KEY (ID_FILME) references Filmes(ID_FILME), -- codigo filme
FOREIGN KEY (ID_UTILIZADOR) references Utilizadores(ID_UTILIZADOR) -- codigo genero
);

create table Lista_generos(
ID_LISTA_GENEROS integer primary key autoincrement, -- codigo da lista genera
ID_FILME number(6),
ID_GENERO number(6),
FOREIGN KEY (ID_FILME) references Filmes(ID_FILME), -- codigo filme
FOREIGN KEY (ID_GENERO) references Generos(ID_GENERO) -- codigo genero
);

create table Actores_filme(
ID_ACTORES_FILME integer primary key autoincrement, -- codigo actores filme
ID_FILME number(6),
ID_ACTOR number(6),
PERSONAGEM varchar2(35),
FOREIGN KEY (ID_FILME) references Filmes(ID_FILME), -- codigo filme
FOREIGN KEY (ID_ACTOR) references Actores(ID_ACTOR) -- codigo actor
);

CREATE TABLE CISE
(
  session_id    VARCHAR2(40)  DEFAULT 0 NOT NULL PRIMARY KEY,
  ip_address    VARCHAR2(16)  DEFAULT 0 NOT NULL,
  user_agent    VARCHAR2(200)          NOT NULL,
  last_activity  NUMBER                       ,
  user_data    VARCHAR2(3000),
  ID_UTILIZADOR NUMBER,
  username VARCHAR2(20),
  FOREIGN KEY (ID_UTILIZADOR) REFERENCES UTILIZADORES(ID_UTILIZADOR)
);


