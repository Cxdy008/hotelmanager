-- data.sql - Dados iniciais para o sistema de hotel
-- 60 quartos total: 35 Standard, 15 Luxo, 10 Suite
-- Todos com status 'DISPOSED' por padrão (disponível conforme constraint)

-- Inserindo quartos STANDARD (35 quartos - IDs 101 a 135)
-- Status padrão: DISPOSED (disponível conforme constraint)
INSERT INTO rooms (number, status, room_type) VALUES
                                                   (101, 'DISPOSED', 'STANDARD'),
                                                   (102, 'DISPOSED', 'STANDARD'),
                                                   (103, 'DISPOSED', 'STANDARD'),
                                                   (104, 'DISPOSED', 'STANDARD'),
                                                   (105, 'DISPOSED', 'STANDARD'),
                                                   (106, 'DISPOSED', 'STANDARD'),
                                                   (107, 'DISPOSED', 'STANDARD'),
                                                   (108, 'DISPOSED', 'STANDARD'),
                                                   (109, 'DISPOSED', 'STANDARD'),
                                                   (110, 'DISPOSED', 'STANDARD'),
                                                   (111, 'DISPOSED', 'STANDARD'),
                                                   (112, 'DISPOSED', 'STANDARD'),
                                                   (113, 'DISPOSED', 'STANDARD'),
                                                   (114, 'DISPOSED', 'STANDARD'),
                                                   (115, 'DISPOSED', 'STANDARD'),
                                                   (116, 'DISPOSED', 'STANDARD'),
                                                   (117, 'DISPOSED', 'STANDARD'),
                                                   (118, 'DISPOSED', 'STANDARD'),
                                                   (119, 'DISPOSED', 'STANDARD'),
                                                   (120, 'DISPOSED', 'STANDARD'),
                                                   (121, 'DISPOSED', 'STANDARD'),
                                                   (122, 'DISPOSED', 'STANDARD'),
                                                   (123, 'DISPOSED', 'STANDARD'),
                                                   (124, 'DISPOSED', 'STANDARD'),
                                                   (125, 'DISPOSED', 'STANDARD'),
                                                   (126, 'DISPOSED', 'STANDARD'),
                                                   (127, 'DISPOSED', 'STANDARD'),
                                                   (128, 'DISPOSED', 'STANDARD'),
                                                   (129, 'DISPOSED', 'STANDARD'),
                                                   (130, 'DISPOSED', 'STANDARD'),
                                                   (131, 'DISPOSED', 'STANDARD'),
                                                   (132, 'DISPOSED', 'STANDARD'),
                                                   (133, 'DISPOSED', 'STANDARD'),
                                                   (134, 'DISPOSED', 'STANDARD'),
                                                   (135, 'DISPOSED', 'STANDARD');

-- Inserindo quartos LUXO (15 quartos - IDs 201 a 215)
INSERT INTO rooms (number, status, room_type) VALUES
                                                   (201, 'DISPOSED', 'LUXO'),
                                                   (202, 'DISPOSED', 'LUXO'),
                                                   (203, 'DISPOSED', 'LUXO'),
                                                   (204, 'DISPOSED', 'LUXO'),
                                                   (205, 'DISPOSED', 'LUXO'),
                                                   (206, 'DISPOSED', 'LUXO'),
                                                   (207, 'DISPOSED', 'LUXO'),
                                                   (208, 'DISPOSED', 'LUXO'),
                                                   (209, 'DISPOSED', 'LUXO'),
                                                   (210, 'DISPOSED', 'LUXO'),
                                                   (211, 'DISPOSED', 'LUXO'),
                                                   (212, 'DISPOSED', 'LUXO'),
                                                   (213, 'DISPOSED', 'LUXO'),
                                                   (214, 'DISPOSED', 'LUXO'),
                                                   (215, 'DISPOSED', 'LUXO');

-- Inserindo quartos SUITE (10 quartos - IDs 301 a 310)
INSERT INTO rooms (number, status, room_type) VALUES
                                                   (301, 'DISPOSED', 'SUITE'),
                                                   (302, 'DISPOSED', 'SUITE'),
                                                   (303, 'DISPOSED', 'SUITE'),
                                                   (304, 'DISPOSED', 'SUITE'),
                                                   (305, 'DISPOSED', 'SUITE'),
                                                   (306, 'DISPOSED', 'SUITE'),
                                                   (307, 'DISPOSED', 'SUITE'),
                                                   (308, 'DISPOSED', 'SUITE'),
                                                   (309, 'DISPOSED', 'SUITE'),
                                                   (310, 'DISPOSED', 'SUITE');

-- Verificação dos dados inseridos
-- SELECT room_type, COUNT(*) as quantidade
-- FROM rooms
-- GROUP BY room_type
-- ORDER BY room_type;