-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2017 at 07:43 PM
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
-- Table structure for table `continents`
--

CREATE TABLE IF NOT EXISTS `continents` (
  `IDCONTIENT` int(11) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(20) NOT NULL,
  PRIMARY KEY (`IDCONTIENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
  `DATENAISS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PAYS` int(11) DEFAULT NULL,
  `GROUPE` int(11) DEFAULT NULL,
  `SOCIETE` int(11) DEFAULT NULL,
  `SECTION` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDPERSONNE`),
  KEY `PAYS` (`PAYS`),
  KEY `GROUPE` (`GROUPE`),
  KEY `SOCIETE` (`SOCIETE`),
  KEY `SECTION` (`SECTION`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `personnes`
--

INSERT INTO `personnes` (`IDPERSONNE`, `MATRICULE`, `NOM`, `PRENOM`, `AUTRENOM`, `DATENAISS`, `PAYS`, `GROUPE`, `SOCIETE`, `SECTION`) VALUES
(1, 'CM0012', 'Ainam', 'Jean-Paul', NULL, '2017-06-10 23:05:43', 130, 1, 1, 2),
(2, 'TC0012', 'Armel', 'Kadje', NULL, '2017-06-10 23:05:43', 18, 2, 2, 4);

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
  `ADRESSE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDSOCIETE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `societes`
--

INSERT INTO `societes` (`IDSOCIETE`, `NOM`, `ADRESSE`) VALUES
(1, 'Banque AfriLand', 'Cameroun, Yaoundé'),
(2, 'Renault Equipement', 'France');

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
-- Constraints for table `personnes`
--
ALTER TABLE `personnes`
  ADD CONSTRAINT `personnes_ibfk_4` FOREIGN KEY (`SECTION`) REFERENCES `sections` (`IDSECTION`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_1` FOREIGN KEY (`PAYS`) REFERENCES `pays` (`IDPAYS`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_2` FOREIGN KEY (`GROUPE`) REFERENCES `groupes` (`IDGROUPE`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `personnes_ibfk_3` FOREIGN KEY (`SOCIETE`) REFERENCES `societes` (`IDSOCIETE`) ON DELETE SET NULL ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
