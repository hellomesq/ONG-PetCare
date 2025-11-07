-- Inserir dados de teste para facilitar a demonstração da funcionalidade de IA

-- Inserir uma ONG de exemplo
INSERT INTO ong (nome, email, senha) VALUES 
('Amigos dos Animais', 'contato@amigosanimais.org', 'senha123');

-- Inserir alguns pets de exemplo
INSERT INTO pet (nome, especie, raca, idade, descricao, ong_id) VALUES 
('Rex', 'Cachorro', 'Labrador', 3, 'Rex é um cachorro muito brincalhão e energético. Adora correr e brincar com bola. Precisa de bastante exercício diário.', 1),
('Mia', 'Gato', 'Persa', 2, 'Mia é uma gatinha calma e carinhosa. Gosta de dormir em lugares altos e receber carinho. Tem pelagem longa que precisa de escovação regular.', 1),
('Bob', 'Cachorro', 'Vira-lata', 5, 'Bob é um cão idoso, muito tranquilo e companheiro. Ideal para apartamentos. Tem algumas limitações de mobilidade.', 1);
