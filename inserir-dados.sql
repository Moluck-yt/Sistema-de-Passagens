-- Execute este script DEPOIS que a aplicação subir pela primeira vez
-- As tabelas serão criadas automaticamente pelo Hibernate

USE passagens_rodoviarias;

-- Inserir usuários
INSERT INTO usuarios (nome, cargo, login, senha, email) VALUES
('Administrador', 'Gerente', 'admin', 'admin123', 'admin@sistema.com'),
('Gabriel Kauan', 'Atendente', 'gabriel', '123456', 'gabriel@sistema.com'),
('Rodrigo', 'Supervisor', 'rodrigo', '123456', 'rodrigo@sistema.com'),
('Guilherme Teixeira', 'Atendente', 'guilherme', '123456', 'guilherme@sistema.com');

-- Inserir cidades
INSERT INTO cidades (nomeCidade, idCidade, uf) VALUES
('Salvador', '2927408', 'BA'),
('Feira de Santana', '2910800', 'BA'),
('Vitória da Conquista', '2933307', 'BA'),
('Camaçari', '2905701', 'BA'),
('Ilhéus', '2913606', 'BA'),
('Itabuna', '2914505', 'BA'),
('São Paulo', '3550308', 'SP'),
('Rio de Janeiro', '3304557', 'RJ'),
('Belo Horizonte', '3106200', 'MG'),
('Brasília', '5300108', 'DF');

-- Inserir veículos
INSERT INTO veiculos (numero, placa, motorista, modelo, dataCompra, qtdPoltronas) VALUES
('BUS001', 'ABC1D23', 'João Silva', 'Mercedes-Benz O500', '2023-01-15', 45),
('BUS002', 'DEF4G56', 'Maria Santos', 'Marcopolo G7', '2023-03-20', 42),
('BUS003', 'GHI7J89', 'Pedro Oliveira', 'Scania K360', '2023-05-10', 48),
('BUS004', 'JKL0M12', 'Ana Costa', 'Volvo B270F', '2023-07-25', 40),
('BUS005', 'MNO3P45', 'Carlos Souza', 'Mercedes-Benz OF-1721', '2023-09-12', 50);
