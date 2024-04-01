DELETE FROM tb_roles;

INSERT INTO tb_roles VALUES ((UNHEX(REPLACE(UUID(), '-', ''))), now(), now(), 'ROLE_STUDENT');
INSERT INTO tb_roles VALUES ((UNHEX(REPLACE(UUID(), '-', ''))), now(), now(), 'ROLE_INSTRUCTOR');
INSERT INTO tb_roles VALUES ((UNHEX(REPLACE(UUID(), '-', ''))), now(), now(), 'ROLE_ADMIN');
