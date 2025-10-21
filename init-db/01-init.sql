-- Script de inicialização do banco de dados
-- Este arquivo é executado automaticamente quando o container MySQL é criado pela primeira vez

USE passagens_rodoviarias;

-- Criar usuário administrador padrão
INSERT INTO usuarios (nome, cargo, login, senha, email)
VALUES ('Administrador', 'Gerente', 'admin', '123456', 'admin@passagens.com')
ON DUPLICATE KEY UPDATE nome = nome;

-- Inserir algumas cidades de exemplo (opcional)
INSERT IGNORE INTO cidades (nomeCidade, idCidade, uf) VALUES
('Salvador', '2927408', 'BA'),
('São Paulo', '3550308', 'SP'),
('Rio de Janeiro', '3304557', 'RJ'),
('Brasília', '5300108', 'DF'),
('Belo Horizonte', '3106200', 'MG'),
('Recife', '2611606', 'PE'),
('Fortaleza', '2304400', 'CE');

-- Log de inicialização
SELECT 'Banco de dados inicializado com sucesso!' as status;
