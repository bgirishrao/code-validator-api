DROP SCHEMA PUBLIC CASCADE;
COMMIT;

CREATE CACHED TABLE VALUESETS (
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  CODE VARCHAR(50) NOT NULL,
  DISPLAYNAME VARCHAR(10000)  NOT NULL,
  CODESYSTEMNAME VARCHAR(50)  NOT NULL,
  CODESYSTEMVERSION VARCHAR(50)  NOT NULL,
  CODESYSTEM VARCHAR(50)  NOT NULL,
  TTY VARCHAR(10)  NOT NULL,
  VALUESETNAME VARCHAR(75),
  VALUESETOID VARCHAR(50),
  VALUESETTYPE VARCHAR(50),
  VALUESETDEFINITIONVERSION VARCHAR(25),
  VALUESETSTEWARD VARCHAR(500)
);

CREATE CACHED TABLE CODES (
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  CODE VARCHAR(50) NOT NULL,
  DISPLAYNAME VARCHAR(5000) NOT NULL,
  CODESYSTEM VARCHAR(50) NOT NULL
);
COMMIT ;

CREATE INDEX IDX_VALUESETS ON VALUESETS (CODE, DISPLAYNAME, CODESYSTEMNAME, CODESYSTEMVERSION, CODESYSTEM, VALUESETNAME, VALUESETOID);
CREATE INDEX IDX_VALUESETOIDS ON VALUESETS (VALUESETOID);
CREATE INDEX IDX_CODESYSTEMSINVALUESETOIDS ON VALUESETS (CODESYSTEM, VALUESETOID);
CREATE INDEX IDX_CODESYSTEMS ON VALUESETS (CODE, DISPLAYNAME, CODESYSTEM, CODESYSTEMNAME, CODESYSTEMVERSION);
CREATE INDEX IDX_CODES ON VALUESETS (CODE, VALUESETOID);
COMMIT ;
