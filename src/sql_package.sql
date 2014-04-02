create or replace PACKAGE MY_TEST_PACKAGE AS

  PROCEDURE INSERT_CLIENT(nameVal IN CLIENTS.NAME%TYPE, surnameVal IN CLIENTS.SURNAME%TYPE, dateofbirth IN CLIENTS.DATEOFBIRTH%TYPE);
  
  PROCEDURE INSERT_CARDS(cardVal IN CARDS.CARD%TYPE);
  
  PROCEDURE INSERT_ACCOUNTS(accVal IN ACCOUNTS.ACCOUNT%TYPE);
  
  PROCEDURE UPDATE_CLIENT(idClient IN CLIENTS.ID%TYPE, nameVal IN CLIENTS.NAME%TYPE, surnameVal IN CLIENTS.SURNAME%TYPE, 
     dateofbirthVal IN CLIENTS.DATEOFBIRTH%TYPE);
  
  PROCEDURE DELETE_CLIENT(idClient IN CLIENTS.ID%TYPE);
  
END MY_TEST_PACKAGE;


------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------


create or replace PACKAGE BODY MY_TEST_PACKAGE
  AS
    ID_CLIENT_STORAGE CLIENTS.ID%TYPE;
    
-- ---------------------------------------------------------------------------------------------------------------------
  PROCEDURE INSERT_CLIENT(nameVal IN CLIENTS.NAME%TYPE, surnameVal IN CLIENTS.SURNAME%TYPE, dateofbirth IN CLIENTS.DATEOFBIRTH%TYPE)
  AS
    BEGIN
      INSERT INTO CLIENTS(NAME, SURNAME, DATEOFBIRTH) VALUES(nameVal, surnameVal, dateofbirth) RETURNING ID INTO ID_CLIENT_STORAGE;
  END INSERT_CLIENT;
-- ---------------------------------------------------------------------------------------------------------------------  
  PROCEDURE INSERT_CARDS(cardVal IN CARDS.CARD%TYPE)
  AS
    BEGIN
      INSERT INTO CARDS(CARD, ID_CLIENT) VALUES(cardVal, ID_CLIENT_STORAGE);
  END INSERT_CARDS;
-- ---------------------------------------------------------------------------------------------------------------------  
  PROCEDURE INSERT_ACCOUNTS(accVal IN ACCOUNTS.ACCOUNT%TYPE)
  AS
    BEGIN
      INSERT INTO ACCOUNTS(ACCOUNT, ID_CLIENT) VALUES(accVal, ID_CLIENT_STORAGE);
  END INSERT_ACCOUNTS;
-- --------------------------------------------------------------------------------------------------------------------- 
  PROCEDURE UPDATE_CLIENT (idClient IN CLIENTS.ID%TYPE, nameVal IN CLIENTS.NAME%TYPE, surnameVal IN CLIENTS.SURNAME%TYPE, 
     dateofbirthVal IN CLIENTS.DATEOFBIRTH%TYPE)
  AS
  BEGIN
    UPDATE CLIENTS SET NAME='nameVal', SURNAME='surnameVal', DATEOFBIRTH='dateofbirthVal' WHERE CLIENTS.ID=idClient;
  END UPDATE_CLIENT;
-- ---------------------------------------------------------------------------------------------------------------------  
  PROCEDURE DELETE_CLIENT(idClient IN CLIENTS.ID%TYPE)
  AS
  BEGIN
    DELETE FROM CLIENTS WHERE CLIENTS.ID=idClient;
  END DELETE_CLIENT;
-- ---------------------------------------------------------------------------------------------------------------------  
END MY_TEST_PACKAGE;