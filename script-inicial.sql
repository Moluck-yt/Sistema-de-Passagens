-- Script de Inicialização do Banco de Dados
-- Sistema de Passagens Rodoviárias
-- Equipe 1: Rodrigo, Gabriel Kauan, Guilherme Teixeira

-- Criação do banco de dados (opcional, pois o Hibernate pode criar automaticamente)
CREATE DATABASE IF NOT EXISTS passagens_rodoviarias
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE passagens_rodoviarias;

-- Inserir usuário administrador padrão
INSERT INTO usuarios (nome, cargo, login, senha, email) VALUES
('Administrador', 'Gerente', 'admin', 'admin123', 'admin@sistema.com'),
('Gabriel Kauã', 'Atendente', 'gabriel', '123456', 'gabriel@sistema.com'),
('Rodrigo', 'Supervisor', 'rodrigo', '123456', 'rodrigo@sistema.com'),
('Guilherme Teixeira', 'Atendente', 'guilherme', '123456', 'guilherme@sistema.com');

-- Inserir algumas cidades de exemplo
INSERT INTO cidades (nomeCidade, idCidade, uf) VALUES
('Salvador', '2927408', 'BA'),
('Feira de Santana', '2910800', 'BA'),
('Vitória da Conquista', '2933307', 'BA'),
('Camaçari', '2905701', 'BA'),
('Ilhéus', '2913606', 'BA'),
('Itabuna', '2914505', 'BA'),
('Lauro de Freitas', '2918704', 'BA'),
('Juazeiro', '2918001', 'BA'),
('Paulo Afonso', '2924009', 'BA'),
('Alagoinhas', '2900603', 'BA'),
('São Paulo', '3550308', 'SP'),
('Rio de Janeiro', '3304557', 'RJ'),
('Belo Horizonte', '3106200', 'MG'),
('Brasília', '5300108', 'DF'),
('Fortaleza', '2304400', 'CE'),
('Recife', '2611606', 'PE'),
('Aracaju', '2800308', 'SE'),
('Maceió', '2704302', 'AL');

-- Inserir alguns veículos de exemplo
INSERT INTO veiculos (numero, placa, motorista, modelo, dataCompra, qtdPoltronas) VALUES
('BUS001', 'ABC1D23', 'João Silva', 'Mercedes-Benz O500', '2023-01-15', 45),
('BUS002', 'DEF4G56', 'Maria Santos', 'Marcopolo G7', '2023-03-20', 42),
('BUS003', 'GHI7J89', 'Pedro Oliveira', 'Scania K360', '2023-05-10', 48),
('BUS004', 'JKL0M12', 'Ana Costa', 'Volvo B270F', '2023-07-25', 40),
('BUS005', 'MNO3P45', 'Carlos Souza', 'Mercedes-Benz OF-1721', '2023-09-12', 50);

-- Inserir algumas passagens de exemplo (não vendidas)
-- Nota: Você precisará ajustar os IDs das cidades e veículos após a inserção acima

-- Exemplo de consultas úteis para verificar os dados:

-- Verificar usuários
SELECT * FROM usuarios;

-- Verificar cidades
SELECT * FROM cidades ORDER BY nomeCidade;

-- Verificar veículos
SELECT * FROM veiculos ORDER BY numero;

-- Verificar passagens
SELECT
    p.idPassagem,
    v.numero AS veiculo,
    p.poltrona,
    p.dataSaida,
    p.horaSaida,
    co.nomeCidade AS origem,
    cd.nomeCidade AS destino,
    p.valorPassagem,
    p.vendida
FROM passagens p
JOIN veiculos v ON p.veiculo_id = v.id
JOIN cidades co ON p.cidadeOrigem_id = co.id
JOIN cidades cd ON p.cidadeDestino_id = cd.id
ORDER BY p.dataSaida, p.horaSaida;

-- Consultar faturamento
SELECT
    DATE(p.dataVenda) AS data,
    COUNT(*) AS quantidade_passagens,
    SUM(p.valorPassagem) AS faturamento_total
FROM passagens p
WHERE p.vendida = true
GROUP BY DATE(p.dataVenda)
ORDER BY data DESC;

-- Consultar passagens por roteiro
SELECT
    co.nomeCidade AS origem,
    cd.nomeCidade AS destino,
    COUNT(*) AS total_passagens,
    SUM(p.valorPassagem) AS faturamento
FROM passagens p
JOIN cidades co ON p.cidadeOrigem_id = co.id
JOIN cidades cd ON p.cidadeDestino_id = cd.id
WHERE p.vendida = true
GROUP BY co.nomeCidade, cd.nomeCidade
ORDER BY total_passagens DESC;

-- Verificar poltronas ocupadas por veículo/data/hora
SELECT
    v.numero AS veiculo,
    p.dataSaida,
    p.horaSaida,
    GROUP_CONCAT(p.poltrona ORDER BY p.poltrona) AS poltronas_ocupadas,
    COUNT(*) AS total_ocupadas,
    v.qtdPoltronas AS total_poltronas,
    (v.qtdPoltronas - COUNT(*)) AS poltronas_disponiveis
FROM passagens p
JOIN veiculos v ON p.veiculo_id = v.id
WHERE p.vendida = true
GROUP BY v.numero, p.dataSaida, p.horaSaida, v.qtdPoltronas
ORDER BY p.dataSaida, p.horaSaida;
