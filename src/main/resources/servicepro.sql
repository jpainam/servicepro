-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 03, 2017 at 03:49 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `servicepro`
--

-- --------------------------------------------------------

--
-- Table structure for table `ambitions`
--

CREATE TABLE IF NOT EXISTS `ambitions` (
  `IDAMBITION` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDAMBITION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `ambitions`
--

INSERT INTO `ambitions` (`IDAMBITION`, `LIBELLE`) VALUES
(1, 'Fondamental'),
(2, 'Avancé'),
(3, 'Expert');

-- --------------------------------------------------------

--
-- Table structure for table `competences`
--

CREATE TABLE IF NOT EXISTS `competences` (
  `IDCOMPETENCE` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` text,
  `TYPE` char(3) DEFAULT NULL COMMENT 'CN = Connaissance, CP = Competence, CNP = les deux',
  `NIVEAU` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDCOMPETENCE`),
  KEY `NIVEAU` (`NIVEAU`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `competences`
--

INSERT INTO `competences` (`IDCOMPETENCE`, `DESCRIPTION`, `TYPE`, `NIVEAU`) VALUES
(1, 'Connaissance de CFAO, histoire, domaines d''activité', 'CN', 2),
(2, 'Connaissance de Renault Trucks, histoire, domaines d''activité', 'CP', 3),
(3, 'Connaitre et appliquer les règles de sécurité sur le lieu de travail', 'CN', 4),
(4, 'Connaitre et appliquer les procédures "5S" sur le lieu de travail', 'CNP', 3),
(5, 'Maitiser les bases de la mécanique', 'CNP', 1),
(6, 'Maitriser les principes des systèmes de base, moteur, électricité, transmission', 'CNP', 2);

-- --------------------------------------------------------

--
-- Table structure for table `competence_statut`
--

CREATE TABLE IF NOT EXISTS `competence_statut` (
  `STATUT` char(2) NOT NULL COMMENT 'EC = En cours, AC = A certifier; CE = certifie',
  `LIBELLE` varchar(15) NOT NULL,
  PRIMARY KEY (`STATUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `competence_statut`
--

INSERT INTO `competence_statut` (`STATUT`, `LIBELLE`) VALUES
('AC', 'A certifier'),
('CE', 'Certifié'),
('EC', 'En cours');

-- --------------------------------------------------------

--
-- Table structure for table `continents`
--

CREATE TABLE IF NOT EXISTS `continents` (
  `IDCONTIENT` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(20) NOT NULL,
  PRIMARY KEY (`IDCONTIENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `contrats`
--

CREATE TABLE IF NOT EXISTS `contrats` (
  `IDCONTRAT` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  `IDPOTENTIEL` int(11) NOT NULL,
  PRIMARY KEY (`IDCONTRAT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `contrats`
--

INSERT INTO `contrats` (`IDCONTRAT`, `LIBELLE`, `IDPOTENTIEL`) VALUES
(1, 'Contrat à durée déterminée (CDD)', 0),
(2, 'Contrat à durée indéterminée (CDI)', 0),
(3, 'Autres', 0);

-- --------------------------------------------------------

--
-- Table structure for table `etat_formation`
--

CREATE TABLE IF NOT EXISTS `etat_formation` (
  `IDETATFORMATION` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDETATFORMATION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `etat_formation`
--

INSERT INTO `etat_formation` (`IDETATFORMATION`, `LIBELLE`) VALUES
(1, 'Terminée'),
(2, 'Validée'),
(3, 'Préparation'),
(4, 'Annulée');

-- --------------------------------------------------------

--
-- Table structure for table `formateurs`
--

CREATE TABLE IF NOT EXISTS `formateurs` (
  `FORMATION` int(11) NOT NULL,
  `PERSONNEL` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`FORMATION`,`PERSONNEL`),
  KEY `formateurs_ibfk_2` (`PERSONNEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `formateurs`
--

INSERT INTO `formateurs` (`FORMATION`, `PERSONNEL`) VALUES
(1, 1),
(3, 1),
(1, 2),
(3, 2),
(2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `formations`
--

CREATE TABLE IF NOT EXISTS `formations` (
  `IDFORMATION` int(11) NOT NULL AUTO_INCREMENT,
  `CODEFORMATION` varchar(15) NOT NULL,
  `MODELE` int(11) DEFAULT NULL,
  `TITRE` varchar(150) NOT NULL,
  `DESCRIPTION` text,
  `ETATFORMATION` int(11) DEFAULT NULL,
  `TYPEFORMATION` int(11) DEFAULT NULL,
  `DATEDEBUT` date DEFAULT NULL,
  `DATEFIN` date DEFAULT NULL,
  PRIMARY KEY (`IDFORMATION`),
  KEY `MODELE` (`MODELE`),
  KEY `ETATFORMATION` (`ETATFORMATION`),
  KEY `TYPEFORMATION` (`TYPEFORMATION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `formations`
--

INSERT INTO `formations` (`IDFORMATION`, `CODEFORMATION`, `MODELE`, `TITRE`, `DESCRIPTION`, `ETATFORMATION`, `TYPEFORMATION`, `DATEDEBUT`, `DATEFIN`) VALUES
(1, 'TEST02', 4, 'Un testhrerh', 'Une description de test', 3, 2, '1919-11-21', '1970-11-13'),
(2, 'aui', 6, 'je suis ', 'qh obon', 3, 4, '1919-11-12', '1970-11-02'),
(3, 'eto', 4, 'je dois', 'brqvotoi qussi', 4, NULL, '2017-07-12', '2017-07-09'),
(7, '', 4, 'yugvhv', '', 3, 2, '2017-07-26', '2017-07-26'),
(8, 'INTROTO', 3, 'INTRODUCTION TOYOTA FORKLIFT CI & ELECTRIQUE', '', 1, 1, '2017-08-10', '2017-08-06');

-- --------------------------------------------------------

--
-- Table structure for table `formation_competence`
--

CREATE TABLE IF NOT EXISTS `formation_competence` (
  `FORMATION` int(11) NOT NULL,
  `COMPETENCE` int(11) NOT NULL,
  PRIMARY KEY (`FORMATION`,`COMPETENCE`),
  KEY `FORMATION` (`FORMATION`,`COMPETENCE`),
  KEY `COMPETENCE` (`COMPETENCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `formation_competence`
--

INSERT INTO `formation_competence` (`FORMATION`, `COMPETENCE`) VALUES
(1, 1),
(8, 1),
(1, 2),
(8, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(8, 6);

-- --------------------------------------------------------

--
-- Table structure for table `formation_personne`
--

CREATE TABLE IF NOT EXISTS `formation_personne` (
  `FORMATION` int(11) NOT NULL,
  `PERSONNE` int(11) NOT NULL,
  PRIMARY KEY (`FORMATION`,`PERSONNE`),
  KEY `FORMATION` (`FORMATION`,`PERSONNE`),
  KEY `PERSONNE` (`PERSONNE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `formation_personne`
--

INSERT INTO `formation_personne` (`FORMATION`, `PERSONNE`) VALUES
(8, 2),
(8, 3),
(2, 4),
(8, 4),
(2, 5),
(3, 5),
(8, 5),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 17),
(3, 18),
(3, 19),
(3, 20),
(3, 21),
(3, 22),
(3, 23),
(3, 24),
(3, 25),
(3, 26),
(3, 27),
(3, 31),
(3, 32),
(3, 33),
(3, 34),
(3, 35),
(3, 36),
(3, 37);

-- --------------------------------------------------------

--
-- Table structure for table `groupes`
--

CREATE TABLE IF NOT EXISTS `groupes` (
  `IDGROUPE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDGROUPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `groupes`
--

INSERT INTO `groupes` (`IDGROUPE`, `LIBELLE`) VALUES
(1, 'Centre de Formation'),
(2, 'RH'),
(3, 'Siège'),
(4, 'Filiale');

-- --------------------------------------------------------

--
-- Table structure for table `langue`
--

CREATE TABLE IF NOT EXISTS `langue` (
  `IDLANGUE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(100) NOT NULL,
  PRIMARY KEY (`IDLANGUE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `langue`
--

INSERT INTO `langue` (`IDLANGUE`, `LIBELLE`) VALUES
(1, 'Francais'),
(2, 'Anglais'),
(3, 'Espagnol');

-- --------------------------------------------------------

--
-- Table structure for table `langue_parlee`
--

CREATE TABLE IF NOT EXISTS `langue_parlee` (
  `IDPERS` int(11) NOT NULL,
  `IDLANGUE` int(11) NOT NULL,
  PRIMARY KEY (`IDPERS`,`IDLANGUE`),
  KEY `IDLANGUE` (`IDLANGUE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `modeles`
--

CREATE TABLE IF NOT EXISTS `modeles` (
  `IDMODELE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(150) NOT NULL,
  PRIMARY KEY (`IDMODELE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `modeles`
--

INSERT INTO `modeles` (`IDMODELE`, `LIBELLE`) VALUES
(1, 'CFAO Academy'),
(2, 'JCB Distance Learning'),
(3, 'Mécanique de base CFAO Academy'),
(4, 'Niveau Initial CFAO Academy (4 modules)'),
(5, 'JCB JDS'),
(6, 'Induction'),
(7, 'Introduction conduite engins'),
(8, 'JCB 3DX');

-- --------------------------------------------------------

--
-- Table structure for table `niveau`
--

CREATE TABLE IF NOT EXISTS `niveau` (
  `IDNIVEAU` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(100) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`IDNIVEAU`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `niveau`
--

INSERT INTO `niveau` (`IDNIVEAU`, `LIBELLE`, `id`) VALUES
(1, 'Initial', 0),
(2, 'Fondamental', 0),
(3, 'Avancé', 0),
(4, 'Expert', 0);

-- --------------------------------------------------------

--
-- Table structure for table `niveau_etude`
--

CREATE TABLE IF NOT EXISTS `niveau_etude` (
  `IDNIVEAUETUDE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(10) NOT NULL,
  PRIMARY KEY (`IDNIVEAUETUDE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Niveau d etude de l etudiant' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `niveau_service`
--

CREATE TABLE IF NOT EXISTS `niveau_service` (
  `IDNIVEAUSERVICE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(30) NOT NULL,
  PRIMARY KEY (`IDNIVEAUSERVICE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pays`
--

CREATE TABLE IF NOT EXISTS `pays` (
  `IDPAYS` int(11) NOT NULL AUTO_INCREMENT,
  `ISO` char(2) NOT NULL,
  `NAMEFR` varchar(80) NOT NULL,
  `NAMEEN` varchar(80) NOT NULL,
  `ISO3` char(3) DEFAULT NULL,
  `NUMCODE` smallint(6) DEFAULT NULL,
  `PHONECODE` int(5) NOT NULL,
  PRIMARY KEY (`IDPAYS`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=254 ;

--
-- Dumping data for table `pays`
--

INSERT INTO `pays` (`IDPAYS`, `ISO`, `NAMEFR`, `NAMEEN`, `ISO3`, `NUMCODE`, `PHONECODE`) VALUES
(1, 'AF', 'Afghanistan', 'Afghanistan', 'AFG', 4, 93),
(2, 'AL', 'Albanie', 'Albania', 'ALB', 8, 355),
(3, 'DZ', 'Algérie', 'Algeria', 'DZA', 12, 213),
(4, 'AS', 'Samoa Américaines', 'American Samoa', 'ASM', 16, 1684),
(5, 'AD', 'Andorre', 'Andorra', 'AND', 20, 376),
(6, 'AO', 'Angola', 'Angola', 'AGO', 24, 244),
(7, 'AI', 'Anguilla', 'Anguilla', 'AIA', 660, 1264),
(8, 'AQ', 'Antarctique', 'Antarctica', NULL, NULL, 0),
(9, 'AG', 'Antigua-et-Barbuda', 'Antigua and Barbuda', 'ATG', 28, 1268),
(10, 'AR', 'Argentine', 'Argentina', 'ARG', 32, 54),
(11, 'AM', 'Arménie', 'Armenia', 'ARM', 51, 374),
(12, 'AW', 'Aruba', 'Aruba', 'ABW', 533, 297),
(13, 'AU', 'Australie', 'Australia', 'AUS', 36, 61),
(14, 'AT', 'Autriche', 'Austria', 'AUT', 40, 43),
(15, 'AZ', 'Azerbaïdjan', 'Azerbaijan', 'AZE', 31, 994),
(16, 'BS', 'Bahamas', 'Bahamas', 'BHS', 44, 1242),
(17, 'BH', 'Bahreïn', 'Bahrain', 'BHR', 48, 973),
(18, 'BD', 'Bangladesh', 'Bangladesh', 'BGD', 50, 880),
(19, 'BB', 'Barbade', 'Barbados', 'BRB', 52, 1246),
(20, 'BY', 'Bélarus', 'Belarus', 'BLR', 112, 375),
(21, 'BE', 'Belgique', 'Belgium', 'BEL', 56, 32),
(22, 'BZ', 'Belize', 'Belize', 'BLZ', 84, 501),
(23, 'BJ', 'Bénin', 'Benin', 'BEN', 204, 229),
(24, 'BM', 'Bermudes', 'Bermuda', 'BMU', 60, 1441),
(25, 'BT', 'Bhoutan', 'Bhutan', 'BTN', 64, 975),
(26, 'BO', 'Bolivie', 'Bolivia', 'BOL', 68, 591),
(27, 'BA', 'Bosnie-Herzégovine', 'Bosnia and Herzegovina', 'BIH', 70, 387),
(28, 'BW', 'Botswana', 'Botswana', 'BWA', 72, 267),
(29, 'BV', 'Île Bouvet', 'Bouvet Island', NULL, NULL, 0),
(30, 'BR', 'Brésil', 'Brazil', 'BRA', 76, 55),
(31, 'IO', 'Territoire Britannique de l''Océan Indien', 'British Indian Ocean Territory', NULL, NULL, 246),
(32, 'BN', 'Brunéi Darussalam', 'Brunei Darussalam', 'BRN', 96, 673),
(33, 'BG', 'Bulgarie', 'Bulgaria', 'BGR', 100, 359),
(34, 'BF', 'Burkina Faso', 'Burkina Faso', 'BFA', 854, 226),
(35, 'BI', 'Burundi', 'Burundi', 'BDI', 108, 257),
(36, 'KH', 'Cambodge', 'Cambodia', 'KHM', 116, 855),
(37, 'CM', 'Cameroun', 'Cameroon', 'CMR', 120, 237),
(38, 'CA', 'Canada', 'Canada', 'CAN', 124, 1),
(39, 'CV', 'Cap-vert', 'Cape Verde', 'CPV', 132, 238),
(40, 'KY', 'Îles Caïmanes', 'Cayman Islands', 'CYM', 136, 1345),
(41, 'CF', 'République Centrafricaine', 'Central African Republic', 'CAF', 140, 236),
(42, 'TD', 'Tchad', 'Chad', 'TCD', 148, 235),
(43, 'CL', 'Chili', 'Chile', 'CHL', 152, 56),
(44, 'CN', 'Chine', 'China', 'CHN', 156, 86),
(45, 'CX', 'Île Christmas', 'Christmas Island', NULL, NULL, 61),
(46, 'CC', 'Îles Cocos (Keeling)', 'Cocos (Keeling) Islands', NULL, NULL, 672),
(47, 'CO', 'Colombie', 'Colombia', 'COL', 170, 57),
(48, 'KM', 'Comores', 'Comoros', 'COM', 174, 269),
(49, 'CG', 'République du Congo', 'Congo', 'COG', 178, 242),
(50, 'CD', 'République Démocratique du Congo', 'Congo, the Democratic Republic of the', 'COD', 180, 243),
(51, 'CK', 'Îles Cook', 'Cook Islands', 'COK', 184, 682),
(52, 'CR', 'Costa Rica', 'Costa Rica', 'CRI', 188, 506),
(53, 'CI', 'Côte d''Ivoire', 'Cote D''Ivoire', 'CIV', 384, 225),
(54, 'HR', 'Croatie', 'Croatia', 'HRV', 191, 385),
(55, 'CU', 'Cuba', 'Cuba', 'CUB', 192, 53),
(56, 'CY', 'Chypre', 'Cyprus', 'CYP', 196, 357),
(57, 'CZ', 'République Tchèque', 'Czech Republic', 'CZE', 203, 420),
(58, 'DK', 'Danemark', 'Denmark', 'DNK', 208, 45),
(59, 'DJ', 'Djibouti', 'Djibouti', 'DJI', 262, 253),
(60, 'DM', 'Dominique', 'Dominica', 'DMA', 212, 1767),
(61, 'DO', 'République Dominicaine', 'Dominican Republic', 'DOM', 214, 1809),
(62, 'EC', 'Équateur', 'Ecuador', 'ECU', 218, 593),
(63, 'EG', 'Égypte', 'Egypt', 'EGY', 818, 20),
(64, 'SV', 'El Salvador', 'El Salvador', 'SLV', 222, 503),
(65, 'GQ', 'Guinée Équatoriale', 'Equatorial Guinea', 'GNQ', 226, 240),
(66, 'ER', 'Érythrée', 'Eritrea', 'ERI', 232, 291),
(67, 'EE', 'Estonie', 'Estonia', 'EST', 233, 372),
(68, 'ET', 'Éthiopie', 'Ethiopia', 'ETH', 231, 251),
(69, 'FK', 'Îles (malvinas) Falkland', 'Falkland Islands (Malvinas)', 'FLK', 238, 500),
(70, 'FO', 'Îles Féroé', 'Faroe Islands', 'FRO', 234, 298),
(71, 'FJ', 'Fidji', 'Fiji', 'FJI', 242, 679),
(72, 'FI', 'Finlande', 'Finland', 'FIN', 246, 358),
(73, 'FR', 'France', 'France', 'FRA', 250, 33),
(74, 'GF', 'Guyane Française', 'French Guiana', 'GUF', 254, 594),
(75, 'PF', 'Polynésie Française', 'French Polynesia', 'PYF', 258, 689),
(76, 'TF', 'Terres Australes Françaises', 'French Southern Territories', NULL, NULL, 0),
(77, 'GA', 'Gabon', 'Gabon', 'GAB', 266, 241),
(78, 'GM', 'Gambie', 'Gambia', 'GMB', 270, 220),
(79, 'GE', 'Géorgie', 'Georgia', 'GEO', 268, 995),
(80, 'DE', 'Allemagne', 'Germany', 'DEU', 276, 49),
(81, 'GH', 'Ghana', 'Ghana', 'GHA', 288, 233),
(82, 'GI', 'Gibraltar', 'Gibraltar', 'GIB', 292, 350),
(83, 'GR', 'Grèce', 'Greece', 'GRC', 300, 30),
(84, 'GL', 'Groenland', 'Greenland', 'GRL', 304, 299),
(85, 'GD', 'Grenade', 'Grenada', 'GRD', 308, 1473),
(86, 'GP', 'Guadeloupe', 'Guadeloupe', 'GLP', 312, 590),
(87, 'GU', 'Guam', 'Guam', 'GUM', 316, 1671),
(88, 'GT', 'Guatemala', 'Guatemala', 'GTM', 320, 502),
(89, 'GN', 'Guinée', 'Guinea', 'GIN', 324, 224),
(90, 'GW', 'Guinée-Bissau', 'Guinea-Bissau', 'GNB', 624, 245),
(91, 'GY', 'Guyana', 'Guyana', 'GUY', 328, 592),
(92, 'HT', 'Haïti', 'Haiti', 'HTI', 332, 509),
(93, 'HM', 'Îles Heard et Mcdonald', 'Heard Island and Mcdonald Islands', NULL, NULL, 0),
(94, 'VA', 'Saint-Siège (état de la Cité du Vatican)', 'Holy See (Vatican City State)', 'VAT', 336, 39),
(95, 'HN', 'Honduras', 'Honduras', 'HND', 340, 504),
(96, 'HK', 'Hong-Kong', 'Hong Kong', 'HKG', 344, 852),
(97, 'HU', 'Hongrie', 'Hungary', 'HUN', 348, 36),
(98, 'IS', 'Islande', 'Iceland', 'ISL', 352, 354),
(99, 'IN', 'Inde', 'India', 'IND', 356, 91),
(100, 'ID', 'Indonésie', 'Indonesia', 'IDN', 360, 62),
(101, 'IR', 'République Islamique d''Iran', 'Iran, Islamic Republic of', 'IRN', 364, 98),
(102, 'IQ', 'Iraq', 'Iraq', 'IRQ', 368, 964),
(103, 'IE', 'Irlande', 'Ireland', 'IRL', 372, 353),
(104, 'IL', 'Israël', 'Israel', 'ISR', 376, 972),
(105, 'IT', 'Italie', 'Italy', 'ITA', 380, 39),
(106, 'JM', 'Jamaïque', 'Jamaica', 'JAM', 388, 1876),
(107, 'JP', 'Japon', 'Japan', 'JPN', 392, 81),
(108, 'JO', 'Jordanie', 'Jordan', 'JOR', 400, 962),
(109, 'KZ', 'Kazakhstan', 'Kazakhstan', 'KAZ', 398, 7),
(110, 'KE', 'Kenya', 'Kenya', 'KEN', 404, 254),
(111, 'KI', 'Kiribati', 'Kiribati', 'KIR', 296, 686),
(112, 'KP', 'République Populaire Démocratique de Corée', 'Korea, Democratic People''s Republic of', 'PRK', 408, 850),
(113, 'KR', 'République de Corée', 'Korea, Republic of', 'KOR', 410, 82),
(114, 'KW', 'Koweït', 'Kuwait', 'KWT', 414, 965),
(115, 'KG', 'Kirghizistan', 'Kyrgyzstan', 'KGZ', 417, 996),
(116, 'LA', 'République Démocratique Populaire Lao', 'Lao People''s Democratic Republic', 'LAO', 418, 856),
(117, 'LV', 'Lettonie', 'Latvia', 'LVA', 428, 371),
(118, 'LB', 'Liban', 'Lebanon', 'LBN', 422, 961),
(119, 'LS', 'Lesotho', 'Lesotho', 'LSO', 426, 266),
(120, 'LR', 'Libéria', 'Liberia', 'LBR', 430, 231),
(121, 'LY', 'Jamahiriya Arabe Libyenne', 'Libyan Arab Jamahiriya', 'LBY', 434, 218),
(122, 'LI', 'Liechtenstein', 'Liechtenstein', 'LIE', 438, 423),
(123, 'LT', 'Lituanie', 'Lithuania', 'LTU', 440, 370),
(124, 'LU', 'Luxembourg', 'Luxembourg', 'LUX', 442, 352),
(125, 'MO', 'Macao', 'Macao', 'MAC', 446, 853),
(126, 'MK', 'L''ex-République Yougoslave de Macédoine', 'Macedonia, the Former Yugoslav Republic of', 'MKD', 807, 389),
(127, 'MG', 'Madagascar', 'Madagascar', 'MDG', 450, 261),
(128, 'MW', 'Malawi', 'Malawi', 'MWI', 454, 265),
(129, 'MY', 'Malaisie', 'Malaysia', 'MYS', 458, 60),
(130, 'MV', 'Maldives', 'Maldives', 'MDV', 462, 960),
(131, 'ML', 'Mali', 'Mali', 'MLI', 466, 223),
(132, 'MT', 'Malte', 'Malta', 'MLT', 470, 356),
(133, 'MH', 'Îles Marshall', 'Marshall Islands', 'MHL', 584, 692),
(134, 'MQ', 'Martinique', 'Martinique', 'MTQ', 474, 596),
(135, 'MR', 'Mauritanie', 'Mauritania', 'MRT', 478, 222),
(136, 'MU', 'Maurice', 'Mauritius', 'MUS', 480, 230),
(137, 'YT', 'Mayotte', 'Mayotte', NULL, NULL, 269),
(138, 'MX', 'Mexique', 'Mexico', 'MEX', 484, 52),
(139, 'FM', 'États Fédérés de Micronésie', 'Micronesia, Federated States of', 'FSM', 583, 691),
(140, 'MD', 'République de Moldova', 'Moldova, Republic of', 'MDA', 498, 373),
(141, 'MC', 'Monaco', 'Monaco', 'MCO', 492, 377),
(142, 'MN', 'Mongolie', 'Mongolia', 'MNG', 496, 976),
(143, 'MS', 'Montserrat', 'Montserrat', 'MSR', 500, 1664),
(144, 'MA', 'Maroc', 'Morocco', 'MAR', 504, 212),
(145, 'MZ', 'Mozambique', 'Mozambique', 'MOZ', 508, 258),
(146, 'MM', 'Myanmar', 'Myanmar', 'MMR', 104, 95),
(147, 'NA', 'Namibie', 'Namibia', 'NAM', 516, 264),
(148, 'NR', 'Nauru', 'Nauru', 'NRU', 520, 674),
(149, 'NP', 'Népal', 'Nepal', 'NPL', 524, 977),
(150, 'NL', 'Pays-Bas', 'Netherlands', 'NLD', 528, 31),
(151, 'AN', 'Antilles Néerlandaises', 'Netherlands Antilles', 'ANT', 530, 599),
(152, 'NC', 'Nouvelle-Calédonie', 'New Caledonia', 'NCL', 540, 687),
(153, 'NZ', 'Nouvelle-Zélande', 'New Zealand', 'NZL', 554, 64),
(154, 'NI', 'Nicaragua', 'Nicaragua', 'NIC', 558, 505),
(155, 'NE', 'Niger', 'Niger', 'NER', 562, 227),
(156, 'NG', 'Nigéria', 'Nigeria', 'NGA', 566, 234),
(157, 'NU', 'Niué', 'Niue', 'NIU', 570, 683),
(158, 'NF', 'Île Norfolk', 'Norfolk Island', 'NFK', 574, 672),
(159, 'MP', 'Îles Mariannes du Nord', 'Northern Mariana Islands', 'MNP', 580, 1670),
(160, 'NO', 'Norvège', 'Norway', 'NOR', 578, 47),
(161, 'OM', 'Oman', 'Oman', 'OMN', 512, 968),
(162, 'PK', 'Pakistan', 'Pakistan', 'PAK', 586, 92),
(163, 'PW', 'Palaos', 'Palau', 'PLW', 585, 680),
(164, 'PS', 'Territoire Palestinien Occupé', 'Palestinian Territory, Occupied', NULL, NULL, 970),
(165, 'PA', 'Panama', 'Panama', 'PAN', 591, 507),
(166, 'PG', 'Papouasie-Nouvelle-Guinée', 'Papua New Guinea', 'PNG', 598, 675),
(167, 'PY', 'Paraguay', 'Paraguay', 'PRY', 600, 595),
(168, 'PE', 'Pérou', 'Peru', 'PER', 604, 51),
(169, 'PH', 'Philippines', 'Philippines', 'PHL', 608, 63),
(170, 'PN', 'Pitcairn', 'Pitcairn', 'PCN', 612, 0),
(171, 'PL', 'Pologne', 'Poland', 'POL', 616, 48),
(172, 'PT', 'Portugal', 'Portugal', 'PRT', 620, 351),
(173, 'PR', 'Porto Rico', 'Puerto Rico', 'PRI', 630, 1787),
(174, 'QA', 'Qatar', 'Qatar', 'QAT', 634, 974),
(175, 'RE', 'Réunion', 'Reunion', 'REU', 638, 262),
(176, 'RO', 'Roumanie', 'Romania', 'ROM', 642, 40),
(177, 'RU', 'Fédération de Russie', 'Russian Federation', 'RUS', 643, 7),
(178, 'RW', 'Rwanda', 'Rwanda', 'RWA', 646, 250),
(179, 'SH', 'Sainte-Hélène', 'Saint Helena', 'SHN', 654, 290),
(180, 'KN', 'Saint-Kitts-et-Nevis', 'Saint Kitts and Nevis', 'KNA', 659, 1869),
(181, 'LC', 'Sainte-Lucie', 'Saint Lucia', 'LCA', 662, 1758),
(182, 'PM', 'Saint-Pierre-et-Miquelon', 'Saint Pierre and Miquelon', 'SPM', 666, 508),
(183, 'VC', 'Saint-Vincent-et-les Grenadines', 'Saint Vincent and the Grenadines', 'VCT', 670, 1784),
(184, 'WS', 'Samoa', 'Samoa', 'WSM', 882, 684),
(185, 'SM', 'Saint-Marin', 'San Marino', 'SMR', 674, 378),
(186, 'ST', 'Sao Tomé-et-Principe', 'Sao Tome and Principe', 'STP', 678, 239),
(187, 'SA', 'Arabie Saoudite', 'Saudi Arabia', 'SAU', 682, 966),
(188, 'SN', 'Sénégal', 'Senegal', 'SEN', 686, 221),
(190, 'SC', 'Seychelles', 'Seychelles', 'SYC', 690, 248),
(191, 'SL', 'Sierra Leone', 'Sierra Leone', 'SLE', 694, 232),
(192, 'SG', 'Singapour', 'Singapore', 'SGP', 702, 65),
(193, 'SK', 'Slovaquie', 'Slovakia', 'SVK', 703, 421),
(194, 'SI', 'Slovénie', 'Slovenia', 'SVN', 705, 386),
(195, 'SB', 'Îles Salomon', 'Solomon Islands', 'SLB', 90, 677),
(196, 'SO', 'Somalie', 'Somalia', 'SOM', 706, 252),
(197, 'ZA', 'Afrique du Sud', 'South Africa', 'ZAF', 710, 27),
(198, 'GS', 'Géorgie du Sud et les Îles Sandwich du Sud', 'South Georgia and the South Sandwich Islands', NULL, NULL, 0),
(199, 'ES', 'Espagne', 'Spain', 'ESP', 724, 34),
(200, 'LK', 'Sri Lanka', 'Sri Lanka', 'LKA', 144, 94),
(201, 'SD', 'Soudan', 'Sudan', 'SDN', 736, 249),
(202, 'SR', 'Suriname', 'Suriname', 'SUR', 740, 597),
(203, 'SJ', 'Svalbard etÎle Jan Mayen', 'Svalbard and Jan Mayen', 'SJM', 744, 47),
(204, 'SZ', 'Swaziland', 'Swaziland', 'SWZ', 748, 268),
(205, 'SE', 'Suède', 'Sweden', 'SWE', 752, 46),
(206, 'CH', 'Suisse', 'Switzerland', 'CHE', 756, 41),
(207, 'SY', 'République Arabe Syrienne', 'Syrian Arab Republic', 'SYR', 760, 963),
(208, 'TW', 'Taïwan', 'Taiwan, Province of China', 'TWN', 158, 886),
(209, 'TJ', 'Tadjikistan', 'Tajikistan', 'TJK', 762, 992),
(210, 'TZ', 'République-Unie de Tanzanie', 'Tanzania, United Republic of', 'TZA', 834, 255),
(211, 'TH', 'Thaïlande', 'Thailand', 'THA', 764, 66),
(212, 'TL', 'Timor-Leste', 'Timor-Leste', NULL, NULL, 670),
(213, 'TG', 'Togo', 'Togo', 'TGO', 768, 228),
(214, 'TK', 'Tokelau', 'Tokelau', 'TKL', 772, 690),
(215, 'TO', 'Tonga', 'Tonga', 'TON', 776, 676),
(216, 'TT', 'Trinité-et-Tobago', 'Trinidad and Tobago', 'TTO', 780, 1868),
(217, 'TN', 'Tunisie', 'Tunisia', 'TUN', 788, 216),
(218, 'TR', 'Turquie', 'Turkey', 'TUR', 792, 90),
(219, 'TM', 'Turkménistan', 'Turkmenistan', 'TKM', 795, 7370),
(220, 'TC', 'Îles Turks et Caïques', 'Turks and Caicos Islands', 'TCA', 796, 1649),
(221, 'TV', 'Tuvalu', 'Tuvalu', 'TUV', 798, 688),
(222, 'UG', 'Ouganda', 'Uganda', 'UGA', 800, 256),
(223, 'UA', 'Ukraine', 'Ukraine', 'UKR', 804, 380),
(224, 'AE', 'Émirats Arabes Unis', 'United Arab Emirates', 'ARE', 784, 971),
(225, 'GB', 'Royaume-Uni', 'United Kingdom', 'GBR', 826, 44),
(226, 'US', 'États-Unis', 'United States', 'USA', 840, 1),
(227, 'UM', 'Îles Mineures Éloignées des États-Unis', 'United States Minor Outlying Islands', NULL, NULL, 1),
(228, 'UY', 'Uruguay', 'Uruguay', 'URY', 858, 598),
(229, 'UZ', 'Ouzbékistan', 'Uzbekistan', 'UZB', 860, 998),
(230, 'VU', 'Vanuatu', 'Vanuatu', 'VUT', 548, 678),
(231, 'VE', 'Venezuela', 'Venezuela', 'VEN', 862, 58),
(232, 'VN', 'Viet Nam', 'Viet Nam', 'VNM', 704, 84),
(233, 'VG', 'Îles Vierges Britanniques', 'Virgin Islands, British', 'VGB', 92, 1284),
(234, 'VI', 'Îles Vierges des États-Unis', 'Virgin Islands, U.s.', 'VIR', 850, 1340),
(235, 'WF', 'Wallis et Futuna', 'Wallis and Futuna', 'WLF', 876, 681),
(236, 'EH', 'Sahara Occidental', 'Western Sahara', 'ESH', 732, 212),
(237, 'YE', 'Yémen', 'Yemen', 'YEM', 887, 967),
(238, 'ZM', 'Zambie', 'Zambia', 'ZMB', 894, 260),
(239, 'ZW', 'Zimbabwe', 'Zimbabwe', 'ZWE', 716, 263),
(240, 'RS', 'Serbie', 'Serbia', 'SRB', 688, 381),
(241, 'AP', 'Asie Région Pacifique', 'Asia / Pacific Region', '0', 0, 0),
(242, 'ME', 'Monténégro', 'Montenegro', 'MNE', 499, 382),
(243, 'AX', 'Îles Åland', 'Aland Islands', 'ALA', 248, 358),
(244, 'BQ', 'Bonaire, Sint Eustatius and Saba', 'Bonaire, Sint Eustatius and Saba', 'BES', 535, 599),
(245, 'CW', 'Curacao', 'Curacao', 'CUW', 531, 599),
(246, 'GG', 'Guernsey', 'Guernsey', 'GGY', 831, 44),
(247, 'IM', 'Île de Man', 'Isle of Man', 'IMN', 833, 44),
(248, 'JE', 'Jersey', 'Jersey', 'JEY', 832, 44),
(249, 'XK', 'Kosovo', 'Kosovo', '---', 0, 381),
(250, 'BL', 'Saint Barthelémie', 'Saint Barthelemy', 'BLM', 652, 590),
(251, 'MF', 'Saint Martin', 'Saint Martin', 'MAF', 663, 590),
(252, 'SX', 'Sint Maarten', 'Sint Maarten', 'SXM', 534, 1),
(253, 'SS', 'Soudan du Sud', 'South Sudan', 'SSD', 728, 211);

-- --------------------------------------------------------

--
-- Table structure for table `pays2`
--

CREATE TABLE IF NOT EXISTS `pays2` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(3) NOT NULL,
  `alpha2` varchar(2) NOT NULL,
  `alpha3` varchar(3) NOT NULL,
  `nom_en_gb` varchar(45) NOT NULL,
  `nom_fr_fr` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `alpha2` (`alpha2`),
  UNIQUE KEY `alpha3` (`alpha3`),
  UNIQUE KEY `code_unique` (`code`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=242 ;

--
-- Dumping data for table `pays2`
--

INSERT INTO `pays2` (`id`, `code`, `alpha2`, `alpha3`, `nom_en_gb`, `nom_fr_fr`) VALUES
(1, 4, 'AF', 'AFG', 'Afghanistan', 'Afghanistan'),
(2, 8, 'AL', 'ALB', 'Albania', 'Albanie'),
(3, 10, 'AQ', 'ATA', 'Antarctica', 'Antarctique'),
(4, 12, 'DZ', 'DZA', 'Algeria', 'Algérie'),
(5, 16, 'AS', 'ASM', 'American Samoa', 'Samoa Américaines'),
(6, 20, 'AD', 'AND', 'Andorra', 'Andorre'),
(7, 24, 'AO', 'AGO', 'Angola', 'Angola'),
(8, 28, 'AG', 'ATG', 'Antigua and Barbuda', 'Antigua-et-Barbuda'),
(9, 31, 'AZ', 'AZE', 'Azerbaijan', 'Azerbaïdjan'),
(10, 32, 'AR', 'ARG', 'Argentina', 'Argentine'),
(11, 36, 'AU', 'AUS', 'Australia', 'Australie'),
(12, 40, 'AT', 'AUT', 'Austria', 'Autriche'),
(13, 44, 'BS', 'BHS', 'Bahamas', 'Bahamas'),
(14, 48, 'BH', 'BHR', 'Bahrain', 'Bahreïn'),
(15, 50, 'BD', 'BGD', 'Bangladesh', 'Bangladesh'),
(16, 51, 'AM', 'ARM', 'Armenia', 'Arménie'),
(17, 52, 'BB', 'BRB', 'Barbados', 'Barbade'),
(18, 56, 'BE', 'BEL', 'Belgium', 'Belgique'),
(19, 60, 'BM', 'BMU', 'Bermuda', 'Bermudes'),
(20, 64, 'BT', 'BTN', 'Bhutan', 'Bhoutan'),
(21, 68, 'BO', 'BOL', 'Bolivia', 'Bolivie'),
(22, 70, 'BA', 'BIH', 'Bosnia and Herzegovina', 'Bosnie-Herzégovine'),
(23, 72, 'BW', 'BWA', 'Botswana', 'Botswana'),
(24, 74, 'BV', 'BVT', 'Bouvet Island', 'Île Bouvet'),
(25, 76, 'BR', 'BRA', 'Brazil', 'Brésil'),
(26, 84, 'BZ', 'BLZ', 'Belize', 'Belize'),
(27, 86, 'IO', 'IOT', 'British Indian Ocean Territory', 'Territoire Britannique de l''Océan Indien'),
(28, 90, 'SB', 'SLB', 'Solomon Islands', 'Îles Salomon'),
(29, 92, 'VG', 'VGB', 'British Virgin Islands', 'Îles Vierges Britanniques'),
(30, 96, 'BN', 'BRN', 'Brunei Darussalam', 'Brunéi Darussalam'),
(31, 100, 'BG', 'BGR', 'Bulgaria', 'Bulgarie'),
(32, 104, 'MM', 'MMR', 'Myanmar', 'Myanmar'),
(33, 108, 'BI', 'BDI', 'Burundi', 'Burundi'),
(34, 112, 'BY', 'BLR', 'Belarus', 'Bélarus'),
(35, 116, 'KH', 'KHM', 'Cambodia', 'Cambodge'),
(36, 120, 'CM', 'CMR', 'Cameroon', 'Cameroun'),
(37, 124, 'CA', 'CAN', 'Canada', 'Canada'),
(38, 132, 'CV', 'CPV', 'Cape Verde', 'Cap-vert'),
(39, 136, 'KY', 'CYM', 'Cayman Islands', 'Îles Caïmanes'),
(40, 140, 'CF', 'CAF', 'Central African', 'République Centrafricaine'),
(41, 144, 'LK', 'LKA', 'Sri Lanka', 'Sri Lanka'),
(42, 148, 'TD', 'TCD', 'Chad', 'Tchad'),
(43, 152, 'CL', 'CHL', 'Chile', 'Chili'),
(44, 156, 'CN', 'CHN', 'China', 'Chine'),
(45, 158, 'TW', 'TWN', 'Taiwan', 'Taïwan'),
(46, 162, 'CX', 'CXR', 'Christmas Island', 'Île Christmas'),
(47, 166, 'CC', 'CCK', 'Cocos (Keeling) Islands', 'Îles Cocos (Keeling)'),
(48, 170, 'CO', 'COL', 'Colombia', 'Colombie'),
(49, 174, 'KM', 'COM', 'Comoros', 'Comores'),
(50, 175, 'YT', 'MYT', 'Mayotte', 'Mayotte'),
(51, 178, 'CG', 'COG', 'Republic of the Congo', 'République du Congo'),
(52, 180, 'CD', 'COD', 'The Democratic Republic Of The Congo', 'République Démocratique du Congo'),
(53, 184, 'CK', 'COK', 'Cook Islands', 'Îles Cook'),
(54, 188, 'CR', 'CRI', 'Costa Rica', 'Costa Rica'),
(55, 191, 'HR', 'HRV', 'Croatia', 'Croatie'),
(56, 192, 'CU', 'CUB', 'Cuba', 'Cuba'),
(57, 196, 'CY', 'CYP', 'Cyprus', 'Chypre'),
(58, 203, 'CZ', 'CZE', 'Czech Republic', 'République Tchèque'),
(59, 204, 'BJ', 'BEN', 'Benin', 'Bénin'),
(60, 208, 'DK', 'DNK', 'Denmark', 'Danemark'),
(61, 212, 'DM', 'DMA', 'Dominica', 'Dominique'),
(62, 214, 'DO', 'DOM', 'Dominican Republic', 'République Dominicaine'),
(63, 218, 'EC', 'ECU', 'Ecuador', 'Équateur'),
(64, 222, 'SV', 'SLV', 'El Salvador', 'El Salvador'),
(65, 226, 'GQ', 'GNQ', 'Equatorial Guinea', 'Guinée Équatoriale'),
(66, 231, 'ET', 'ETH', 'Ethiopia', 'Éthiopie'),
(67, 232, 'ER', 'ERI', 'Eritrea', 'Érythrée'),
(68, 233, 'EE', 'EST', 'Estonia', 'Estonie'),
(69, 234, 'FO', 'FRO', 'Faroe Islands', 'Îles Féroé'),
(70, 238, 'FK', 'FLK', 'Falkland Islands', 'Îles (malvinas) Falkland'),
(71, 239, 'GS', 'SGS', 'South Georgia and the South Sandwich Islands', 'Géorgie du Sud et les Îles Sandwich du Sud'),
(72, 242, 'FJ', 'FJI', 'Fiji', 'Fidji'),
(73, 246, 'FI', 'FIN', 'Finland', 'Finlande'),
(74, 248, 'AX', 'ALA', 'Åland Islands', 'Îles Åland'),
(75, 250, 'FR', 'FRA', 'France', 'France'),
(76, 254, 'GF', 'GUF', 'French Guiana', 'Guyane Française'),
(77, 258, 'PF', 'PYF', 'French Polynesia', 'Polynésie Française'),
(78, 260, 'TF', 'ATF', 'French Southern Territories', 'Terres Australes Françaises'),
(79, 262, 'DJ', 'DJI', 'Djibouti', 'Djibouti'),
(80, 266, 'GA', 'GAB', 'Gabon', 'Gabon'),
(81, 268, 'GE', 'GEO', 'Georgia', 'Géorgie'),
(82, 270, 'GM', 'GMB', 'Gambia', 'Gambie'),
(83, 275, 'PS', 'PSE', 'Occupied Palestinian Territory', 'Territoire Palestinien Occupé'),
(84, 276, 'DE', 'DEU', 'Germany', 'Allemagne'),
(85, 288, 'GH', 'GHA', 'Ghana', 'Ghana'),
(86, 292, 'GI', 'GIB', 'Gibraltar', 'Gibraltar'),
(87, 296, 'KI', 'KIR', 'Kiribati', 'Kiribati'),
(88, 300, 'GR', 'GRC', 'Greece', 'Grèce'),
(89, 304, 'GL', 'GRL', 'Greenland', 'Groenland'),
(90, 308, 'GD', 'GRD', 'Grenada', 'Grenade'),
(91, 312, 'GP', 'GLP', 'Guadeloupe', 'Guadeloupe'),
(92, 316, 'GU', 'GUM', 'Guam', 'Guam'),
(93, 320, 'GT', 'GTM', 'Guatemala', 'Guatemala'),
(94, 324, 'GN', 'GIN', 'Guinea', 'Guinée'),
(95, 328, 'GY', 'GUY', 'Guyana', 'Guyana'),
(96, 332, 'HT', 'HTI', 'Haiti', 'Haïti'),
(97, 334, 'HM', 'HMD', 'Heard Island and McDonald Islands', 'Îles Heard et Mcdonald'),
(98, 336, 'VA', 'VAT', 'Vatican City State', 'Saint-Siège (état de la Cité du Vatican)'),
(99, 340, 'HN', 'HND', 'Honduras', 'Honduras'),
(100, 344, 'HK', 'HKG', 'Hong Kong', 'Hong-Kong'),
(101, 348, 'HU', 'HUN', 'Hungary', 'Hongrie'),
(102, 352, 'IS', 'ISL', 'Iceland', 'Islande'),
(103, 356, 'IN', 'IND', 'India', 'Inde'),
(104, 360, 'ID', 'IDN', 'Indonesia', 'Indonésie'),
(105, 364, 'IR', 'IRN', 'Islamic Republic of Iran', 'République Islamique d''Iran'),
(106, 368, 'IQ', 'IRQ', 'Iraq', 'Iraq'),
(107, 372, 'IE', 'IRL', 'Ireland', 'Irlande'),
(108, 376, 'IL', 'ISR', 'Israel', 'Israël'),
(109, 380, 'IT', 'ITA', 'Italy', 'Italie'),
(110, 384, 'CI', 'CIV', 'Côte d''Ivoire', 'Côte d''Ivoire'),
(111, 388, 'JM', 'JAM', 'Jamaica', 'Jamaïque'),
(112, 392, 'JP', 'JPN', 'Japan', 'Japon'),
(113, 398, 'KZ', 'KAZ', 'Kazakhstan', 'Kazakhstan'),
(114, 400, 'JO', 'JOR', 'Jordan', 'Jordanie'),
(115, 404, 'KE', 'KEN', 'Kenya', 'Kenya'),
(116, 408, 'KP', 'PRK', 'Democratic People''s Republic of Korea', 'République Populaire Démocratique de Corée'),
(117, 410, 'KR', 'KOR', 'Republic of Korea', 'République de Corée'),
(118, 414, 'KW', 'KWT', 'Kuwait', 'Koweït'),
(119, 417, 'KG', 'KGZ', 'Kyrgyzstan', 'Kirghizistan'),
(120, 418, 'LA', 'LAO', 'Lao People''s Democratic Republic', 'République Démocratique Populaire Lao'),
(121, 422, 'LB', 'LBN', 'Lebanon', 'Liban'),
(122, 426, 'LS', 'LSO', 'Lesotho', 'Lesotho'),
(123, 428, 'LV', 'LVA', 'Latvia', 'Lettonie'),
(124, 430, 'LR', 'LBR', 'Liberia', 'Libéria'),
(125, 434, 'LY', 'LBY', 'Libyan Arab Jamahiriya', 'Jamahiriya Arabe Libyenne'),
(126, 438, 'LI', 'LIE', 'Liechtenstein', 'Liechtenstein'),
(127, 440, 'LT', 'LTU', 'Lithuania', 'Lituanie'),
(128, 442, 'LU', 'LUX', 'Luxembourg', 'Luxembourg'),
(129, 446, 'MO', 'MAC', 'Macao', 'Macao'),
(130, 450, 'MG', 'MDG', 'Madagascar', 'Madagascar'),
(131, 454, 'MW', 'MWI', 'Malawi', 'Malawi'),
(132, 458, 'MY', 'MYS', 'Malaysia', 'Malaisie'),
(133, 462, 'MV', 'MDV', 'Maldives', 'Maldives'),
(134, 466, 'ML', 'MLI', 'Mali', 'Mali'),
(135, 470, 'MT', 'MLT', 'Malta', 'Malte'),
(136, 474, 'MQ', 'MTQ', 'Martinique', 'Martinique'),
(137, 478, 'MR', 'MRT', 'Mauritania', 'Mauritanie'),
(138, 480, 'MU', 'MUS', 'Mauritius', 'Maurice'),
(139, 484, 'MX', 'MEX', 'Mexico', 'Mexique'),
(140, 492, 'MC', 'MCO', 'Monaco', 'Monaco'),
(141, 496, 'MN', 'MNG', 'Mongolia', 'Mongolie'),
(142, 498, 'MD', 'MDA', 'Republic of Moldova', 'République de Moldova'),
(143, 500, 'MS', 'MSR', 'Montserrat', 'Montserrat'),
(144, 504, 'MA', 'MAR', 'Morocco', 'Maroc'),
(145, 508, 'MZ', 'MOZ', 'Mozambique', 'Mozambique'),
(146, 512, 'OM', 'OMN', 'Oman', 'Oman'),
(147, 516, 'NA', 'NAM', 'Namibia', 'Namibie'),
(148, 520, 'NR', 'NRU', 'Nauru', 'Nauru'),
(149, 524, 'NP', 'NPL', 'Nepal', 'Népal'),
(150, 528, 'NL', 'NLD', 'Netherlands', 'Pays-Bas'),
(151, 530, 'AN', 'ANT', 'Netherlands Antilles', 'Antilles Néerlandaises'),
(152, 533, 'AW', 'ABW', 'Aruba', 'Aruba'),
(153, 540, 'NC', 'NCL', 'New Caledonia', 'Nouvelle-Calédonie'),
(154, 548, 'VU', 'VUT', 'Vanuatu', 'Vanuatu'),
(155, 554, 'NZ', 'NZL', 'New Zealand', 'Nouvelle-Zélande'),
(156, 558, 'NI', 'NIC', 'Nicaragua', 'Nicaragua'),
(157, 562, 'NE', 'NER', 'Niger', 'Niger'),
(158, 566, 'NG', 'NGA', 'Nigeria', 'Nigéria'),
(159, 570, 'NU', 'NIU', 'Niue', 'Niué'),
(160, 574, 'NF', 'NFK', 'Norfolk Island', 'Île Norfolk'),
(161, 578, 'NO', 'NOR', 'Norway', 'Norvège'),
(162, 580, 'MP', 'MNP', 'Northern Mariana Islands', 'Îles Mariannes du Nord'),
(163, 581, 'UM', 'UMI', 'United States Minor Outlying Islands', 'Îles Mineures Éloignées des États-Unis'),
(164, 583, 'FM', 'FSM', 'Federated States of Micronesia', 'États Fédérés de Micronésie'),
(165, 584, 'MH', 'MHL', 'Marshall Islands', 'Îles Marshall'),
(166, 585, 'PW', 'PLW', 'Palau', 'Palaos'),
(167, 586, 'PK', 'PAK', 'Pakistan', 'Pakistan'),
(168, 591, 'PA', 'PAN', 'Panama', 'Panama'),
(169, 598, 'PG', 'PNG', 'Papua New Guinea', 'Papouasie-Nouvelle-Guinée'),
(170, 600, 'PY', 'PRY', 'Paraguay', 'Paraguay'),
(171, 604, 'PE', 'PER', 'Peru', 'Pérou'),
(172, 608, 'PH', 'PHL', 'Philippines', 'Philippines'),
(173, 612, 'PN', 'PCN', 'Pitcairn', 'Pitcairn'),
(174, 616, 'PL', 'POL', 'Poland', 'Pologne'),
(175, 620, 'PT', 'PRT', 'Portugal', 'Portugal'),
(176, 624, 'GW', 'GNB', 'Guinea-Bissau', 'Guinée-Bissau'),
(177, 626, 'TL', 'TLS', 'Timor-Leste', 'Timor-Leste'),
(178, 630, 'PR', 'PRI', 'Puerto Rico', 'Porto Rico'),
(179, 634, 'QA', 'QAT', 'Qatar', 'Qatar'),
(180, 638, 'RE', 'REU', 'Réunion', 'Réunion'),
(181, 642, 'RO', 'ROU', 'Romania', 'Roumanie'),
(182, 643, 'RU', 'RUS', 'Russian Federation', 'Fédération de Russie'),
(183, 646, 'RW', 'RWA', 'Rwanda', 'Rwanda'),
(184, 654, 'SH', 'SHN', 'Saint Helena', 'Sainte-Hélène'),
(185, 659, 'KN', 'KNA', 'Saint Kitts and Nevis', 'Saint-Kitts-et-Nevis'),
(186, 660, 'AI', 'AIA', 'Anguilla', 'Anguilla'),
(187, 662, 'LC', 'LCA', 'Saint Lucia', 'Sainte-Lucie'),
(188, 666, 'PM', 'SPM', 'Saint-Pierre and Miquelon', 'Saint-Pierre-et-Miquelon'),
(189, 670, 'VC', 'VCT', 'Saint Vincent and the Grenadines', 'Saint-Vincent-et-les Grenadines'),
(190, 674, 'SM', 'SMR', 'San Marino', 'Saint-Marin'),
(191, 678, 'ST', 'STP', 'Sao Tome and Principe', 'Sao Tomé-et-Principe'),
(192, 682, 'SA', 'SAU', 'Saudi Arabia', 'Arabie Saoudite'),
(193, 686, 'SN', 'SEN', 'Senegal', 'Sénégal'),
(194, 690, 'SC', 'SYC', 'Seychelles', 'Seychelles'),
(195, 694, 'SL', 'SLE', 'Sierra Leone', 'Sierra Leone'),
(196, 702, 'SG', 'SGP', 'Singapore', 'Singapour'),
(197, 703, 'SK', 'SVK', 'Slovakia', 'Slovaquie'),
(198, 704, 'VN', 'VNM', 'Vietnam', 'Viet Nam'),
(199, 705, 'SI', 'SVN', 'Slovenia', 'Slovénie'),
(200, 706, 'SO', 'SOM', 'Somalia', 'Somalie'),
(201, 710, 'ZA', 'ZAF', 'South Africa', 'Afrique du Sud'),
(202, 716, 'ZW', 'ZWE', 'Zimbabwe', 'Zimbabwe'),
(203, 724, 'ES', 'ESP', 'Spain', 'Espagne'),
(204, 732, 'EH', 'ESH', 'Western Sahara', 'Sahara Occidental'),
(205, 736, 'SD', 'SDN', 'Sudan', 'Soudan'),
(206, 740, 'SR', 'SUR', 'Suriname', 'Suriname'),
(207, 744, 'SJ', 'SJM', 'Svalbard and Jan Mayen', 'Svalbard etÎle Jan Mayen'),
(208, 748, 'SZ', 'SWZ', 'Swaziland', 'Swaziland'),
(209, 752, 'SE', 'SWE', 'Sweden', 'Suède'),
(210, 756, 'CH', 'CHE', 'Switzerland', 'Suisse'),
(211, 760, 'SY', 'SYR', 'Syrian Arab Republic', 'République Arabe Syrienne'),
(212, 762, 'TJ', 'TJK', 'Tajikistan', 'Tadjikistan'),
(213, 764, 'TH', 'THA', 'Thailand', 'Thaïlande'),
(214, 768, 'TG', 'TGO', 'Togo', 'Togo'),
(215, 772, 'TK', 'TKL', 'Tokelau', 'Tokelau'),
(216, 776, 'TO', 'TON', 'Tonga', 'Tonga'),
(217, 780, 'TT', 'TTO', 'Trinidad and Tobago', 'Trinité-et-Tobago'),
(218, 784, 'AE', 'ARE', 'United Arab Emirates', 'Émirats Arabes Unis'),
(219, 788, 'TN', 'TUN', 'Tunisia', 'Tunisie'),
(220, 792, 'TR', 'TUR', 'Turkey', 'Turquie'),
(221, 795, 'TM', 'TKM', 'Turkmenistan', 'Turkménistan'),
(222, 796, 'TC', 'TCA', 'Turks and Caicos Islands', 'Îles Turks et Caïques'),
(223, 798, 'TV', 'TUV', 'Tuvalu', 'Tuvalu'),
(224, 800, 'UG', 'UGA', 'Uganda', 'Ouganda'),
(225, 804, 'UA', 'UKR', 'Ukraine', 'Ukraine'),
(226, 807, 'MK', 'MKD', 'The Former Yugoslav Republic of Macedonia', 'L''ex-République Yougoslave de Macédoine'),
(227, 818, 'EG', 'EGY', 'Egypt', 'Égypte'),
(228, 826, 'GB', 'GBR', 'United Kingdom', 'Royaume-Uni'),
(229, 833, 'IM', 'IMN', 'Isle of Man', 'Île de Man'),
(230, 834, 'TZ', 'TZA', 'United Republic Of Tanzania', 'République-Unie de Tanzanie'),
(231, 840, 'US', 'USA', 'United States', 'États-Unis'),
(232, 850, 'VI', 'VIR', 'U.S. Virgin Islands', 'Îles Vierges des États-Unis'),
(233, 854, 'BF', 'BFA', 'Burkina Faso', 'Burkina Faso'),
(234, 858, 'UY', 'URY', 'Uruguay', 'Uruguay'),
(235, 860, 'UZ', 'UZB', 'Uzbekistan', 'Ouzbékistan'),
(236, 862, 'VE', 'VEN', 'Venezuela', 'Venezuela'),
(237, 876, 'WF', 'WLF', 'Wallis and Futuna', 'Wallis et Futuna'),
(238, 882, 'WS', 'WSM', 'Samoa', 'Samoa'),
(239, 887, 'YE', 'YEM', 'Yemen', 'Yémen'),
(240, 891, 'CS', 'SCG', 'Serbia and Montenegro', 'Serbie-et-Monténégro'),
(241, 894, 'ZM', 'ZMB', 'Zambia', 'Zambie');

-- --------------------------------------------------------

--
-- Table structure for table `personnels`
--

CREATE TABLE IF NOT EXISTS `personnels` (
  `IDPERSONNEL` int(11) NOT NULL AUTO_INCREMENT,
  `NOM` varchar(30) NOT NULL,
  `PRENOM` varchar(30) DEFAULT NULL,
  `ADRESSE` varchar(50) DEFAULT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDPERSONNEL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `personnels`
--

INSERT INTO `personnels` (`IDPERSONNEL`, `NOM`, `PRENOM`, `ADRESSE`, `TELEPHONE`) VALUES
(1, 'Ainam', 'Jean-Paul', 'Nanga-Eboko', NULL),
(2, 'Armel', 'Kadje', 'Une adresse', NULL),
(3, 'Un Personnel', 'prenom personnel', 'adresse du personne', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `personnes`
--

CREATE TABLE IF NOT EXISTS `personnes` (
  `IDPERSONNE` int(11) NOT NULL AUTO_INCREMENT,
  `MATRICULE` varchar(15) NOT NULL,
  `NOM` varchar(30) NOT NULL,
  `PRENOM` varchar(30) NOT NULL,
  `AUTRENOM` varchar(30) DEFAULT NULL,
  `DATENAISS` date NOT NULL,
  `TELEPHONE` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `PAYS` int(11) DEFAULT NULL,
  `GROUPE` int(11) DEFAULT NULL,
  `SOCIETE` int(11) DEFAULT NULL,
  `SECTION` int(11) DEFAULT NULL,
  `CONTRAT` int(11) DEFAULT NULL,
  `AMBITION` int(11) DEFAULT NULL,
  `LANGUE` int(11) DEFAULT NULL,
  `DATECONTRAT` date DEFAULT NULL,
  `MEMO` varchar(255) DEFAULT NULL,
  `POTENTIEL` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDPERSONNE`),
  KEY `PAYS` (`PAYS`),
  KEY `GROUPE` (`GROUPE`),
  KEY `SOCIETE` (`SOCIETE`),
  KEY `SECTION` (`SECTION`),
  KEY `FK_contrat` (`CONTRAT`),
  KEY `LANGUE` (`LANGUE`),
  KEY `AMBITION` (`AMBITION`),
  KEY `FK_sn7joh98p4rajw7bflfj6fx91` (`POTENTIEL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1786 ;

--
-- Dumping data for table `personnes`
--

INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `TELEPHONE`, `EMAIL`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`, `DATECONTRAT`, `MEMO`, `POTENTIEL`) VALUES
(2, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 'telllese', 'a,il', 18, 2, 2, 4, 3, 3, 1, NULL, NULL, NULL),
(3, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 'yrdy', NULL, 14, 1, 3, 3, 2, 1, 3, NULL, NULL, NULL),
(4, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 1, 4, 4, 2, 3, 3, NULL, NULL, NULL),
(5, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 11, 5, 3, 2, 2, NULL, NULL, NULL),
(8, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 3, 13, 4, 3, 2, 1, NULL, NULL, NULL),
(10, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(11, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(12, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(13, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(14, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 'hrtre', NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(17, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(18, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(19, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(20, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(21, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(22, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(23, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(24, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(25, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(26, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, '2017-07-23', NULL, 2),
(27, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(31, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(32, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(33, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(34, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(35, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(36, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(37, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(38, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(39, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(40, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(41, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(42, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(43, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(44, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(45, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(46, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(47, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(48, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(49, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(50, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(51, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(52, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(53, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(54, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(62, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(63, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(64, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(65, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(66, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(67, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(68, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(69, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(70, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(71, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(72, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(73, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(74, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(75, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(76, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(77, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(78, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(79, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(80, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(81, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(82, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(83, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(84, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(85, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(86, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(87, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(88, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(89, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(90, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(91, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(92, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(93, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(94, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(95, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(96, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(97, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(98, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(99, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(100, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(101, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(102, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(103, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(104, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(105, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(106, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(107, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(108, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(109, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(125, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(126, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(127, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(128, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(129, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(130, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(131, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(132, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(133, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(134, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(135, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(136, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(137, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(138, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(139, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(140, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(141, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(142, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(143, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(144, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(145, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(146, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(147, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(148, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(149, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(150, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(151, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(152, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(153, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(154, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(155, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(156, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(157, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(158, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(159, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(160, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(161, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(162, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(163, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(164, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(165, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(166, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(167, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(168, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(169, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(170, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(171, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(172, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(173, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(174, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(175, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(176, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(177, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(178, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(179, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(180, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(181, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(182, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(183, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(184, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(185, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(186, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(187, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(188, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(189, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(190, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(191, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(192, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(193, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(194, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(195, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(196, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(197, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(198, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(199, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(200, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(201, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(202, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(203, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(204, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(205, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(206, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(207, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(208, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(209, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(210, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(211, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(212, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(213, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(214, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(215, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(216, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(217, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(218, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(219, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(220, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(252, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(253, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(254, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(255, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(256, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(257, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(258, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(259, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(260, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(261, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(262, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(263, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(264, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(265, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(266, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(267, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(268, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(269, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(270, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(271, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(272, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(273, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(274, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(275, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(276, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(277, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(278, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(279, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(280, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(281, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(282, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(283, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(284, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(285, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(286, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(287, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(288, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(289, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(290, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(291, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(292, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(293, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(294, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(295, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(296, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(297, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(298, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(299, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(300, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(301, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(302, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(303, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(304, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(305, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(306, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(307, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(308, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(309, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(310, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(311, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(312, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(313, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(314, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(315, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(316, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(317, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(318, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(319, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(320, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(321, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(322, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(323, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(324, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(325, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(326, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(327, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(328, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(329, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(330, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(331, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(332, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(333, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(334, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(335, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(336, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(337, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(338, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(339, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(340, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(341, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(342, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(343, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(344, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(345, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(346, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(347, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(348, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(349, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(350, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(351, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(352, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(353, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(354, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(355, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(356, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(357, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(358, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(359, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(360, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(361, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(362, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(363, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(364, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(365, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(366, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(367, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(368, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(369, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(370, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(371, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(372, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(373, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(374, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(375, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(376, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(377, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(378, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(379, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(380, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(381, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(382, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(383, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(384, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(385, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(386, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(387, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(388, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(389, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(390, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(391, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(392, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(393, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(394, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(395, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(396, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(397, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(398, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(399, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(400, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(401, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(402, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(403, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(404, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(405, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(406, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(407, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(408, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(409, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(410, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(411, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(412, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(413, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(414, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(415, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(416, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(417, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(418, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(419, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(420, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(421, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(422, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(423, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(424, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(425, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(426, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(427, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(428, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(429, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(430, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(431, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(432, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(433, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(434, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(435, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(436, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(437, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(438, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(439, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(440, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(441, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(442, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(443, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(507, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(508, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(509, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(510, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(511, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(512, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(513, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(514, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(515, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(516, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(517, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(518, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(519, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(520, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(521, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(522, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(523, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(524, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(525, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(526, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(527, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(528, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(529, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(530, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(531, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(532, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(533, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(534, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(535, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(536, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(537, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(538, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(539, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(540, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(541, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(542, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(543, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(544, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(545, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(546, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(547, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(548, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(549, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(550, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(551, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(552, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(553, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(554, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(555, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(556, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(557, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(558, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(559, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(560, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(561, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(562, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(563, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(564, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(565, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(566, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(567, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(568, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(569, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(570, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(571, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(572, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(573, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(574, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(575, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(576, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(577, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(578, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(579, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(580, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(581, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL);
INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `TELEPHONE`, `EMAIL`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`, `DATECONTRAT`, `MEMO`, `POTENTIEL`) VALUES
(582, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(583, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(584, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(585, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(586, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(587, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(588, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(589, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(590, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(591, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(592, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(593, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(594, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(595, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(596, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(597, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(598, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(599, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(600, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(601, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(602, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(603, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(604, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(605, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(606, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(607, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(608, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(609, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(610, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(611, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(612, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(613, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(614, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(615, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(616, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(617, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(618, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(619, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(620, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(621, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(622, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(623, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(624, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(625, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(626, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(627, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(628, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(629, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(630, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(631, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(632, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(633, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(634, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(635, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(636, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(637, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(638, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(639, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(640, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(641, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(642, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(643, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(644, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(645, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(646, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(647, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(648, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(649, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(650, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(651, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(652, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(653, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(654, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(655, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(656, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(657, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(658, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(659, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(660, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(661, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(662, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(663, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(664, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(665, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(666, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(667, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(668, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(669, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(670, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(671, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(672, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(673, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(674, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(675, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(676, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(677, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(678, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(679, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(680, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(681, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(682, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(683, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(684, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(685, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(686, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(687, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(688, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(689, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(690, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(691, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(692, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(693, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(694, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(695, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(696, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(697, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(698, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(699, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(700, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(701, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(702, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(703, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(704, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(705, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(706, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(707, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(708, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(709, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(710, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(711, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(712, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(713, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(714, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(715, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(716, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(717, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(718, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(719, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(720, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(721, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(722, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(723, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(724, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(725, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(726, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(727, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(728, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(729, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(730, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(731, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(732, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(733, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(734, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(735, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(736, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(737, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(738, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(739, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(740, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(741, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(742, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(743, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(744, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(745, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(746, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(747, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(748, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(749, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(750, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(751, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(752, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(753, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(754, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(755, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(756, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(757, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(758, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(759, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(760, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(761, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(762, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(763, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(764, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(765, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(766, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(767, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(768, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(769, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(770, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(771, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(772, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(773, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(774, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(775, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(776, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(777, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(778, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(779, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(780, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(781, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(782, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(783, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(784, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(785, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(786, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(787, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(788, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(789, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(790, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(791, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(792, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(793, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(794, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(795, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(796, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(797, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(798, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(799, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(800, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(801, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(802, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(803, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(804, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(805, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(806, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(807, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(808, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(809, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(810, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(811, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(812, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(813, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(814, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(815, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(816, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(817, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(818, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(819, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(820, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(821, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(822, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(823, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(824, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(825, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(826, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(827, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(828, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(829, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(830, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(831, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(832, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(833, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(834, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(835, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(836, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(837, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(838, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(839, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(840, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(841, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(842, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(843, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(844, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(845, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(846, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(847, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(848, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(849, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(850, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(851, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(852, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(853, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(854, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(855, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(856, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(857, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(858, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(859, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(860, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(861, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(862, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(863, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(864, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(865, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(866, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(867, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(868, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(869, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(870, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(871, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(872, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(873, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(874, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(875, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(876, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(877, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(878, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(879, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(880, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(881, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(882, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(883, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(884, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(885, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(886, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(887, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(888, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(889, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(890, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1018, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1019, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1020, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1021, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1022, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1023, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1024, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1025, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1026, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1027, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1028, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1029, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1030, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1031, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1032, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1033, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1034, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1035, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1036, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1037, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1038, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1039, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1040, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1041, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1042, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1043, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1044, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1045, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1046, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1047, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1048, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1049, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1050, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1051, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1052, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1053, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1054, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1055, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1056, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1057, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1058, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1059, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1060, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1061, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1062, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1063, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1064, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1065, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1066, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1067, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1068, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1069, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1070, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1071, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1072, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1073, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1074, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1075, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1076, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1077, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1078, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1079, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1080, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1081, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1082, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1083, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1084, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1085, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1086, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1087, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1088, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1089, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1090, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1091, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1092, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1093, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1094, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1095, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1096, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1097, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1098, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1099, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1100, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1101, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1102, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1103, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1104, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1105, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1106, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1107, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1108, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1109, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1110, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1111, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1112, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1113, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1114, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1115, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1116, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1117, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1118, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1119, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1120, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1121, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1122, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1123, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1124, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1125, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1126, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1127, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1128, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1129, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1130, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1131, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1132, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1133, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1134, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1135, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1136, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1137, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1138, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1139, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1140, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1141, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1142, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1143, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1144, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1145, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1146, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1147, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1148, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1149, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1150, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1151, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1152, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1153, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1154, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1155, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1156, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1157, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1158, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1159, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1160, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1161, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1162, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL);
INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `TELEPHONE`, `EMAIL`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`, `DATECONTRAT`, `MEMO`, `POTENTIEL`) VALUES
(1163, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1164, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1165, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1166, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1167, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1168, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1169, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1170, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1171, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1172, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1173, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1174, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1175, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1176, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1177, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1178, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1179, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1180, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1181, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1182, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1183, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1184, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1185, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1186, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1187, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1188, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1189, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1190, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1191, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1192, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1193, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1194, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1195, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1196, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1197, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1198, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1199, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1200, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1201, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1202, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1203, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1204, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1205, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1206, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1207, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1208, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1209, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1210, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1211, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1212, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1213, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1214, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1215, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1216, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1217, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1218, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1219, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1220, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1221, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1222, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1223, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1224, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1225, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1226, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1227, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1228, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1229, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1230, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1231, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1232, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1233, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1234, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1235, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1236, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1237, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1238, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1239, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1240, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1241, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1242, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1243, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1244, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1245, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1246, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1247, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1248, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1249, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1250, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1251, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1252, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1253, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1254, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1255, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1256, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1257, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1258, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1259, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1260, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1261, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1262, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1263, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1264, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1265, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1266, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1267, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1268, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1269, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1270, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1271, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1272, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1273, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1274, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1275, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1276, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1277, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1278, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1279, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1280, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1281, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1282, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1283, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1284, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1285, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1286, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1287, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1288, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1289, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1290, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1291, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1292, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1293, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1294, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1295, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1296, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1297, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1298, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1299, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1300, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1301, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1302, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1303, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1304, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1305, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1306, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1307, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1308, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1309, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1310, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1311, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1312, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1313, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1314, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1315, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1316, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1317, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1318, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1319, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1320, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1321, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1322, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1323, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1324, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1325, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1326, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1327, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1328, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1329, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1330, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1331, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1332, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1333, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1334, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1335, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1336, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1337, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1338, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1339, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1340, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1341, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1342, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1343, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1344, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1345, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1346, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1347, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1348, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1349, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1350, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1351, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1352, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1353, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1354, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1355, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1356, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1357, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1358, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1359, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1360, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1361, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1362, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1363, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1364, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1365, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1366, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1367, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1368, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1369, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1370, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1371, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1372, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1373, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1374, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1375, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1376, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1377, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1378, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1379, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1380, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1381, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1382, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1383, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1384, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1385, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1386, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1387, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1388, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1389, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1390, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1391, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1392, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1393, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1394, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1395, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1396, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1397, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1398, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1399, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1400, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1401, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1402, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1403, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1404, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1405, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1406, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1407, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1408, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1409, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1410, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1411, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1412, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1413, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1414, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1415, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1416, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1417, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1418, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1419, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1420, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1421, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1422, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1423, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1424, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1425, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1426, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1427, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1428, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1429, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1430, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1431, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1432, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1433, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1434, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1435, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1436, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1437, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1438, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1439, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1440, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1441, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1442, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1443, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1444, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1445, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1446, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1447, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1448, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1449, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1450, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1451, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1452, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1453, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1454, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1455, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1456, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1457, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1458, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1459, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1460, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1461, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1462, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1463, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1464, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1465, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1466, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1467, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1468, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1469, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1470, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1471, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1472, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1473, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1474, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1475, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1476, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1477, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1478, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1479, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1480, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1481, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1482, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1483, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1484, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1485, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1486, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1487, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1488, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1489, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1490, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1491, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1492, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1493, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1494, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1495, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1496, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1497, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1498, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1499, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1500, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1501, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1502, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1503, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1504, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1505, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1506, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1507, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1508, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1509, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1510, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1511, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1512, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1513, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1514, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1515, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1516, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1517, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1518, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1519, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1520, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1521, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1522, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1523, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1524, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1525, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1526, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1527, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1528, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1529, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1530, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1531, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1532, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1533, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1534, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1535, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1536, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1537, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1538, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1539, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1540, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1541, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1542, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1543, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1544, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1545, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1546, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1547, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1548, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1549, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1550, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1551, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1552, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1553, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1554, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1555, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1556, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1557, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1558, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1559, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1560, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1561, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1562, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1563, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1564, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1565, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1566, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1567, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1568, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1569, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1570, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1571, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1572, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1573, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1574, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1575, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1576, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1577, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1578, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1579, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1580, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1581, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1582, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1583, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1584, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1585, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1586, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1587, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1588, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1589, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1590, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1591, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1592, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1593, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1594, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1595, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1596, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1597, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1598, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1599, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1600, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1601, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1602, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1603, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1604, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1605, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1606, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1607, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1608, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1609, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1610, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1611, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1612, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1613, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL);
INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `TELEPHONE`, `EMAIL`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`, `DATECONTRAT`, `MEMO`, `POTENTIEL`) VALUES
(1614, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1615, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1616, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1617, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1618, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1619, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1620, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1621, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1622, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1623, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1624, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1625, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1626, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1627, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1628, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1629, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1630, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1631, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1632, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1633, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1634, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1635, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1636, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1637, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1638, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1639, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1640, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1641, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1642, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1643, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1644, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1645, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1646, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1647, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1648, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1649, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1650, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1651, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1652, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1653, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1654, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1655, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1656, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1657, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1658, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1659, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1660, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1661, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1662, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1663, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1664, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1665, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1666, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1667, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1668, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1669, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1670, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1671, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1672, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1673, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1674, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1675, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1676, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1677, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1678, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1679, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1680, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1681, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1682, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1683, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1684, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1685, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1686, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1687, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1688, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1689, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1690, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1691, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1692, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1693, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1694, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1695, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1696, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1697, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1698, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1699, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1700, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1701, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1702, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1703, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1704, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1705, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1706, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1707, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1708, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1709, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1710, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1711, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1712, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1713, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1714, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1715, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1716, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1717, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1718, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1719, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1720, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1721, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1722, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1723, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1724, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1725, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1726, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1727, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1728, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1729, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1730, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1731, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1732, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1733, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1734, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1735, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1736, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1737, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1738, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1739, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1740, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1741, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1742, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1743, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1744, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1745, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1746, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1747, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1748, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1749, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1750, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1751, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1752, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1753, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1754, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1755, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1756, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1757, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1758, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1759, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1760, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1761, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1762, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1763, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1764, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1765, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1766, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1767, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1768, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1769, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1770, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1771, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1772, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1773, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1774, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1775, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1776, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1777, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1778, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1779, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1780, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', NULL, NULL, 130, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1781, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', NULL, NULL, 18, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1782, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', NULL, NULL, 14, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1783, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', NULL, NULL, 15, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1784, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', NULL, NULL, 20, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL),
(1785, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', NULL, NULL, 55, 2, 12, 3, 2, 3, 3, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `personne_competence`
--

CREATE TABLE IF NOT EXISTS `personne_competence` (
  `PERSONNE` int(11) NOT NULL,
  `COMPETENCE` int(11) NOT NULL,
  `CERTIFICATION` char(2) NOT NULL DEFAULT 'AC' COMMENT 'AC = a certifier; CE = certifier; EN = encours',
  `FORMATION` int(11) DEFAULT NULL,
  KEY `PERSONNE` (`PERSONNE`),
  KEY `COMPETENCE` (`COMPETENCE`),
  KEY `FORMATION` (`FORMATION`),
  KEY `CERTIFICATION` (`CERTIFICATION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `personne_qcm`
--

CREATE TABLE IF NOT EXISTS `personne_qcm` (
  `PERSONNE` int(11) NOT NULL,
  `QCM` int(11) NOT NULL,
  `NOTE` decimal(6,2) NOT NULL,
  PRIMARY KEY (`PERSONNE`,`QCM`,`NOTE`),
  KEY `QCM` (`QCM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `personne_qcm`
--

INSERT INTO `personne_qcm` (`PERSONNE`, `QCM`, `NOTE`) VALUES
(2, 1, '10.00'),
(2, 2, '10.00'),
(2, 2, '12.00'),
(3, 2, '12.00'),
(10, 2, '13.00'),
(2, 4, '19.00'),
(2, 6, '13.00');

-- --------------------------------------------------------

--
-- Table structure for table `postes`
--

CREATE TABLE IF NOT EXISTS `postes` (
  `IDPOSTE` int(11) NOT NULL AUTO_INCREMENT,
  `PERSONNE` int(11) NOT NULL,
  `SOCIETE` int(11) DEFAULT NULL,
  `TITRE` varchar(150) NOT NULL,
  `DATEDEBUT` date DEFAULT NULL,
  `DATEFIN` date DEFAULT NULL,
  PRIMARY KEY (`IDPOSTE`),
  KEY `PERSONNE` (`PERSONNE`),
  KEY `SOCIETE` (`SOCIETE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `postes`
--

INSERT INTO `postes` (`IDPOSTE`, `PERSONNE`, `SOCIETE`, `TITRE`, `DATEDEBUT`, `DATEFIN`) VALUES
(3, 3, 2, 'test', '2017-07-28', '2017-07-28');

-- --------------------------------------------------------

--
-- Table structure for table `potentiels`
--

CREATE TABLE IF NOT EXISTS `potentiels` (
  `IDPOTENTIEL` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDPOTENTIEL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `potentiels`
--

INSERT INTO `potentiels` (`IDPOTENTIEL`, `LIBELLE`) VALUES
(1, 'Aucun'),
(2, 'Faible'),
(3, 'Bon'),
(4, 'Talent');

-- --------------------------------------------------------

--
-- Table structure for table `prerequis`
--

CREATE TABLE IF NOT EXISTS `prerequis` (
  `COMPETENCE` int(11) NOT NULL,
  `FORMATION` int(11) NOT NULL,
  PRIMARY KEY (`COMPETENCE`,`FORMATION`),
  KEY `FORMATION` (`FORMATION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `profils`
--

CREATE TABLE IF NOT EXISTS `profils` (
  `IDPROFIL` int(11) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(15) DEFAULT NULL,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDPROFIL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `profils`
--

INSERT INTO `profils` (`IDPROFIL`, `ABBREVIATION`, `LIBELLE`) VALUES
(1, 'Tech RT', 'Technicien Renault Trucks'),
(2, 'Tech JCB', 'Technicien JCB Engins'),
(3, 'Tech Toyota MH', 'Technicien Toyota MH'),
(4, 'Tech GE', 'Technicien Groupes Electrogène JCB'),
(5, 'Recep Admin RT', 'Réceptionnaire atelier RT'),
(6, 'Recep Admin Ens', 'Réceptionnaire Engins RT'),
(7, 'Tech OTIS', 'Technicien OTIS');

-- --------------------------------------------------------

--
-- Table structure for table `profil_competence`
--

CREATE TABLE IF NOT EXISTS `profil_competence` (
  `COMPETENCE` int(11) NOT NULL,
  `PROFIL` int(11) NOT NULL,
  PRIMARY KEY (`COMPETENCE`,`PROFIL`),
  KEY `PROFIL` (`PROFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `profil_competence`
--

INSERT INTO `profil_competence` (`COMPETENCE`, `PROFIL`) VALUES
(3, 1),
(4, 1),
(4, 2),
(5, 2),
(1, 3),
(4, 3),
(5, 3),
(6, 3),
(2, 4),
(3, 4),
(5, 4),
(6, 4),
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(3, 6),
(4, 6),
(1, 7),
(2, 7),
(3, 7),
(4, 7);

-- --------------------------------------------------------

--
-- Table structure for table `profil_personne`
--

CREATE TABLE IF NOT EXISTS `profil_personne` (
  `PERSONNE` int(11) NOT NULL,
  `PROFIL` int(11) NOT NULL,
  `NIVEAU` int(11) NOT NULL,
  PRIMARY KEY (`PERSONNE`,`PROFIL`,`NIVEAU`) USING BTREE,
  KEY `fk_profil` (`PROFIL`),
  KEY `fk_niveau` (`NIVEAU`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `profil_personne`
--

INSERT INTO `profil_personne` (`PERSONNE`, `PROFIL`, `NIVEAU`) VALUES
(19, 1, 2),
(3, 2, 2),
(19, 2, 2),
(21, 2, 2),
(19, 4, 3),
(19, 5, 4);

-- --------------------------------------------------------

--
-- Table structure for table `qcm`
--

CREATE TABLE IF NOT EXISTS `qcm` (
  `IDQCM` int(11) NOT NULL AUTO_INCREMENT,
  `TITRE` varchar(250) NOT NULL,
  `TYPE` int(11) DEFAULT '1',
  `BASE` int(11) NOT NULL COMMENT 'sur 20 ou sur 100',
  PRIMARY KEY (`IDQCM`),
  KEY `TYPE` (`TYPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `qcm`
--

INSERT INTO `qcm` (`IDQCM`, `TITRE`, `TYPE`, `BASE`) VALUES
(1, 'Electricité initial', 1, 20),
(2, 'Hydraulique initial', 1, 20),
(3, 'Roulements', 2, 20),
(4, 'Moteur initial', 1, 20),
(5, 'Evaluation', 1, 20),
(6, 'Soudeur niveau 2', 3, 10);

-- --------------------------------------------------------

--
-- Table structure for table `qcm_competence`
--

CREATE TABLE IF NOT EXISTS `qcm_competence` (
  `QCM` int(11) NOT NULL,
  `COMPETENCE` int(11) NOT NULL,
  PRIMARY KEY (`QCM`,`COMPETENCE`),
  KEY `COMPETENCE` (`COMPETENCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `qcm_competence`
--

INSERT INTO `qcm_competence` (`QCM`, `COMPETENCE`) VALUES
(1, 2),
(1, 3),
(3, 3),
(4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `qcm_type`
--

CREATE TABLE IF NOT EXISTS `qcm_type` (
  `IDQCMTYPE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(30) NOT NULL,
  PRIMARY KEY (`IDQCMTYPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `qcm_type`
--

INSERT INTO `qcm_type` (`IDQCMTYPE`, `LIBELLE`) VALUES
(1, 'Quiz'),
(2, 'Test Out'),
(3, 'Test In');

-- --------------------------------------------------------

--
-- Table structure for table `sections`
--

CREATE TABLE IF NOT EXISTS `sections` (
  `IDSECTION` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(30) NOT NULL,
  PRIMARY KEY (`IDSECTION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `sections`
--

INSERT INTO `sections` (`IDSECTION`, `LIBELLE`) VALUES
(1, 'Matériel'),
(2, 'Ascenseurs'),
(3, 'Engins'),
(4, 'Trucks'),
(5, 'Groupes Electrogènes'),
(6, 'Ingénierie Bâtiment');

-- --------------------------------------------------------

--
-- Table structure for table `societes`
--

CREATE TABLE IF NOT EXISTS `societes` (
  `IDSOCIETE` int(11) NOT NULL AUTO_INCREMENT,
  `NOM` varchar(50) NOT NULL,
  `CODE` varchar(15) DEFAULT NULL,
  `CONTACT` varchar(100) DEFAULT NULL,
  `TELEPHONE` varchar(15) DEFAULT NULL,
  `EMAIL` varchar(30) DEFAULT NULL,
  `FAX` varchar(50) DEFAULT NULL,
  `ADRESSE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDSOCIETE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `societes`
--

INSERT INTO `societes` (`IDSOCIETE`, `NOM`, `CODE`, `CONTACT`, `TELEPHONE`, `EMAIL`, `FAX`, `ADRESSE`) VALUES
(1, 'CFAO Burkina', NULL, NULL, NULL, NULL, NULL, 'Burkina, Ouagadougou'),
(2, 'CFAO Cameroun', NULL, NULL, NULL, NULL, NULL, 'Cameroun, Douala'),
(3, 'CFAO Congo', NULL, NULL, NULL, NULL, NULL, 'Congo, Brazzaville'),
(4, 'CFAO Cote d''ivoire', NULL, NULL, NULL, NULL, NULL, NULL),
(5, 'CFAO Gabon', NULL, NULL, NULL, NULL, NULL, 'Gabon, Libreville'),
(6, 'CFAO Mali', NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'CFAO Nigéria', NULL, NULL, NULL, NULL, NULL, NULL),
(8, 'CFAO RDC', NULL, NULL, NULL, NULL, NULL, NULL),
(9, 'CFAO Tchad', NULL, NULL, NULL, NULL, NULL, NULL),
(10, 'CFAO Togo', NULL, NULL, NULL, NULL, NULL, NULL),
(11, 'CFAO Niger', NULL, NULL, NULL, NULL, NULL, NULL),
(12, 'CFAO RCA', NULL, NULL, NULL, NULL, NULL, NULL),
(13, 'CFAO Niger', NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `supports`
--

CREATE TABLE IF NOT EXISTS `supports` (
  `IDSUPPORT` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` char(8) NOT NULL,
  `TITRE` varchar(150) DEFAULT NULL,
  `LIEN` varchar(250) NOT NULL,
  PRIMARY KEY (`IDSUPPORT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `supports`
--

INSERT INTO `supports` (`IDSUPPORT`, `CODE`, `TITRE`, `LIEN`) VALUES
(1, 'un code', 'un titre', '\\documents\\fort.pdf'),
(2, 'un code', 'un titre', '\\documents\\fort.pdf'),
(3, 'tetst', 'tes', '\\documents\\thesis.pdf'),
(4, 'fort', 'fort', '\\documents\\transparent.png'),
(5, 'voilq', 'voilq', '\\documents\\xdebug.txt'),
(6, 'h', 'h', '\\documents\\java0.log');

-- --------------------------------------------------------

--
-- Table structure for table `support_formation`
--

CREATE TABLE IF NOT EXISTS `support_formation` (
  `SUPPORT` int(11) NOT NULL,
  `FORMATION` int(11) NOT NULL,
  KEY `SUPPORT` (`SUPPORT`,`FORMATION`),
  KEY `FORMATION` (`FORMATION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `support_formation`
--

INSERT INTO `support_formation` (`SUPPORT`, `FORMATION`) VALUES
(1, 3),
(3, 3),
(4, 3),
(5, 1),
(6, 2);

-- --------------------------------------------------------

--
-- Table structure for table `type_formation`
--

CREATE TABLE IF NOT EXISTS `type_formation` (
  `IDTYPEFORMATION` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(30) NOT NULL,
  PRIMARY KEY (`IDTYPEFORMATION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `type_formation`
--

INSERT INTO `type_formation` (`IDTYPEFORMATION`, `LIBELLE`) VALUES
(1, 'Tutorat'),
(2, 'Présentiel'),
(3, 'Formation à distance'),
(4, 'CFAO Academy');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `IDUSER` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN` varchar(30) NOT NULL,
  `PASSWORD` varchar(150) NOT NULL,
  `PROFILE` int(11) NOT NULL,
  PRIMARY KEY (`IDUSER`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`IDUSER`, `LOGIN`, `PASSWORD`, `PROFILE`) VALUES
(1, 'test', 'pwd', 1),
(2, 'test2', 'pasw2', 5);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `competences`
--
ALTER TABLE `competences`
  ADD CONSTRAINT `competences_ibfk_1` FOREIGN KEY (`NIVEAU`) REFERENCES `niveau` (`IDNIVEAU`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `formateurs`
--
ALTER TABLE `formateurs`
  ADD CONSTRAINT `formateurs_ibfk_1` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `formateurs_ibfk_2` FOREIGN KEY (`PERSONNEL`) REFERENCES `personnels` (`IDPERSONNEL`);

--
-- Constraints for table `formations`
--
ALTER TABLE `formations`
  ADD CONSTRAINT `FK_i72bqlp26nj7xingtf3rqi69r` FOREIGN KEY (`TYPEFORMATION`) REFERENCES `type_formation` (`IDTYPEFORMATION`),
  ADD CONSTRAINT `formations_ibfk_1` FOREIGN KEY (`MODELE`) REFERENCES `modeles` (`IDMODELE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `formations_ibfk_2` FOREIGN KEY (`ETATFORMATION`) REFERENCES `etat_formation` (`IDETATFORMATION`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `formation_competence`
--
ALTER TABLE `formation_competence`
  ADD CONSTRAINT `formation_competence_ibfk_1` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `formation_competence_ibfk_2` FOREIGN KEY (`COMPETENCE`) REFERENCES `competences` (`IDCOMPETENCE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `formation_personne`
--
ALTER TABLE `formation_personne`
  ADD CONSTRAINT `formation_personne_ibfk_1` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `formation_personne_ibfk_2` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `langue_parlee`
--
ALTER TABLE `langue_parlee`
  ADD CONSTRAINT `langue_parlee_ibfk_1` FOREIGN KEY (`IDPERS`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `langue_parlee_ibfk_2` FOREIGN KEY (`IDLANGUE`) REFERENCES `langue` (`IDLANGUE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `personnes`
--
ALTER TABLE `personnes`
  ADD CONSTRAINT `FK_contrat` FOREIGN KEY (`CONTRAT`) REFERENCES `contrats` (`IDCONTRAT`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_sn7joh98p4rajw7bflfj6fx91` FOREIGN KEY (`POTENTIEL`) REFERENCES `potentiels` (`IDPOTENTIEL`),
  ADD CONSTRAINT `personnes_ibfk_1` FOREIGN KEY (`PAYS`) REFERENCES `pays` (`IDPAYS`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_2` FOREIGN KEY (`GROUPE`) REFERENCES `groupes` (`IDGROUPE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_3` FOREIGN KEY (`SOCIETE`) REFERENCES `societes` (`IDSOCIETE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_4` FOREIGN KEY (`SECTION`) REFERENCES `sections` (`IDSECTION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_5` FOREIGN KEY (`AMBITION`) REFERENCES `ambitions` (`IDAMBITION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_6` FOREIGN KEY (`LANGUE`) REFERENCES `langue` (`IDLANGUE`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `personne_competence`
--
ALTER TABLE `personne_competence`
  ADD CONSTRAINT `personne_competence_ibfk_1` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_competence_ibfk_2` FOREIGN KEY (`COMPETENCE`) REFERENCES `competences` (`IDCOMPETENCE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_competence_ibfk_3` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_competence_ibfk_4` FOREIGN KEY (`CERTIFICATION`) REFERENCES `competence_statut` (`STATUT`);

--
-- Constraints for table `personne_qcm`
--
ALTER TABLE `personne_qcm`
  ADD CONSTRAINT `personne_qcm_ibfk_1` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_qcm_ibfk_2` FOREIGN KEY (`QCM`) REFERENCES `qcm` (`IDQCM`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `postes`
--
ALTER TABLE `postes`
  ADD CONSTRAINT `postes_ibfk_1` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `postes_ibfk_2` FOREIGN KEY (`SOCIETE`) REFERENCES `societes` (`IDSOCIETE`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `prerequis`
--
ALTER TABLE `prerequis`
  ADD CONSTRAINT `prerequis_ibfk_1` FOREIGN KEY (`COMPETENCE`) REFERENCES `competences` (`IDCOMPETENCE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `prerequis_ibfk_2` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `profil_competence`
--
ALTER TABLE `profil_competence`
  ADD CONSTRAINT `profil_competence_ibfk_1` FOREIGN KEY (`COMPETENCE`) REFERENCES `competences` (`IDCOMPETENCE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profil_competence_ibfk_2` FOREIGN KEY (`PROFIL`) REFERENCES `profils` (`IDPROFIL`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `profil_personne`
--
ALTER TABLE `profil_personne`
  ADD CONSTRAINT `fk_niveau` FOREIGN KEY (`NIVEAU`) REFERENCES `niveau` (`IDNIVEAU`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_personne` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profil` FOREIGN KEY (`PROFIL`) REFERENCES `profils` (`IDPROFIL`) ON DELETE CASCADE;

--
-- Constraints for table `qcm`
--
ALTER TABLE `qcm`
  ADD CONSTRAINT `qcm_ibfk_1` FOREIGN KEY (`TYPE`) REFERENCES `qcm_type` (`IDQCMTYPE`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `qcm_competence`
--
ALTER TABLE `qcm_competence`
  ADD CONSTRAINT `qcm_competence_ibfk_1` FOREIGN KEY (`QCM`) REFERENCES `qcm` (`IDQCM`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `qcm_competence_ibfk_2` FOREIGN KEY (`COMPETENCE`) REFERENCES `competences` (`IDCOMPETENCE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `support_formation`
--
ALTER TABLE `support_formation`
  ADD CONSTRAINT `support_formation_ibfk_1` FOREIGN KEY (`SUPPORT`) REFERENCES `supports` (`IDSUPPORT`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `support_formation_ibfk_2` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
