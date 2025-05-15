CREATE SEQUENCE seq_campo_monitorado
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;
  
CREATE TABLE campos_monitorados (
    id               NUMBER PRIMARY KEY,
    campo            VARCHAR2(500) NOT NULL,
    campo_monitorado NUMBER(1)     NOT NULL CHECK (campo_monitorado IN (0, 1)),
    display_name     VARCHAR2(50)
);

COMMENT ON TABLE campos_monitorados IS 'Tabela que armazena alterações monitoradas em campos de objetos.';

COMMENT ON COLUMN campos_monitorados.id IS 'Identificador único gerado por sequência.';
COMMENT ON COLUMN campos_monitorados.campo IS 'Nome do campo que foi alterado.';
COMMENT ON COLUMN campos_monitorados.campo_monitorado IS 'Indica se o campo é monitorado (1 = Sim, 0 = Não).';
COMMENT ON COLUMN campos_monitorados.display_name IS 'Nome amigável do campo para exibição.';
