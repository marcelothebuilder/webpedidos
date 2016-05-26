-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: webpedidos
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `categoria_pai_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `uk_categoria_nome` (`descricao`),
  KEY `fk_categoria_to_categoria_pai` (`categoria_pai_id`),
  CONSTRAINT `fk_categoria_to_categoria_pai` FOREIGN KEY (`categoria_pai_id`) REFERENCES `categoria` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cidade`
--

DROP TABLE IF EXISTS `cidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cidade` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `estado_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_cidade_to_estado` (`estado_codigo`),
  CONSTRAINT `fk_cidade_to_estado` FOREIGN KEY (`estado_codigo`) REFERENCES `estado` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `doc_receita_federal` varchar(14) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `tipo_pessoa` varchar(10) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `uk_doc_receita_federal` (`doc_receita_federal`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `cep` varchar(9) NOT NULL,
  `complemento` varchar(150) NOT NULL,
  `logradouro` varchar(150) NOT NULL,
  `numero` int(11) NOT NULL,
  `cidade_codigo` int(11) NOT NULL,
  `cliente_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_endereco_to_cidade` (`cidade_codigo`),
  KEY `fk_endereco_to_cliente` (`cliente_codigo`),
  CONSTRAINT `fk_endereco_to_cidade` FOREIGN KEY (`cidade_codigo`) REFERENCES `cidade` (`codigo`),
  CONSTRAINT `fk_endereco_to_cliente` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `sigla` varchar(2) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_pedido` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `valor_unitario` decimal(10,2) NOT NULL,
  `pedido_id` bigint(20) NOT NULL,
  `produto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_item_pedido_to_pedido` (`pedido_id`),
  KEY `fk_item_pedido_to_produto` (`produto_id`),
  CONSTRAINT `fk_item_pedido_to_pedido` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`codigo`),
  CONSTRAINT `fk_item_pedido_to_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime NOT NULL,
  `data_entrega` date NOT NULL,
  `entrega_cep` varchar(12) NOT NULL,
  `entrega_complemento` varchar(150) NOT NULL,
  `entrega_logradouro` varchar(150) NOT NULL,
  `entrega_numero` int(11) NOT NULL,
  `forma_pagamento` varchar(20) NOT NULL,
  `observacao` longtext,
  `status_pedido` varchar(20) NOT NULL,
  `valor_desconto` decimal(10,2) NOT NULL,
  `valor_frete` decimal(10,2) NOT NULL,
  `valor_total` decimal(10,2) NOT NULL,
  `cliente_codigo` bigint(20) NOT NULL,
  `entrega_cidade` int(11) NOT NULL,
  `vendedor_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_pedido_to_cliente` (`cliente_codigo`),
  KEY `fk_endereco_entrega_to_cidade` (`entrega_cidade`),
  KEY `fk_pedido_to_vendedor` (`vendedor_codigo`),
  CONSTRAINT `fk_endereco_entrega_to_cidade` FOREIGN KEY (`entrega_cidade`) REFERENCES `cidade` (`codigo`),
  CONSTRAINT `fk_pedido_to_cliente` FOREIGN KEY (`cliente_codigo`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `fk_pedido_to_vendedor` FOREIGN KEY (`vendedor_codigo`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `quantidade_estoque` int(11) NOT NULL,
  `sku` varchar(20) NOT NULL,
  `valor_unitario` decimal(10,2) NOT NULL,
  `categoria_id` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `uk_sku` (`sku`),
  KEY `fk_produto_to_categoria` (`categoria_id`),
  CONSTRAINT `fk_produto_to_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `email` varchar(254) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_grupo`
--

DROP TABLE IF EXISTS `usuario_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_grupo` (
  `usuario_id` bigint(20) NOT NULL,
  `grupo_id` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`,`grupo_id`),
  KEY `fk_usuario_grupo_to_grupo` (`grupo_id`),
  CONSTRAINT `fk_usuario_grupo_to_grupo` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`codigo`),
  CONSTRAINT `fk_usuario_grupo_to_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'webpedidos'
--

--
-- Dumping routines for database 'webpedidos'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-25 20:40:23
