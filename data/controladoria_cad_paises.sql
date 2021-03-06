-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: controladoria
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `cad_paises`
--

DROP TABLE IF EXISTS `cad_paises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cad_paises` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cod_siscomex` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `cod_siscomex_UNIQUE` (`cod_siscomex`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cad_paises`
--

LOCK TABLES `cad_paises` WRITE;
/*!40000 ALTER TABLE `cad_paises` DISABLE KEYS */;
INSERT INTO `cad_paises` VALUES (1,132,'AFEGANISTAO\r'),(2,175,'ALBANIA, REPUBLICA  DA\r'),(3,230,'ALEMANHA\r'),(4,310,'BURKINA FASO\r'),(5,370,'ANDORRA\r'),(6,400,'ANGOLA\r'),(7,418,'ANGUILLA\r'),(8,434,'ANTIGUA E BARBUDA\r'),(9,477,'ANTILHAS HOLANDESAS\r'),(10,531,'ARABIA SAUDITA\r'),(11,590,'ARGELIA\r'),(12,639,'ARGENTINA\r'),(13,647,'ARMENIA, REPUBLICA DA\r'),(14,655,'ARUBA\r'),(15,698,'AUSTRALIA\r'),(16,728,'AUSTRIA\r'),(17,736,'AZERBAIJAO, REPUBLICA DO\r'),(18,779,'BAHAMAS, ILHAS\r'),(19,809,'BAHREIN, ILHAS\r'),(20,817,'BANGLADESH\r'),(21,833,'BARBADOS\r'),(22,850,'BELARUS, REPUBLICA DA\r'),(23,876,'BELGICA\r'),(24,884,'BELIZE\r'),(25,906,'BERMUDAS\r'),(26,930,'MIANMAR (BIRMANIA)\r'),(27,973,'BOLIVIA, ESTADO PLURINACIONAL DA\r'),(28,981,'BOSNIA-HERZEGOVINA (REPUBLICA DA)\r'),(29,1015,'BOTSUANA\r'),(30,1058,'BRASIL\r'),(31,1082,'BRUNEI\r'),(32,1112,'BULGARIA, REPUBLICA DA\r'),(33,1155,'BURUNDI\r'),(34,1198,'BUTAO\r'),(35,1279,'CABO VERDE, REPUBLICA DE\r'),(36,1376,'CAYMAN, ILHAS\r'),(37,1414,'CAMBOJA\r'),(38,1457,'CAMAROES\r'),(39,1490,'CANADA\r'),(40,1504,'GUERNSEY, ILHA DO CANAL (INCLUI ALDERNEY E SA'),(41,1508,'JERSEY, ILHA DO CANAL\r'),(42,1511,'CANARIAS, ILHAS\r'),(43,1538,'CAZAQUISTAO, REPUBLICA DO\r'),(44,1546,'CATAR\r'),(45,1589,'CHILE\r'),(46,1600,'CHINA, REPUBLICA POPULAR\r'),(47,1619,'FORMOSA (TAIWAN)\r'),(48,1635,'CHIPRE\r'),(49,1651,'COCOS(KEELING),ILHAS\r'),(50,1694,'COLOMBIA\r'),(51,1732,'COMORES, ILHAS\r'),(52,1775,'CONGO\r'),(53,1830,'COOK, ILHAS\r'),(54,1872,'COREIA (DO NORTE), REP.POP.DEMOCRATICA\r'),(55,1902,'COREIA (DO SUL), REPUBLICA DA\r'),(56,1937,'COSTA DO MARFIM\r'),(57,1953,'CROACIA (REPUBLICA DA)\r'),(58,1961,'COSTA RICA\r'),(59,1988,'COVEITE\r'),(60,1996,'CUBA\r'),(61,2291,'BENIN\r'),(62,2321,'DINAMARCA\r'),(63,2356,'DOMINICA,ILHA\r'),(64,2399,'EQUADOR\r'),(65,2402,'EGITO\r'),(66,2437,'ERITREIA\r'),(67,2445,'EMIRADOS ARABES UNIDOS\r'),(68,2453,'ESPANHA\r'),(69,2461,'ESLOVENIA, REPUBLICA DA\r'),(70,2470,'ESLOVACA, REPUBLICA\r'),(71,2496,'ESTADOS UNIDOS\r'),(72,2518,'ESTONIA, REPUBLICA DA\r'),(73,2534,'ETIOPIA\r'),(74,2550,'FALKLAND (ILHAS MALVINAS)\r'),(75,2593,'FEROE, ILHAS\r'),(76,2674,'FILIPINAS\r'),(77,2712,'FINLANDIA\r'),(78,2755,'FRANCA\r'),(79,2810,'GABAO\r'),(80,2852,'GAMBIA\r'),(81,2895,'GANA\r'),(82,2917,'GEORGIA, REPUBLICA DA\r'),(83,2933,'GIBRALTAR\r'),(84,2976,'GRANADA\r'),(85,3018,'GRECIA\r'),(86,3050,'GROENLANDIA\r'),(87,3093,'GUADALUPE\r'),(88,3131,'GUAM\r'),(89,3174,'GUATEMALA\r'),(90,3255,'GUIANA FRANCESA\r'),(91,3298,'GUINE\r'),(92,3310,'GUINE-EQUATORIAL\r'),(93,3344,'GUINE-BISSAU\r'),(94,3379,'GUIANA\r'),(95,3417,'HAITI\r'),(96,3450,'HONDURAS\r'),(97,3514,'HONG KONG\r'),(98,3557,'HUNGRIA, REPUBLICA DA\r'),(99,3573,'IEMEN\r'),(100,3595,'MAN, ILHA DE\r'),(101,3611,'INDIA\r'),(102,3654,'INDONESIA\r'),(103,3697,'IRAQUE\r'),(104,3727,'IRA, REPUBLICA ISLAMICA DO\r'),(105,3751,'IRLANDA\r'),(106,3794,'ISLANDIA\r'),(107,3832,'ISRAEL\r'),(108,3867,'ITALIA\r'),(109,3913,'JAMAICA\r'),(110,3964,'JOHNSTON, ILHAS\r'),(111,3999,'JAPAO\r'),(112,4030,'JORDANIA\r'),(113,4111,'KIRIBATI\r'),(114,4200,'LAOS, REP.POP.DEMOCR.DO\r'),(115,4235,'LEBUAN,ILHAS\r'),(116,4260,'LESOTO\r'),(117,4278,'LETONIA, REPUBLICA DA\r'),(118,4316,'LIBANO\r'),(119,4340,'LIBERIA\r'),(120,4383,'LIBIA\r'),(121,4405,'LIECHTENSTEIN\r'),(122,4421,'LITUANIA, REPUBLICA DA\r'),(123,4456,'LUXEMBURGO\r'),(124,4472,'MACAU\r'),(125,4499,'MACEDONIA, ANT.REP.IUGOSLAVA\r'),(126,4502,'MADAGASCAR\r'),(127,4525,'MADEIRA, ILHA DA\r'),(128,4553,'MALASIA\r'),(129,4588,'MALAVI\r'),(130,4618,'MALDIVAS\r'),(131,4642,'MALI\r'),(132,4677,'MALTA\r'),(133,4723,'MARIANAS DO NORTE\r'),(134,4740,'MARROCOS\r'),(135,4766,'MARSHALL,ILHAS\r'),(136,4774,'MARTINICA\r'),(137,4855,'MAURICIO\r'),(138,4880,'MAURITANIA\r'),(139,4885,'MAYOTTE (ILHAS FRANCESAS)\r'),(140,4901,'MIDWAY, ILHAS\r'),(141,4936,'MEXICO\r'),(142,4944,'MOLDAVIA, REPUBLICA DA\r'),(143,4952,'MONACO\r'),(144,4979,'MONGOLIA\r'),(145,4985,'MONTENEGRO\r'),(146,4995,'MICRONESIA\r'),(147,5010,'MONTSERRAT,ILHAS\r'),(148,5053,'MOCAMBIQUE\r'),(149,5070,'NAMIBIA\r'),(150,5088,'NAURU\r'),(151,5118,'CHRISTMAS,ILHA (NAVIDAD)\r'),(152,5177,'NEPAL\r'),(153,5215,'NICARAGUA\r'),(154,5258,'NIGER\r'),(155,5282,'NIGERIA\r'),(156,5312,'NIUE,ILHA\r'),(157,5355,'NORFOLK,ILHA\r'),(158,5380,'NORUEGA\r'),(159,5428,'NOVA CALEDONIA\r'),(160,5452,'PAPUA NOVA GUINE\r'),(161,5487,'NOVA ZELANDIA\r'),(162,5517,'VANUATU\r'),(163,5568,'OMA\r'),(164,5665,'PACIFICO,ILHAS DO (POSSESSAO DOS EUA)\r'),(165,5738,'PAISES BAIXOS (HOLANDA)\r'),(166,5754,'PALAU\r'),(167,5762,'PAQUISTAO\r'),(168,5780,'PALESTINA\r'),(169,5800,'PANAMA\r'),(170,5860,'PARAGUAI\r'),(171,5894,'PERU\r'),(172,5932,'PITCAIRN,ILHA\r'),(173,5991,'POLINESIA FRANCESA\r'),(174,6033,'POLONIA, REPUBLICA DA\r'),(175,6076,'PORTUGAL\r'),(176,6114,'PORTO RICO\r'),(177,6238,'QUENIA\r'),(178,6254,'QUIRGUIZ, REPUBLICA\r'),(179,6289,'REINO UNIDO\r'),(180,6408,'REPUBLICA CENTRO-AFRICANA\r'),(181,6475,'REPUBLICA DOMINICANA\r'),(182,6602,'REUNIAO, ILHA\r'),(183,6653,'ZIMBABUE\r'),(184,6700,'ROMENIA\r'),(185,6750,'RUANDA\r'),(186,6769,'RUSSIA, FEDERACAO DA\r'),(187,6777,'SALOMAO, ILHAS\r'),(188,6858,'SAARA OCIDENTAL\r'),(189,6874,'EL SALVADOR\r'),(190,6904,'SAMOA\r'),(191,6912,'SAMOA AMERICANA\r'),(192,6955,'SAO CRISTOVAO E NEVES,ILHAS\r'),(193,6971,'SAN MARINO\r'),(194,7005,'SAO PEDRO E MIQUELON\r'),(195,7056,'SAO VICENTE E GRANADINAS\r'),(196,7102,'SANTA HELENA\r'),(197,7153,'SANTA LUCIA\r'),(198,7200,'SAO TOME E PRINCIPE, ILHAS\r'),(199,7285,'SENEGAL\r'),(200,7315,'SEYCHELLES\r'),(201,7358,'SERRA LEOA\r'),(202,7370,'SERVIA\r'),(203,7412,'CINGAPURA\r'),(204,7447,'SIRIA, REPUBLICA ARABE DA\r'),(205,7480,'SOMALIA\r'),(206,7501,'SRI LANKA\r'),(207,7544,'SUAZILANDIA\r'),(208,7560,'AFRICA DO SUL\r'),(209,7595,'SUDAO\r'),(210,7600,'SUDÃO DO SUL\r'),(211,7641,'SUECIA\r'),(212,7676,'SUICA\r'),(213,7706,'SURINAME\r'),(214,7722,'TADJIQUISTAO, REPUBLICA DO\r'),(215,7765,'TAILANDIA\r'),(216,7803,'TANZANIA, REP.UNIDA DA\r'),(217,7820,'TERRITORIO BRIT.OC.INDICO\r'),(218,7838,'DJIBUTI\r'),(219,7889,'CHADE\r'),(220,7919,'TCHECA, REPUBLICA\r'),(221,7951,'TIMOR LESTE\r'),(222,8001,'TOGO\r'),(223,8052,'TOQUELAU,ILHAS\r'),(224,8109,'TONGA\r'),(225,8150,'TRINIDAD E TOBAGO\r'),(226,8206,'TUNISIA\r'),(227,8230,'TURCAS E CAICOS,ILHAS\r'),(228,8249,'TURCOMENISTAO, REPUBLICA DO\r'),(229,8273,'TURQUIA\r'),(230,8281,'TUVALU\r'),(231,8311,'UCRANIA\r'),(232,8338,'UGANDA\r'),(233,8451,'URUGUAI\r'),(234,8478,'UZBEQUISTAO, REPUBLICA DO\r'),(235,8486,'VATICANO, EST.DA CIDADE DO\r'),(236,8508,'VENEZUELA\r'),(237,8583,'VIETNA\r'),(238,8630,'VIRGENS,ILHAS (BRITANICAS)\r'),(239,8664,'VIRGENS,ILHAS (E.U.A.)\r'),(240,8702,'FIJI\r'),(241,8737,'WAKE, ILHA\r'),(242,8885,'CONGO, REPUBLICA DEMOCRATICA DO\r'),(243,8907,'ZAMBIA\r');
/*!40000 ALTER TABLE `cad_paises` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-02  0:02:39
