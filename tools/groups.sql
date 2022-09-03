-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.7-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table hotel.cms_recommended
CREATE TABLE IF NOT EXISTS `cms_recommended` (
  `recommended_id` int(11) NOT NULL,
  `type` enum('GROUP','ROOM') NOT NULL,
  `is_staff_pick` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table hotel.cms_recommended: ~5 rows (approximately)
DELETE FROM `cms_recommended`;
/*!40000 ALTER TABLE `cms_recommended` DISABLE KEYS */;
INSERT INTO `cms_recommended` (`recommended_id`, `type`, `is_staff_pick`) VALUES
	(1, 'GROUP', 1),
	(2, 'GROUP', 0),
	(3, 'GROUP', 0),
	(4, 'GROUP', 0),
	(5, 'GROUP', 0);
/*!40000 ALTER TABLE `cms_recommended` ENABLE KEYS */;

-- Dumping structure for table hotel.cms_stickers
CREATE TABLE IF NOT EXISTS `cms_stickers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `x` varchar(6) NOT NULL DEFAULT '0' COMMENT 'left',
  `y` varchar(6) NOT NULL DEFAULT '0' COMMENT 'top',
  `z` varchar(6) NOT NULL DEFAULT '0' COMMENT 'z-index',
  `sticker_id` int(11) NOT NULL,
  `skin_id` int(11) NOT NULL DEFAULT 0,
  `group_id` int(11) NOT NULL DEFAULT -1,
  `text` longtext NOT NULL DEFAULT '',
  `is_placed` tinyint(1) NOT NULL DEFAULT 0,
  `extra_data` varchar(11) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`group_id`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table hotel.cms_stickers: 108 rows
DELETE FROM `cms_stickers`;
/*!40000 ALTER TABLE `cms_stickers` DISABLE KEYS */;
INSERT INTO `cms_stickers` (`id`, `user_id`, `x`, `y`, `z`, `sticker_id`, `skin_id`, `group_id`, `text`, `is_placed`, `extra_data`) VALUES
	(1, 0, '0', '0', '0', 11100, 1, 1, '', 0, ''),
	(2, 0, '570', '1078', '42', 11000, 2, 1, '', 1, ''),
	(3, 0, '0', '0', '0', 11200, 1, 1, '', 0, ''),
	(4, 0, '0', '0', '0', 11300, 1, 1, '', 0, ''),
	(5, 1, '109', '795', '10', 13, 9, 1, '[color=white][b]Owl[/b]\rThe old and wise one - loyal owl \rwith a heart of gold.\rLevel 10[/color]', 1, ''),
	(6, 1, '189', '1069', '12', 13, 2, 1, 'Well of course I can. Because I am a Habbo Guide!', 1, ''),
	(7, 1, '109', '197', '2', 13, 9, 1, '[color=white][b]Bunny[/b]\rThe fast one - catch one if \ryou can.\rLevel 1[/color]', 1, ''),
	(8, 1, '109', '331', '1', 13, 9, 1, '[color=white][b]Otter[/b]\rThe one who does not let \ryou sink.\rLevel 3[/color]', 1, ''),
	(9, 1, '110', '728', '9', 13, 9, 1, '[color=white][b]Eagle[/b]\rThe sharp eyed one - flying to \ryour aid.\rLevel 9[/color]', 1, ''),
	(10, 1, '109', '528', '6', 13, 9, 1, '[color=white][b]Lynx[/b]\rThe hunter one - stalks down \ranswers.\rLevel 6[/color]', 1, ''),
	(11, 1, '108', '396', '3', 13, 9, 1, '[color=white][b]Badger[/b]\rThe digger one - has information \ryou cannot find.\rLevel 4[/color]', 1, ''),
	(12, 1, '383', '488', '32', 13, 9, 1, '[color=white][b]Who are Habbo guides?[/b]\r\rHabbo Guides are experienced players \rwho like to share their knowledge of \rHabbo and welcome new players.\r\rHabbo Guides meet and greet new \rHabbos, guide them around and\rgenerally making them feel at home.\r\r[b]I\'m new. How do I find a Guide?[/b]\r\rThe first time you enter your room in\rthe hotel you will be asked if you would\rlike to meet a Habbo Guide. You can also \ridentify Guides by their animal badge. \r\r[b]What\'s in it for me?[/b]\r\rA warm fuzzy feeling at the end of the \rday, and hopefully the start of many new \rfriendships :)\r\r[b]Can I join?[/b]\r\rYes, just join this Group. Anyone who\'s\rinterested in being nice to newcomers is\rwelcome to join - the more the merrier![/color]', 1, ''),
	(13, 1, '107', '864', '33', 13, 9, 1, '[color=white][b]Wolf[/b]\rThe eXperienced one with \rthe knowledge to show the way. \rLevel X[/color]', 1, ''),
	(14, 1, '109', '667', '8', 13, 9, 1, '[color=white][b]Bear[/b]\rThe friendly one  - kind and soft.\rLevel 8[/color]', 1, ''),
	(15, 1, '639', '489', '31', 13, 9, 1, '[color=white][b]How do we find the new people?[/b]\r\rWhen new people join Habbo we\'ll ask \rthem if they\'d like to meet a Guide \rfrom Habbo. If they say yes, we\'ll\rsend alerts to members of this group \rwho are in the Hotel at the same time. \rAccept the invitation and you\'ll be \rwhisked off to meet the new person. \rYou can guide them around Habbo and \rshow them what it\'s about.\r\r[b]Can I get as many invites as I want?[/b]\r\rSure, with the Guide tool, You can \rdecide when You want to receive the \rinvites. After helping someone, you can \rpick up the next invite. The Guide tool is \rshown on your screen (in the hotel),\rin the top left corner.\r\r[b]What do I get for guiding?[/b]\r\rHabbo Guides receive badges to \rreward their good work. There are 10 \rlevels to achieve. If you successfully \rguide a Habbo, who stays in Habbo as a \rplayer, is your friend and has spent \renough time online, you will be rewarded.\r\rThe more Habbos you help, the \rcooler the badge! These badges are not \ravailable anywhere else.[/color]', 1, ''),
	(16, 1, '109', '461', '5', 13, 9, 1, '[color=white][b]Fox[/b]\rThe clever one - is swift of \rthought and foot.\rLevel 5[/color]', 1, ''),
	(17, 1, '108', '264', '4', 13, 9, 1, '[color=white][b]Bambi[/b]\rThe cutest one - makes you \rwanna hug.\rLevel 2[/color]', 1, ''),
	(18, 1, '110', '596', '7', 13, 9, 1, '[color=white][b]Buffalo[/b]\rThe strong one - the one you \rcan depend on.\rLevel 7[/color]', 1, ''),
	(19, 1, '25', '1164', '11', 13, 2, 1, 'Hey, I\'m confused. Can you help me?', 1, ''),
	(20, 1, '515', '412', '23', 2198, 0, 1, '', 1, ''),
	(21, 1, '180', '122', '17', 2206, 0, 1, '', 1, ''),
	(22, 1, '630', '411', '25', 2206, 0, 1, '', 1, ''),
	(23, 1, '137', '122', '16', 2201, 0, 1, '', 1, ''),
	(24, 1, '560', '412', '24', 2214, 0, 1, '', 1, ''),
	(25, 1, '786', '411', '29', 2203, 0, 1, '', 1, ''),
	(26, 1, '830', '411', '30', 2220, 0, 1, '', 1, ''),
	(27, 1, '674', '411', '26', 2222, 0, 1, '', 1, ''),
	(28, 1, '471', '412', '22', 2198, 0, 1, '', 1, ''),
	(29, 1, '426', '412', '21', 2193, 0, 1, '', 1, ''),
	(30, 1, '263', '121', '19', 2220, 0, 1, '', 1, ''),
	(31, 1, '720', '411', '27', 2208, 0, 1, '', 1, ''),
	(32, 1, '94', '122', '15', 2193, 0, 1, '', 1, ''),
	(33, 1, '222', '122', '18', 2203, 0, 1, '', 1, ''),
	(34, 1, '50', '121', '14', 2198, 0, 1, '', 1, ''),
	(35, 1, '741', '411', '28', 2201, 0, 1, '', 1, ''),
	(36, 1, '381', '413', '20', 2207, 0, 1, '', 1, ''),
	(37, 0, '0', '0', '0', 11100, 1, 2, '', 0, ''),
	(38, 0, '628', '741', '23', 11000, 3, 2, '', 1, ''),
	(39, 0, '0', '0', '0', 11200, 1, 2, '', 0, ''),
	(40, 0, '0', '0', '0', 11300, 1, 2, '', 0, ''),
	(41, 1, '667', '611', '35', 13, 0, 2, '[b]Tip 5:[/b] Bouncing on your tile three\rtimes locks the square- make shapes\rout of locked tiles to lock all the\rtiles within.', 1, ''),
	(42, 1, '85', '383', '94', 13, 0, 2, '[b][color=#ffce00]Power Drill: [/color][/b][color=#fe6301]Bounce on any tile in \rthe field to clear it. [/color]', 1, ''),
	(43, 1, '369', '317', '20', 13, 4, 2, '[b]Free Battleball[/b]\r\rYes until further notice we\'re letting you play both SnowStorm and Battleball for free - no tickets required.\r\r[b]Please note:[/b]\r* skill levels don\'t apply for the free games - everyone can play everyone', 1, ''),
	(44, 1, '86', '318', '100', 13, 0, 2, '[b][color=#ffce00]Battle Bomb: [/color][/b][color=#fe6301]Clears all tiles \raround it- even yours![/color]', 1, ''),
	(45, 1, '30', '30', '16', 13, 2, 2, '[b]Want to know how to play?\r\rRead the instructions in our [url=https://classichabbo.com/groups/1/id/discussions]discussion forum[/url][/b]', 1, ''),
	(46, 1, '84', '452', '83', 13, 0, 2, '[b][color=#ffce00]Light Bulb: [/color][/b][color=#fe6301]Turns all the tiles in a \rsmall area your colour.[/color]', 1, ''),
	(47, 1, '84', '584', '96', 13, 0, 2, '[b][color=#ffce00]Flashlight: [/color][/b][color=#fe6301]Colours all the tiles in a\r straight line in front of you[/color]', 1, ''),
	(48, 1, '85', '255', '89', 13, 0, 2, '[b][color=#ffce00]Box of pins: [/color][/b][color=#fe6301]Bouncing on these\rwill burst your ball![/color]', 1, ''),
	(49, 1, '697', '414', '43', 13, 0, 2, '[b]Tip 3:[/b] Distract your opponent by\rcalling him or her strange names like\r&quot;llama feet&quot;.', 1, ''),
	(50, 1, '682', '309', '49', 13, 0, 2, '[b]Tip 2:[/b] Bounce on your own squares multiple times to gain extra points, rather than on empty squares.', 1, ''),
	(51, 1, '84', '714', '104', 13, 0, 2, '[b][color=#ffce00]Random: [/color][/b][color=#fe6301]Gives you a power-up\rchosen at random![/color]', 1, ''),
	(52, 1, '683', '254', '47', 13, 0, 2, '[b]Tip 1:[/b] Bouncing over your opponents squares earns you more points than bouncing on empty squares.', 1, ''),
	(53, 1, '84', '650', '102', 13, 0, 2, '[b][color=#ffce00]Harlequin: [/color][/b][color=#fe6301]Makes all other players\rcolour tiles for your team.[/color]', 1, ''),
	(54, 1, '683', '222', '108', 13, 0, 2, '[size=large][b]Tips & Tactics[/b][/size]', 1, ''),
	(55, 1, '669', '532', '31', 13, 0, 2, '[b]Tip 4:[/b] The best power-up is the \rHarlequin, while the worse is the\rbox of pins which can harm you\rmost of all!', 1, ''),
	(56, 1, '85', '519', '98', 13, 0, 2, '[b][color=#ffce00]Spring: [/color][/b][color=#fe6301]Locks tiles to your colour\rwith a single bounce.[/color]', 1, ''),
	(57, 1, '256', '391', '2', 1129, 0, 2, '', 1, ''),
	(58, 1, '627', '370', '39', 1129, 0, 2, '', 1, ''),
	(59, 1, '799', '211', '1', 212, 0, 2, '', 1, ''),
	(60, 0, '0', '0', '0', 11100, 1, 3, '', 0, ''),
	(61, 0, '476', '492', '34', 11000, 2, 3, '', 1, ''),
	(62, 0, '0', '0', '0', 11200, 1, 3, '', 0, ''),
	(63, 0, '0', '0', '0', 11300, 1, 3, '', 0, ''),
	(64, 1, '56', '333', '8', 46, 0, 3, '', 1, ''),
	(65, 1, '210', '1257', '22', 141, 0, 3, '', 1, ''),
	(66, 1, '153', '337', '10', 49, 0, 3, '', 1, ''),
	(67, 1, '462', '344', '12', 1308, 0, 3, '', 1, ''),
	(68, 1, '59', '414', '19', 56, 0, 3, '', 1, ''),
	(69, 1, '215', '321', '11', 52, 0, 3, '', 1, ''),
	(70, 1, '222', '410', '14', 47, 0, 3, '', 1, ''),
	(71, 1, '171', '439', '16', 39, 0, 3, '', 1, ''),
	(72, 1, '273', '416', '13', 45, 0, 3, '', 1, ''),
	(73, 1, '141', '238', '7', 54, 0, 3, '', 1, ''),
	(74, 1, '271', '470', '15', 1226, 0, 3, '', 1, ''),
	(75, 1, '189', '256', '4', 57, 0, 3, '', 1, ''),
	(76, 1, '672', '228', '3', 1914, 0, 3, '', 1, ''),
	(77, 1, '146', '410', '17', 45, 0, 3, '', 1, ''),
	(78, 1, '238', '243', '5', 50, 0, 3, '', 1, ''),
	(79, 1, '94', '321', '9', 57, 0, 3, '', 1, ''),
	(80, 1, '222', '1267', '6', 1309, 0, 3, '', 1, ''),
	(81, 1, '101', '436', '18', 54, 0, 3, '', 1, ''),
	(82, 1, '425', '1223', '25', 170, 0, 3, '', 1, ''),
	(83, 0, '0', '0', '0', 11100, 1, 4, '', 0, ''),
	(84, 0, '618', '44', '58', 11000, 4, 4, '', 1, ''),
	(85, 0, '0', '0', '0', 11200, 1, 4, '', 0, ''),
	(86, 0, '0', '0', '0', 11300, 1, 4, '', 0, ''),
	(87, 1, '63', '613', '55', 13, 0, 4, 'Space', 1, ''),
	(88, 1, '85', '713', '23', 13, 0, 4, '[b]Tips &amp; Tactics[/b]', 1, ''),
	(89, 1, '40', '542', '53', 13, 0, 4, 'Moving', 1, ''),
	(90, 1, '54', '958', '33', 13, 0, 4, '[color=#d80000][b]Tip 2:[/b][/color] If you are wobbling all over \rthe place, you can get your \rbalance back once during \rthe game using the \'stabilise\' button \r(space bar).', 1, ''),
	(91, 1, '90', '742', '31', 13, 9, 4, '[color=#d80000][b]Tip 1:[/b][/color] When the game starts, you\rwill see a pop up window.\rOn the right is a balance meter. \r\rWhen the hand points straight up,\ryou\'re perfectly balanced. When it \rpoints to the right, you need to \rbalance to the left, and vice versa. \r\rIf the hand drops too far either side\ryou have lost your balance and will\rfall into the water and lose.', 1, ''),
	(92, 1, '56', '1034', '29', 13, 0, 4, '[color=#d80000][b]Tip 3:[/b][/color] Moving away from your \ropponent during a game \rwill allow you time to balance.', 1, ''),
	(93, 1, '334', '955', '14', 13, 4, 4, '[color=#d80000][b]The Queue System[/b][/color]\r\rWhen you have a ticket, get into the pool, swim over and climb on the inflatables to queue for your turn. \r\rThe game starts when two players are ready.', 1, ''),
	(94, 1, '178', '468', '35', 13, 0, 4, 'Balance', 1, ''),
	(95, 1, '43', '488', '51', 13, 0, 4, 'Push', 1, ''),
	(96, 1, '336', '745', '25', 13, 4, 4, '[color=#d80000][b]Buying Tickets[/b][/color]\r\rTo play Wobble Squabble, you need tickets. You can get tickets by clicking on the inflatables, or by clicking on the ticket machine beside the pool.\r\rTickets cost [b][color=#fe6301]6 Credits for 20[/color][/b] or [b][color=#fe6301]1 Credit for 2[/color][/b]. \r\rIn Wobble Squabble the winner stays on, so if you win a game stay on the inflatables and the next one is free!', 1, ''),
	(97, 1, '28', '438', '47', 13, 0, 4, 'Balance', 1, ''),
	(98, 1, '760', '248', '3', 1129, 0, 4, '', 1, ''),
	(99, 1, '330', '207', '1', 150, 0, 4, '', 1, ''),
	(100, 1, '329', '145', '2', 150, 0, 4, '', 1, ''),
	(101, 1, '162', '387', '2', 13, 2, 5, '[b]Eeek, collision course![/b]', 1, ''),
	(102, 0, '0', '0', '0', 11100, 1, 5, '', 0, ''),
	(103, 0, '644', '11', '19', 11000, 5, 5, '', 1, ''),
	(104, 0, '0', '0', '0', 11200, 1, 5, '', 0, ''),
	(105, 0, '0', '0', '0', 11300, 1, 5, '', 0, ''),
	(106, 1, '69', '194', '6', 13, 2, 5, '[b]Hey dude! Don\'t you know the rules? Check out the How to Play thread in the [url=https://classichabbo.com/groups/snowstorm/discussions]discussion forum[/url][/b]', 1, ''),
	(107, 1, '516', '1083', '1', 13, 2, 5, '[b]Not again...[/b]', 1, ''),
	(108, 1, '556', '317', '4', 183, 0, 5, '', 1, '');
/*!40000 ALTER TABLE `cms_stickers` ENABLE KEYS */;

-- Dumping structure for table hotel.groups_details
CREATE TABLE IF NOT EXISTS `groups_details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` mediumtext NOT NULL,
  `owner_id` int(10) NOT NULL,
  `room_id` int(10) NOT NULL DEFAULT 0,
  `badge` mediumtext NOT NULL DEFAULT 'b0503Xs09114s05013s05015',
  `recommended` int(1) NOT NULL DEFAULT 0,
  `background` varchar(255) NOT NULL DEFAULT 'bg_colour_08',
  `views` int(15) NOT NULL DEFAULT 0,
  `topics` smallint(1) NOT NULL DEFAULT 0,
  `group_type` tinyint(3) unsigned NOT NULL DEFAULT 0,
  `forum_type` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `forum_premission` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `alias` varchar(30) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `alias` (`alias`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table hotel.groups_details: ~5 rows (approximately)
DELETE FROM `groups_details`;
/*!40000 ALTER TABLE `groups_details` DISABLE KEYS */;
INSERT INTO `groups_details` (`id`, `name`, `description`, `owner_id`, `room_id`, `badge`, `recommended`, `background`, `views`, `topics`, `group_type`, `forum_type`, `forum_premission`, `alias`, `created_at`) VALUES
	(1, 'Habbo Guides (Official)', 'The official Habbo Guides Group.', 1, 0, 'b1205Xs43104s38114', 0, 'guidesgroup_bg', 0, 0, 0, 0, 0, 'officialhabboguides', '2019-12-11 18:55:51'),
	(2, 'BattleBall: Rebound!', 'Learn all about BattleBall here!', 1, 0, 'b1013Xs04038s04064s04100', 0, 'BB_Group2', 0, 0, 0, 0, 0, 'battleball_rebound', '2019-12-19 08:27:31'),
	(3, 'Lido Diving', 'Official Lido Diving group', 1, 0, 'b0816Xs58111s58063s58065', 0, 'bg_lido_flat', 0, 0, 0, 0, 0, 'lido', '2019-12-19 08:43:58'),
	(4, 'Wobble Squabble', 'Learn about Wobble Squabble here!', 1, 0, 'b1201Xs58164s47023s46025', 0, 'WS_Group2', 0, 0, 0, 0, 0, 'wobble_squabble', '2019-12-19 08:51:23'),
	(5, 'SnowStorm', 'Learn all about SnowStorm here!', 1, 0, 'b1216Xs04116s05118s05110s04112', 0, 'snowstorm_bg', 0, 0, 0, 0, 0, 'snow_storm', '2019-12-21 04:23:46');
/*!40000 ALTER TABLE `groups_details` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
