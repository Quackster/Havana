-- Dumping structure for table havana.navigator_styles
DROP TABLE IF EXISTS `navigator_styles`;
CREATE TABLE IF NOT EXISTS `navigator_styles` (
  `room_id` int(11) NOT NULL,
  `description` text NOT NULL DEFAULT '',
  `thumbnail_url` varchar(255) NOT NULL DEFAULT 'officialrooms_es/shoppingdeals_recroom.gif',
  `thumbnail_layout` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `room_id` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table havana.navigator_styles: ~40 rows (approximately)
DELETE FROM `navigator_styles`;
/*!40000 ALTER TABLE `navigator_styles` DISABLE KEYS */;
INSERT INTO `navigator_styles` (`room_id`, `description`, `thumbnail_url`, `thumbnail_layout`) VALUES
	(1, 'New? Lost? Get a warm welcome here!', 'officialrooms_defaults/hh_room_nlobby.png', 4),
	(2, 'Perform your latest master piece, or simply catch the latest gossip.', 'officialrooms_defaults/hh_room_theater.png', 4),
	(3, 'Books! Glorious books! Fill yourself with information and lose yourself in wonderful literary worlds.', 'officialrooms_defaults/hh_room_library.png', 4),
	(4, 'Will you reach the final of the biggest brains in Habbo competition?', 'officialrooms_hq/hh_room_tv_studio_m6.png', 1),
	(5, 'Now Showing: The Making of Habbo Big Brother', 'officialrooms_defaults/hh_room_cinema.png', 1),
	(6, 'Zac Efron owns in the 17 Again basketball court – what would you do with your second shot?', 'officialrooms_defaults/hh_room_sport.png', 1),
	(7, 'Forget Beijing, check out Habbo\'s very own Olympic Stadium!', 'officialrooms_hq/official_fball.png', 1),
	(8, 'Beware the flying knives!', 'officialrooms_defaults/hh_room_kitchen.png', 1),
	(9, 'Grab a stool and hear Dave and Sadie talk about the good old days...', 'officialrooms_defaults/hh_room_pub.png', 1),
	(10, 'Relax with friends over one of Marias specialty coffees', 'officialrooms_hq/hh_room_cafe_xms08.png', 1),
	(11, 'Join Billy for a bite to eat and some office gossip in the HABprentice', 'officialrooms_defaults/hh_room_erics.png', 1),
	(12, 'Star Wars The Clone Wars on Blu-Ray and DVD from December 8', 'officialrooms_defaults/hh_room_space_cafe.png', 1),
	(13, 'One of the highest points in Habbo Hotel!', 'officialrooms_defaults/hh_room_rooftop.png', 1),
	(15, 'Pizza; food of the hungry!', 'officialrooms_defaults/hh_room_pizza.png', 1),
	(16, 'Get food here!', 'officialrooms_defaults/hh_room_habburger.png', 1),
	(17, 'Old, cool, Dusty and the perfect room for the biggest brains in Habbo', 'officialrooms_defaults/hh_room_dustylounge.png', 1),
	(18, 'Try the tea in this Mongol cafe - it is to die for darlings!', 'officialrooms_defaults/hh_room_tearoom.png', 1),
	(19, 'A set of rooms inspired by the original and legendary Mobiles Disco, the progenitor of Habbo', 'officialrooms_defaults/hh_room_old_skool.png', 1),
	(21, 'Ghetto Fabulous', 'officialrooms_defaults/hh_room_disco.png', 1),
	(23, 'Strut your funky stuff', 'officialrooms_defaults/hh_room_bar.png', 1),
	(25, 'Home to the Duck Island residents.', 'officialrooms_defaults/hh_room_sunsetcafe.png', 1),
	(27, 'Where Ideas can flow freely', 'officialrooms_defaults/hh_room_chill.png', 1),
	(28, 'Monumental and magnificient. For Habbo Club Members only.', 'officialrooms_defaults/hh_room_clubmammoth.png', 1),
	(29, 'Climb the rocks, chill in the shade and watch for pirate ships!', 'officialrooms_defaults/hh_room_floatinggarden.png', 1),
	(30, 'Enjoy the great outdoors, celebrate mother nature and party!', 'officialrooms_defaults/hh_room_picnic.png', 1),
	(31, 'Grab a sunbed and top up that tan!', 'officialrooms_defaults/hh_room_sun_terrace.png', 1),
	(32, 'Follow the path...', 'officialrooms_defaults/hh_room_gate_park.png', 1),
	(34, 'Follow the path...', 'officialrooms_defaults/hh_room_park.png', 1),
	(36, 'Splish, splash and have a bash in the Habbo pool!', 'officialrooms_defaults/hh_room_pool.png', 1),
	(39, 'The heart of Habbo Hotel', 'officialrooms_defaults/hh_room_lobby.png', 1),
	(42, 'This was the Habbo Big Brother Lounge during series 1 (2008)', 'officialrooms_defaults/hh_room_floorlobbies.png', 1),
	(43, 'Grab some brain juice!', 'officialrooms_defaults/hh_room_icecafe.png', 1),
	(44, 'Learn a foreign language and win Habbo Credits in our quests!', 'officialrooms_defaults/hh_room_netcafe.png', 1),
	(45, 'Angus, Thongs and Perfect Snogging in cinemas July 25th', 'officialrooms_defaults/hh_room_beauty_salon_general.png', 1),
	(46, 'Popular? Win a cool band and party at your school. You soon will be!', 'officialrooms_defaults/hh_room_den.png', 1),
	(47, 'Connecting you to the heart of Habbo Hotel', 'officialrooms_defaults/hh_room_hallway.png', 1),
	(53, 'Taking you to the far reaches of Habbo Hotel', 'officialrooms_defaults/hh_room_hallway2.png', 1),
	(59, 'Celebrities favourite hangout', 'officialrooms_defaults/hh_room_starlounge.png', 1),
	(60, 'Tres chic with an eastern twist. For Habbo Club Members only.', 'officialrooms_defaults/hh_room_orient.png', 1),
	(68, 'Even the smallest of light... shines in the darkness', 'officialrooms_defaults/hh_room_emperors.png', 1);
/*!40000 ALTER TABLE `navigator_styles` ENABLE KEYS */;
