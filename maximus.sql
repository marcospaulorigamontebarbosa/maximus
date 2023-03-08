-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 08-Mar-2023 às 20:23
-- Versão do servidor: 10.4.11-MariaDB
-- versão do PHP: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `maximus`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `acessos`
--

CREATE TABLE `acessos` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `acessos`
--

INSERT INTO `acessos` (`id`, `login`, `senha`) VALUES
(1, 'admin', '123456');

-- --------------------------------------------------------

--
-- Estrutura da tabela `avaliacoes`
--

CREATE TABLE `avaliacoes` (
  `id` int(11) NOT NULL,
  `nota` int(11) NOT NULL,
  `id_prestador_servico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `avaliacoes`
--

INSERT INTO `avaliacoes` (`id`, `nota`, `id_prestador_servico`) VALUES
(1, 5, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `descricao` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `categorias`
--

INSERT INTO `categorias` (`id`, `descricao`) VALUES
(1, 'Automativo - Guincho'),
(2, 'Automotivo - Borracheiro'),
(3, 'Automotivo - Mecânico'),
(4, 'Automotivo - Auto Peças'),
(5, 'Automotivo - Chaveiro'),
(6, 'Residencial - Pintor'),
(7, 'Residencial - Pedreiro'),
(8, 'Residencial - Encandador'),
(9, 'Residencial - Chaveiro'),
(10, 'Residencial - Eletricista'),
(11, 'Residencial - Diarista'),
(12, 'Residencial - Jardineiro'),
(13, 'Residencial - Passeador de Cachorro'),
(14, 'Saude - Cuidador de Idoso'),
(15, 'Saude - Acompanhante Hospitalar'),
(16, 'Saude - Esteticista'),
(17, 'Saude - Personal Trainer'),
(18, 'Saude - Babá'),
(19, 'Educação - Professor de Idiomas'),
(20, 'Educação - Professor de Português'),
(21, 'Educação - Professor de Matemática'),
(22, 'Educação - Professor de Geografia'),
(23, 'Educação - Professor de Ciêcias'),
(24, 'Transportes - Vans Escolares'),
(25, 'Transportes - Vans Viagens'),
(26, 'Transportes - Motorista Particular'),
(27, 'Advocacia - Advogado Civil'),
(28, 'Advocacia - Advogado Trabalhista'),
(29, 'Advocacia - Advogado Criminal'),
(30, 'Advocacia - Advogado Familiar'),
(31, 'Advocacia - Advogado Penal'),
(32, 'Advocacia - Advogado empresarial'),
(33, 'Advocacia - Advogado Previdencial');

-- --------------------------------------------------------

--
-- Estrutura da tabela `prestadores_servico`
--

CREATE TABLE `prestadores_servico` (
  `id` int(11) NOT NULL,
  `nome_completo` varchar(50) NOT NULL,
  `cpf_cnpj` varchar(14) NOT NULL,
  `data_nascimento` varchar(11) NOT NULL,
  `id_tipo` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_acesso` int(11) NOT NULL,
  `telefone` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `prestadores_servico`
--

INSERT INTO `prestadores_servico` (`id`, `nome_completo`, `cpf_cnpj`, `data_nascimento`, `id_tipo`, `id_categoria`, `id_acesso`, `telefone`) VALUES
(1, 'Oliver Rocha Dutra', '55714192000123', '2000-03-25', 2, 1, 1, '27985188045'),
(2, 'José Faria Pinto', '14374455005', '1965-06-19', 1, 2, 1, '27994753978'),
(3, 'João Paulo Menezes', '98982080000156', '1990-02-05', 2, 7, 1, '27996136898'),
(4, 'Jacinto Pereira Silva', '99495530000135', '1992-01-12', 2, 9, 1, '27986457303'),
(5, 'Luiz Alves Dacota', '96265470000167', '1985-02-02', 2, 14, 1, '27985865097'),
(6, 'Jefferson Robson Gomes', '39238479000153', '1988-02-06', 2, 17, 1, '27996219175'),
(7, 'Maitê Bianca Mariane Gomes', '60667332909', '1983-08-23', 1, 19, 1, '27951628312'),
(8, 'Carlos Eduardo Ruan Pietro Nascimento', '88947466074', '1992-12-12', 1, 22, 1, '27985188076'),
(9, 'Marcos José Melo', '98200804792', '1991-04-10', 1, 24, 1, '27961118723'),
(10, 'Larissa Nina Fogaça', '71661604117', '1985-09-09', 1, 26, 1, '27994136845'),
(11, 'Mateus de Alcantara', '70566943000161', '1997-10-12', 2, 29, 1, '27911825151'),
(12, 'Maria Cremasco', '97804641000130', '1990-05-20', 2, 30, 1, '27981822021');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipos_prestador`
--

CREATE TABLE `tipos_prestador` (
  `id` int(11) NOT NULL,
  `descricao` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tipos_prestador`
--

INSERT INTO `tipos_prestador` (`id`, `descricao`) VALUES
(1, 'Pessoa Física'),
(2, 'Pessoa Jurídica');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `acessos`
--
ALTER TABLE `acessos`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `avaliacoes`
--
ALTER TABLE `avaliacoes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_prestador_servico` (`id_prestador_servico`);

--
-- Índices para tabela `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `prestadores_servico`
--
ALTER TABLE `prestadores_servico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_acesso` (`id_acesso`),
  ADD KEY `id_categoria` (`id_categoria`),
  ADD KEY `id_tipo` (`id_tipo`);

--
-- Índices para tabela `tipos_prestador`
--
ALTER TABLE `tipos_prestador`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `acessos`
--
ALTER TABLE `acessos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `avaliacoes`
--
ALTER TABLE `avaliacoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de tabela `prestadores_servico`
--
ALTER TABLE `prestadores_servico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de tabela `tipos_prestador`
--
ALTER TABLE `tipos_prestador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `avaliacoes`
--
ALTER TABLE `avaliacoes`
  ADD CONSTRAINT `avaliacoes_ibfk_1` FOREIGN KEY (`id_prestador_servico`) REFERENCES `prestadores_servico` (`id`);

--
-- Limitadores para a tabela `prestadores_servico`
--
ALTER TABLE `prestadores_servico`
  ADD CONSTRAINT `prestadores_servico_ibfk_1` FOREIGN KEY (`id_acesso`) REFERENCES `acessos` (`id`),
  ADD CONSTRAINT `prestadores_servico_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`),
  ADD CONSTRAINT `prestadores_servico_ibfk_3` FOREIGN KEY (`id_tipo`) REFERENCES `tipos_prestador` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
