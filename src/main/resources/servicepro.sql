-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2017 at 08:51 PM
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
  `NIVEAU` int(11) DEFAULT NULL,
  `TYPE` char(3) DEFAULT NULL COMMENT 'CN = Connaissance, CP = Competence, CNP = les deux',
  PRIMARY KEY (`IDCOMPETENCE`),
  KEY `NIVEAU` (`NIVEAU`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `competences`
--

INSERT INTO `competences` (`IDCOMPETENCE`, `DESCRIPTION`, `NIVEAU`, `TYPE`) VALUES
(1, 'Connaissance de CFAO, histoire, domaines d''activité', 1, 'CN'),
(2, 'Connaissance de Renault Trucks, histoire, domaines d''activité', 1, 'CP'),
(3, 'Connaitre et appliquer les règles de sécurité sur le lieu de travail', 2, 'CN'),
(4, 'Connaitre et appliquer les procédures "5S" sur le lieu de travail', 3, 'CNP'),
(5, 'Maitiser les bases de la mécanique', 2, 'CNP'),
(6, 'Maitriser les principes des systèmes de base, moteur, électricité, transmission', 4, 'CNP');

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
(4, 2),
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `formations`
--

INSERT INTO `formations` (`IDFORMATION`, `CODEFORMATION`, `MODELE`, `TITRE`, `DESCRIPTION`, `ETATFORMATION`, `TYPEFORMATION`, `DATEDEBUT`, `DATEFIN`) VALUES
(1, 'TEST02', 4, 'Un test', 'Une description de test', 3, 2, '1919-11-21', '1970-11-13'),
(2, 'aui', 6, 'je suis ', 'qh obon', 3, 4, '1919-11-12', '1970-11-02'),
(3, 'eto', 4, 'je dois', 'brqvotoi qussi', 4, NULL, '2017-07-12', '2017-07-09'),
(4, 'code', 3, 'ah bon', 'gefz', 2, 3, '2017-07-10', '2017-07-10');

-- --------------------------------------------------------

--
-- Table structure for table `formation_competence`
--

CREATE TABLE IF NOT EXISTS `formation_competence` (
  `FORMATION` int(11) NOT NULL,
  `COMPETENCE` int(11) NOT NULL,
  KEY `FORMATION` (`FORMATION`,`COMPETENCE`),
  KEY `COMPETENCE` (`COMPETENCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `formation_personne`
--

CREATE TABLE IF NOT EXISTS `formation_personne` (
  `FORMATION` int(11) NOT NULL,
  `PERSONNE` int(11) NOT NULL,
  KEY `FORMATION` (`FORMATION`,`PERSONNE`),
  KEY `PERSONNE` (`PERSONNE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `formation_personne`
--

INSERT INTO `formation_personne` (`FORMATION`, `PERSONNE`) VALUES
(1, 1),
(1, 3),
(1, 4),
(1, 5),
(1, 8),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 17),
(1, 18),
(1, 19),
(1, 22),
(1, 25),
(1, 27),
(3, 2),
(3, 3),
(3, 5),
(3, 8),
(3, 10),
(3, 12),
(3, 13),
(3, 14),
(3, 17),
(3, 18),
(3, 19),
(3, 20),
(3, 21),
(3, 26),
(4, 2),
(4, 3),
(4, 8),
(4, 10),
(4, 12),
(4, 13),
(4, 17),
(4, 19),
(4, 23);

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

--
-- Dumping data for table `langue_parlee`
--

INSERT INTO `langue_parlee` (`IDPERS`, `IDLANGUE`) VALUES
(1, 2),
(2, 2),
(1, 3),
(3, 3);

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
  PRIMARY KEY (`IDNIVEAU`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `niveau`
--

INSERT INTO `niveau` (`IDNIVEAU`, `LIBELLE`) VALUES
(1, 'Initial'),
(2, 'Fondamental'),
(3, 'Avancé'),
(4, 'Expert');

-- --------------------------------------------------------

--
-- Table structure for table `niveau_competence`
--

CREATE TABLE IF NOT EXISTS `niveau_competence` (
  `IDNIVEAUCOMPETENCE` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(50) NOT NULL,
  PRIMARY KEY (`IDNIVEAUCOMPETENCE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `niveau_competence`
--

INSERT INTO `niveau_competence` (`IDNIVEAUCOMPETENCE`, `LIBELLE`) VALUES
(1, 'Initial'),
(2, 'Fondamental'),
(3, 'Avancé'),
(4, 'Expert');

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
  PRIMARY KEY (`IDPERSONNEL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `personnels`
--

INSERT INTO `personnels` (`IDPERSONNEL`, `NOM`, `PRENOM`, `ADRESSE`) VALUES
(1, 'Ainam', 'Jean-Paul', 'Nanga-Eboko'),
(2, 'Armel', 'Kadje', 'Une adresse'),
(3, 'Un Personnel', 'prenom personnel', 'adresse du personne');

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
  `PAYS` int(11) DEFAULT NULL,
  `GROUPE` int(11) DEFAULT NULL,
  `SOCIETE` int(11) DEFAULT NULL,
  `SECTION` int(11) DEFAULT NULL,
  `CONTRAT` int(11) DEFAULT NULL,
  `AMBITION` int(11) DEFAULT NULL,
  `LANGUE` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDPERSONNE`),
  KEY `PAYS` (`PAYS`),
  KEY `GROUPE` (`GROUPE`),
  KEY `SOCIETE` (`SOCIETE`),
  KEY `SECTION` (`SECTION`),
  KEY `FK_contrat` (`CONTRAT`),
  KEY `LANGUE` (`LANGUE`),
  KEY `AMBITION` (`AMBITION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1787 ;

--
-- Dumping data for table `personnes`
--

INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`) VALUES
(1, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 1, 1, 2, 1, 2, 2),
(2, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 2, 4, 3, 3, 1),
(3, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 1, 3, 3, 2, 1, 3),
(4, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 1, 4, 4, 2, 3, 3),
(5, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 11, 5, 3, 2, 2),
(8, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 3, 13, 4, 3, 2, 1),
(10, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(11, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(12, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(13, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(14, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(17, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(18, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(19, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(20, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(21, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(22, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(23, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(24, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(25, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(26, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(27, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(31, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(32, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(33, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(34, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(35, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(36, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(37, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(38, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(39, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(40, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(41, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(42, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(43, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(44, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(45, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(46, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(47, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(48, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(49, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(50, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(51, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(52, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(53, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(54, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(62, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(63, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(64, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(65, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(66, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(67, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(68, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(69, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(70, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(71, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(72, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(73, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(74, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(75, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(76, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(77, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(78, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(79, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(80, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(81, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(82, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(83, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(84, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(85, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(86, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(87, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(88, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(89, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(90, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(91, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(92, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(93, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(94, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(95, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(96, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(97, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(98, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(99, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(100, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(101, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(102, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(103, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(104, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(105, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(106, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(107, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(108, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(109, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(125, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(126, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(127, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(128, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(129, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(130, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(131, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(132, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(133, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(134, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(135, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(136, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(137, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(138, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(139, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(140, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(141, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(142, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(143, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(144, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(145, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(146, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(147, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(148, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(149, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(150, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(151, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(152, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(153, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(154, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(155, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(156, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(157, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(158, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(159, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(160, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(161, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(162, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(163, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(164, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(165, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(166, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(167, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(168, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(169, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(170, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(171, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(172, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(173, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(174, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(175, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(176, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(177, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(178, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(179, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(180, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(181, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(182, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(183, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(184, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(185, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(186, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(187, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(188, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(189, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(190, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(191, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(192, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(193, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(194, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(195, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(196, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(197, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(198, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(199, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(200, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(201, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(202, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(203, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(204, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(205, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(206, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(207, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(208, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(209, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(210, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(211, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(212, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(213, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(214, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(215, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(216, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(217, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(218, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(219, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(220, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(252, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(253, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(254, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(255, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(256, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(257, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(258, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(259, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(260, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(261, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(262, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(263, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(264, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(265, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(266, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(267, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(268, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(269, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(270, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(271, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(272, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(273, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(274, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(275, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(276, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(277, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(278, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(279, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(280, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(281, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(282, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(283, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(284, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(285, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(286, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(287, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(288, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(289, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(290, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(291, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(292, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(293, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(294, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(295, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(296, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(297, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(298, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(299, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(300, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(301, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(302, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(303, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(304, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(305, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(306, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(307, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(308, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(309, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(310, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(311, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(312, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(313, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(314, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(315, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(316, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(317, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(318, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(319, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(320, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(321, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(322, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(323, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(324, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(325, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(326, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(327, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(328, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(329, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(330, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(331, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(332, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(333, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(334, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(335, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(336, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(337, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(338, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(339, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(340, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(341, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(342, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(343, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(344, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(345, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(346, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(347, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(348, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(349, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(350, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(351, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(352, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(353, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(354, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(355, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(356, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(357, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(358, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(359, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(360, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(361, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(362, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(363, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(364, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(365, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(366, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(367, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(368, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(369, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(370, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(371, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(372, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(373, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(374, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(375, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(376, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(377, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(378, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(379, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(380, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(381, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(382, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(383, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(384, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(385, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(386, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(387, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(388, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(389, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(390, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(391, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(392, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(393, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(394, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(395, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(396, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(397, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(398, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(399, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(400, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(401, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(402, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(403, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(404, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(405, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(406, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(407, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(408, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(409, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(410, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(411, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(412, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(413, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(414, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(415, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(416, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(417, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(418, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(419, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(420, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(421, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(422, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(423, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(424, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(425, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(426, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(427, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(428, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(429, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(430, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(431, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(432, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(433, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(434, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(435, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(436, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(437, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(438, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(439, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(440, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(441, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(442, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(443, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(507, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(508, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(509, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(510, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(511, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(512, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(513, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(514, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(515, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(516, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(517, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(518, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(519, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(520, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(521, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(522, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(523, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(524, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(525, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(526, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(527, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(528, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(529, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(530, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(531, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(532, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(533, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(534, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(535, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(536, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(537, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(538, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(539, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(540, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(541, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(542, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(543, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(544, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(545, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(546, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(547, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(548, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(549, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(550, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(551, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(552, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(553, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(554, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(555, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(556, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(557, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(558, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(559, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(560, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(561, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(562, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(563, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(564, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(565, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(566, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(567, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(568, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(569, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(570, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(571, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(572, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(573, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(574, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(575, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(576, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(577, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(578, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(579, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(580, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(581, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(582, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(583, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(584, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(585, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(586, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(587, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(588, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(589, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(590, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(591, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(592, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(593, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(594, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(595, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(596, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(597, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(598, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(599, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(600, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(601, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(602, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(603, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(604, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(605, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(606, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(607, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(608, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(609, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(610, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(611, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(612, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(613, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(614, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(615, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(616, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(617, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(618, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(619, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(620, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(621, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(622, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(623, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(624, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(625, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(626, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(627, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(628, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(629, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(630, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(631, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(632, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(633, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(634, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(635, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(636, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(637, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(638, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(639, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(640, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(641, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(642, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(643, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(644, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(645, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(646, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(647, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(648, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(649, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(650, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(651, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(652, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(653, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(654, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(655, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(656, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(657, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(658, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(659, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(660, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(661, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(662, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(663, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(664, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(665, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(666, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(667, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(668, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(669, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(670, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(671, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(672, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(673, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(674, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(675, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(676, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(677, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(678, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(679, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(680, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(681, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(682, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(683, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(684, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(685, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(686, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(687, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(688, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(689, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(690, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(691, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(692, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(693, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(694, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(695, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(696, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(697, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(698, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(699, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(700, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(701, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(702, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(703, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(704, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(705, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(706, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(707, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(708, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(709, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(710, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(711, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(712, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(713, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(714, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(715, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(716, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(717, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(718, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(719, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(720, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(721, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(722, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(723, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(724, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(725, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(726, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(727, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(728, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(729, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(730, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(731, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(732, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(733, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(734, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(735, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(736, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(737, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(738, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(739, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(740, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(741, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(742, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(743, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(744, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(745, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(746, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(747, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(748, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(749, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(750, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(751, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(752, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(753, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(754, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3);
INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`) VALUES
(755, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(756, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(757, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(758, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(759, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(760, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(761, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(762, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(763, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(764, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(765, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(766, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(767, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(768, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(769, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(770, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(771, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(772, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(773, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(774, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(775, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(776, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(777, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(778, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(779, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(780, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(781, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(782, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(783, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(784, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(785, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(786, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(787, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(788, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(789, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(790, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(791, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(792, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(793, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(794, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(795, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(796, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(797, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(798, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(799, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(800, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(801, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(802, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(803, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(804, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(805, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(806, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(807, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(808, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(809, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(810, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(811, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(812, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(813, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(814, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(815, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(816, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(817, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(818, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(819, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(820, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(821, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(822, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(823, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(824, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(825, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(826, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(827, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(828, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(829, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(830, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(831, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(832, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(833, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(834, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(835, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(836, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(837, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(838, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(839, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(840, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(841, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(842, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(843, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(844, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(845, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(846, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(847, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(848, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(849, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(850, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(851, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(852, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(853, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(854, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(855, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(856, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(857, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(858, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(859, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(860, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(861, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(862, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(863, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(864, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(865, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(866, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(867, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(868, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(869, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(870, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(871, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(872, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(873, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(874, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(875, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(876, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(877, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(878, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(879, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(880, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(881, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(882, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(883, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(884, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(885, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(886, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(887, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(888, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(889, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(890, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1018, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1019, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1020, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1021, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1022, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1023, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1024, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1025, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1026, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1027, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1028, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1029, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1030, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1031, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1032, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1033, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1034, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1035, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1036, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1037, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1038, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1039, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1040, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1041, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1042, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1043, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1044, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1045, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1046, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1047, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1048, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1049, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1050, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1051, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1052, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1053, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1054, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1055, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1056, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1057, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1058, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1059, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1060, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1061, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1062, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1063, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1064, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1065, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1066, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1067, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1068, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1069, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1070, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1071, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1072, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1073, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1074, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1075, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1076, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1077, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1078, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1079, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1080, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1081, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1082, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1083, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1084, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1085, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1086, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1087, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1088, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1089, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1090, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1091, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1092, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1093, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1094, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1095, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1096, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1097, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1098, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1099, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1100, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1101, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1102, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1103, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1104, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1105, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1106, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1107, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1108, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1109, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1110, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1111, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1112, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1113, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1114, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1115, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1116, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1117, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1118, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1119, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1120, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1121, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1122, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1123, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1124, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1125, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1126, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1127, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1128, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1129, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1130, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1131, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1132, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1133, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1134, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1135, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1136, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1137, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1138, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1139, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1140, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1141, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1142, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1143, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1144, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1145, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1146, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1147, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1148, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1149, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1150, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1151, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1152, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1153, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1154, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1155, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1156, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1157, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1158, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1159, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1160, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1161, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1162, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1163, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1164, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1165, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1166, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1167, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1168, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1169, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1170, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1171, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1172, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1173, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1174, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1175, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1176, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1177, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1178, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1179, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1180, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1181, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1182, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1183, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1184, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1185, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1186, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1187, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1188, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1189, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1190, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1191, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1192, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1193, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1194, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1195, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1196, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1197, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1198, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1199, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1200, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1201, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1202, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1203, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1204, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1205, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1206, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1207, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1208, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1209, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1210, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1211, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1212, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1213, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1214, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1215, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1216, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1217, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1218, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1219, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1220, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1221, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1222, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1223, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1224, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1225, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1226, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1227, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1228, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1229, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1230, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1231, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1232, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1233, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1234, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1235, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1236, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1237, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1238, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1239, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1240, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1241, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1242, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1243, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1244, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1245, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1246, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1247, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1248, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1249, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1250, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1251, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1252, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1253, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1254, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1255, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1256, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1257, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1258, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1259, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1260, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1261, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1262, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1263, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1264, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1265, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1266, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1267, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1268, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1269, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1270, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1271, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1272, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1273, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1274, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1275, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1276, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1277, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1278, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1279, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1280, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1281, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1282, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1283, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1284, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1285, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1286, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1287, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1288, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1289, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1290, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1291, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1292, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1293, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1294, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1295, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1296, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1297, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1298, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1299, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1300, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1301, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1302, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1303, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1304, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1305, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1306, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1307, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1308, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1309, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1310, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1311, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1312, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1313, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1314, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1315, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1316, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1317, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1318, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1319, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1320, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1321, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1322, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1323, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1324, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1325, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1326, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1327, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1328, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1329, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1330, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1331, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1332, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1333, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1334, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1335, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1336, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1337, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1338, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1339, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1340, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1341, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1342, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1343, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1344, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1345, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1346, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1347, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1348, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1349, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1350, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1351, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1352, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1353, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1354, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1355, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1356, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1357, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1358, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1359, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1360, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1361, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1362, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1363, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1364, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1365, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1366, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1367, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1368, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1369, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1370, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1371, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1372, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1373, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1374, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1375, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1376, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1377, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1378, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1379, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1380, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1381, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1382, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1383, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1384, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1385, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1386, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1387, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1388, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1389, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1390, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1391, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1392, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1393, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1394, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1395, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1396, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1397, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1398, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1399, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1400, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1401, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1402, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1403, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1404, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1405, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1406, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1407, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1408, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1409, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1410, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1411, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1412, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1413, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1414, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1415, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1416, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1417, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1418, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1419, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1420, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1421, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1422, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1423, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1424, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1425, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1426, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1427, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1428, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1429, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1430, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1431, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1432, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1433, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1434, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1435, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1436, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1437, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1438, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1439, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1440, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1441, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1442, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1443, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1444, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1445, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1446, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1447, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1448, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1449, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1450, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1451, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1452, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1453, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1454, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1455, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1456, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1457, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1458, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1459, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1460, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1461, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1462, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1463, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1464, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1465, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1466, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1467, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1468, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1469, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1470, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1471, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1472, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1473, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1474, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1475, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1476, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1477, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1478, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1479, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1480, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1481, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1482, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1483, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1484, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1485, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1486, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1487, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1488, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1489, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1490, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1491, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1492, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1493, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1494, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1495, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1496, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1497, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1498, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1499, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1500, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1501, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1502, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1503, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1504, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3);
INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`, `CONTRAT`, `AMBITION`, `LANGUE`) VALUES
(1505, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1506, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1507, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1508, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1509, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1510, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1511, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1512, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1513, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1514, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1515, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1516, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1517, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1518, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1519, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1520, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1521, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1522, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1523, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1524, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1525, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1526, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1527, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1528, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1529, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1530, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1531, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1532, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1533, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1534, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1535, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1536, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1537, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1538, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1539, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1540, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1541, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1542, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1543, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1544, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1545, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1546, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1547, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1548, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1549, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1550, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1551, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1552, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1553, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1554, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1555, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1556, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1557, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1558, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1559, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1560, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1561, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1562, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1563, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1564, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1565, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1566, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1567, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1568, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1569, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1570, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1571, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1572, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1573, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1574, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1575, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1576, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1577, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1578, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1579, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1580, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1581, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1582, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1583, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1584, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1585, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1586, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1587, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1588, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1589, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1590, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1591, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1592, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1593, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1594, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1595, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1596, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1597, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1598, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1599, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1600, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1601, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1602, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1603, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1604, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1605, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1606, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1607, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1608, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1609, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1610, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1611, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1612, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1613, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1614, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1615, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1616, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1617, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1618, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1619, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1620, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1621, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1622, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1623, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1624, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1625, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1626, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1627, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1628, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1629, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1630, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1631, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1632, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1633, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1634, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1635, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1636, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1637, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1638, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1639, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1640, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1641, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1642, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1643, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1644, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1645, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1646, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1647, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1648, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1649, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1650, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1651, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1652, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1653, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1654, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1655, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1656, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1657, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1658, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1659, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1660, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1661, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1662, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1663, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1664, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1665, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1666, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1667, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1668, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1669, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1670, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1671, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1672, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1673, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1674, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1675, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1676, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1677, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1678, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1679, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1680, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1681, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1682, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1683, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1684, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1685, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1686, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1687, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1688, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1689, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1690, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1691, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1692, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1693, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1694, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1695, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1696, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1697, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1698, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1699, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1700, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1701, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1702, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1703, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1704, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1705, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1706, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1707, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1708, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1709, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1710, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1711, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1712, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1713, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1714, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1715, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1716, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1717, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1718, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1719, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1720, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1721, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1722, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1723, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1724, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1725, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1726, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1727, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1728, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1729, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1730, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1731, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1732, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1733, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1734, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1735, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1736, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1737, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1738, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1739, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1740, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1741, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1742, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1743, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1744, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1745, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1746, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1747, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1748, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1749, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1750, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1751, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1752, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1753, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1754, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1755, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1756, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1757, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1758, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1759, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1760, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1761, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1762, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1763, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1764, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1765, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1766, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1767, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1768, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1769, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1770, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1771, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1772, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1773, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1774, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1775, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1776, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1777, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1778, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1779, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3),
(1780, 'NE00729', 'ABDOULKARIM', 'ALI', NULL, '2017-06-11', 130, 2, 12, 3, 2, 3, 3),
(1781, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-11', 18, 2, 12, 3, 2, 3, 3),
(1782, 'BJ00043', 'ADAMNE', 'Bertin', NULL, '2017-06-26', 14, 2, 12, 3, 2, 3, 3),
(1783, 'TG00032', 'AKAKPO AGBAN', 'Alban', NULL, '2017-06-26', 15, 2, 12, 3, 2, 3, 3),
(1784, 'BF00421', 'BADINI', 'Adama', NULL, '2017-06-26', 20, 2, 12, 3, 2, 3, 3),
(1785, 'NE00714', 'CHEFOU', 'IDI', NULL, '2017-06-26', 55, 2, 12, 3, 2, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `personne_profil`
--

CREATE TABLE IF NOT EXISTS `personne_profil` (
  `PERSONNE` int(11) NOT NULL,
  `PROFIL` int(11) NOT NULL,
  KEY `PERSONNE` (`PERSONNE`,`PROFIL`),
  KEY `PROFIL` (`PROFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
(2, 1),
(3, 1),
(4, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(6, 3),
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(6, 4),
(1, 5),
(2, 5),
(3, 5),
(4, 5);

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
(19, 2, 2),
(21, 2, 2),
(19, 4, 3),
(19, 5, 4);

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
  ADD CONSTRAINT `competences_ibfk_1` FOREIGN KEY (`NIVEAU`) REFERENCES `niveau_competence` (`IDNIVEAUCOMPETENCE`) ON DELETE SET NULL ON UPDATE CASCADE;

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
  ADD CONSTRAINT `personnes_ibfk_1` FOREIGN KEY (`PAYS`) REFERENCES `pays` (`IDPAYS`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_2` FOREIGN KEY (`GROUPE`) REFERENCES `groupes` (`IDGROUPE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_3` FOREIGN KEY (`SOCIETE`) REFERENCES `societes` (`IDSOCIETE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_4` FOREIGN KEY (`SECTION`) REFERENCES `sections` (`IDSECTION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_5` FOREIGN KEY (`AMBITION`) REFERENCES `ambitions` (`IDAMBITION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_6` FOREIGN KEY (`LANGUE`) REFERENCES `langue` (`IDLANGUE`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `personne_profil`
--
ALTER TABLE `personne_profil`
  ADD CONSTRAINT `personne_profil_ibfk_1` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `personne_profil_ibfk_2` FOREIGN KEY (`PROFIL`) REFERENCES `profils` (`IDPROFIL`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `postes`
--
ALTER TABLE `postes`
  ADD CONSTRAINT `postes_ibfk_2` FOREIGN KEY (`SOCIETE`) REFERENCES `societes` (`IDSOCIETE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `postes_ibfk_1` FOREIGN KEY (`PERSONNE`) REFERENCES `personnes` (`IDPERSONNE`) ON DELETE CASCADE ON UPDATE CASCADE;

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
-- Constraints for table `support_formation`
--
ALTER TABLE `support_formation`
  ADD CONSTRAINT `support_formation_ibfk_1` FOREIGN KEY (`SUPPORT`) REFERENCES `supports` (`IDSUPPORT`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `support_formation_ibfk_2` FOREIGN KEY (`FORMATION`) REFERENCES `formations` (`IDFORMATION`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
