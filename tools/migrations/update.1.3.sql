DROP TABLE IF EXISTS `rooms_bots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms_bots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `mission` varchar(255) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `start_look` varchar(25) NOT NULL,
  `figure` varchar(255) NOT NULL,
  `figure_flash` varchar(255) NOT NULL,
  `walkspace` text NOT NULL,
  `room_id` int(11) NOT NULL DEFAULT 0,
  `speech` mediumtext NOT NULL,
  `response` mediumtext NOT NULL,
  `unrecognised_response` mediumtext NOT NULL,
  `hand_items` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms_bots`
--

LOCK TABLES `rooms_bots` WRITE;
/*!40000 ALTER TABLE `rooms_bots` DISABLE KEYS */;
INSERT INTO `rooms_bots` VALUES (5,'Xenia','The belle of the Battle Ball',1,8,'2,2','sd=001&sh=002/54,178,190&lg=200/230,49,57&ch=506/230,49,57,141&lh=001/168,123,67&rh=001/168,123,67&hd=001/168,123,67&ey=001&fc=001/168,123,67&hr=506/194,26,134,190&hrb=506/2,3,4&rs=002/230,49,57&ls=002/230,49,57&bd=001/168,123,67','hr-834-61.sh-730-80.ch-825-62.hd-600-5.lg-827-72','0,5 0,6 0,7 1,5 1,6 1,7 1,8',66,'','','',''),(6,'Pamela',':)',7,8,'4,4','sd=001&sh=002/148,98,32&lg=005/230,49,57&ch=201/255,255,255&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=507/103,78,59&rs=002/255,255,255&ls=002/255,255,255&bd=001/215,175,125','hr-836-37.sh-730-62.ch-822-62.hd-600-2.lg-715-96','22,4 23,4 24,4 25,4 26,4 22,5 23,5 24,5 25,5 26,5',45,'','','',''),(7,'Regina','I know, right?',3,6,'2,2','sd=001&sh=002/255,115,131&lg=005/255,115,131&ch=018/255,255,255&lh=001/255,204,153&rh=001/255,204,153&hd=001/255,204,153&ey=001&fc=001/255,204,153&hr=501/225,204,120&rs=003/255,255,255&ls=003/255,255,255&bd=001/255,204,153','hr-832-35.sh-730-97.ch-685-62.hd-600-1.lg-715-97','2,7 2,8 2,9 3,5 3,6 3,7 3,8 3,9 3,10',10,'I\'ve been busy practicing my dance routine for my latest song!|You like coffee? I like my job|You mocha me very happy.|Italians are so good at making coffee because they naturally like to espresso themselves.','Enjoy this|This will do the trick|One %lowercaseDrink% coming right up!','Repeat that please!|Say that again|What?|Hmm...','Coffee'),(8,'James','Nemo my name forever more',4,24,'0,0','sd=001&sh=001/17,17,17&lg=001/17,17,17&ch=800&lh=001/240,213,179&rh=001/240,213,179&hd=001/240,213,179&ey=001/254,202,150&fc=001/240,213,179&hr=201/17,17,17&rs=800&ls=800&bd=001/240,213,179','hr-100-41.lg-270-110.ch-260-62.hd-180-1.sh-300-110','4,24 4,25 4,26 4,27 5,24 5,25 5,26 5,27',23,'','','',''),(9,'Marion','I want to be Bonnie Blond!',6,25,'2,2','sd=001&sh=002/17,17,17&lg=005/255,115,131&ch=018/17,17,17&lh=001/230,200,162&rh=001/230,200,162&hd=001/230,200,162&ey=002&fc=001/230,200,162&hr=202/165,90,24&rs=003/17,17,17&ls=003/17,17,17&bd=001/230,200,162','hr-840-37.lg-715-97.ch-685-110.hd-600-13.sh-730-62','6,24 6,25 6,26 6,27',23,'','','',''),(10,'Brone','Happy to help',0,8,'4,4','sd=001/0&hr=008/115,99,70&hd=002/145,98,55&ey=005/0&fc=001/145,98,55&bd=001/145,98,55&lh=001/145,98,55&rh=001/145,98,55&ch=005/17,17,17&ls=002/17,17,17&rs=002/17,17,17&lg=004/17,17,17&sh=003/17,17,17','hr-125-41.ch-235-110.sh-300-110.hd-180-3.lg-280-110','0,7 1,7 2,7 3,7 4,7 5,7 6,7 7,7 8,7 9,7 0,8 1,8 2,8 3,8 4,8 5,8 6,8 7,8 8,8 9,8',21,'Enjoy the dance!|I\'ve never seen what the other side is like...|My boss doesn\'t allow me to see the disco :(|I serve some mean drinks!','You look like you need this|Hmm, take this','Not sure what you said|Did I hear something?|What?',''),(11,'Marcus','Man of Talent',0,22,'2,2','sd=001/0&hr=010/224,186,120&hd=002/255,204,153&ey=005/0&fc=001/255,204,153&bd=001/255,204,153&lh=001/255,204,153&rh=001/255,204,153&ch=005/59,122,192&ls=002/59,122,192&rs=002/59,122,192&lg=006/119,159,187&sh=001/223,175,209','hr-155-32.sh-290-63.ch-235-78.hd-180-1.lg-285-81','0,21 0,22 0,23 1,21 1,22 1,23',5,'If you hear a funny noise, it\'s just Sid the sloth - he loves to sing!|No ordinary drink for no ordinary Habbo|Stressed out? The Ice House cinema\'s the best place to chill out.|Come on - you don\'t need Dutch courage|We\'ve got the coolest DVD playing this week - check it out!|Wow! You have a real talent!|See a hairy elephant? It\'s just Manny the moody mastodon.','Here you go!|Sure, %lowercaseDrink% it is!','Hello','Cola'),(12,'Dave','Hello, hello',10,7,'2,2','sd=001/0&hr=995/255,255,255&hd=001/255,204,153&ey=001/0&fc=001/255,204,153&bd=001/255,204,153&lh=001/255,204,153&rh=001/255,204,153&ch=995/255,255,255&ls=002/255,255,255&rs=002/255,255,255&lg=999/255,255,255&sh=001/121,94,83','hr-115-41.ch-230-62.fa-1208-63.sh-300-62.hd-180-1.lg-270-96','9,2 9,3 9,4 10,2 10,3 10,4',9,'','','',''),(13,'Sadie','Happy St. Patrick\'s Day!',10,5,'2,2','sd=001&sh=001/36&lg=999/255,255,255&ch=006/163&lh=001/255,204,153&rh=001/255,204,153&hd=001/255,204,153&ey=001&fc=001/255,204,153&hr=006/250,230,150&rs=002/163&ls=002/163&bd=001/255,204,153','hr-515-35.lg-695-96.ch-650-107.hd-600-1.sh-725-81','9,5 9,6 9,7 9,8 10,5 10,6 10,7 10,8',9,'Adorate tutti Bubu, regina delle banane!','','',''),(14,'Reginaldo',':)',23,5,'4,4','sd=001&hd=001/236,214,186&fc=001/236,214,186&bd=001/236,214,186&rh=001/236,214,186&lh=001/236,214,186&hr=001/255,255,255&lg=001/40,40,40&sh=001/150,0,0&rs=002/255,255,255&ls=002/255,255,255&ch=202/255,255,255','hr-100-41.sh-300-73.ch-809-62.hd-180-10.lg-270-110','22,4 23,4 24,4 25,4 26,4 22,5 23,5 24,5 25,5 26,5',17,'It\'s pretty cool working here, I must say|Maybe some day I will become a club member...|Who knew that someone like me would end up working here?','Enjoy the %drink%!,Here you go!','Sorry? I didn\'t catch that|Hello there!|That\'s my name, don\'t wear it out','Water,Juice,Lemonade,Tea'),(15,'Billy','You can call me Bill',5,13,'2,2','sd=001/0&hr=010/224,186,120&hd=002/255,203,152&ey=001/0&fc=001/255,203,152&bd=001/255,203,152&lh=001/255,203,152&rh=001/255,203,152&ch=502/57,65,148&ls=001/57,65,148&rs=001/57,65,148&lg=006/102,102,102&sh=003/51,51,51','hr-155-35.ch-805-82.sh-290-64.hd-180-1.lg-270-87','4,10 5,10 5,11 5,12 5,13',11,'I serve drinks here|Did you know that coffee comes from plants?|Espresso your opinions politely.|Hmmm... the lovely smell of coffee beans...','Coming right up!|Be careful, don\'t hurt yourself!','Cool story, brew.|Yep, that\'s me','Latte,Coffee,Hot Chocolate'),(16,'Phillip','Why not try a nice burger?',1,13,'2,2','sd=001/0&hr=010/255,255,255&hd=002/255,204,153&ey=001/0&fc=001/255,204,153&bd=001/255,204,153&lh=001/255,204,153&rh=001/255,204,153&ch=001/217,113,69&ls=002/217,113,69&rs=002/217,113,69&lg=001/102,102,102&sh=003/47,45,38','sh-300-88.hr-155-40.ch-215-69.hd-180-1.lg-285-64','0,7 0,8 0,9 0,10 0,11 0,12 0,13 1,7 1,8 1,9 1,10 1,11 1,12 1,13',16,'The way to a man\'s heart is through his stomach.|One day i\'ll be famous','Grilling the meat as we speak!|That\'s the special!','','Burger,Water,Drink,Cola,Cow,Reindeer'),(17,'Ariel','Happy to help',0,13,'2,2','sd=001&sh=001/36&lg=001/200,0,0&ch=006/163&lh=001/255,203,152&rh=001/255,203,152&hd=001/255,203,152&ey=001&fc=001/255,203,152&hr=003/250,50,2&rs=002/163&ls=002/163&bd=001/255,203,152','hr-500-59.sh-730-107.ch-650-107.hd-600-1.lg-696-96','0,9 0,10 0,11 0,12 0,13 0,14',43,'Sure is chilly at the Ice Cafe...|Here to serve, every, single, day...|Did you know that I never get a break?','%drink% it is me\'dear\'!:)|Et voila!','Sorry - did you want something?','Juice,Coffee,Coke,Cola,Tea,Chocolate'),(18,'Piers','The master of the kitchen!',11,12,'4,4','sd=001/0&hr=799/255,255,255&hd=002/255,204,153&ey=001/0&fc=001/255,204,153&bd=001/255,204,153&lh=001/255,204,153&rh=001/255,204,153&ch=003/255,255,255&ls=001/255,255,255&rs=001/255,255,255&lg=004/255,255,255&sh=004/255,255,255','hr-891-61.ha-1024-62.ch-220-62.fa-1204-62.sh-300-62.hd-180-1.lg-280-62','3,12 4,12 5,12 6,12 7,12 8,12 9,12 10,12 11,12 12,12 3,13 4,13 5,13 6,13 7,13 8,13 9,13 10,12 11,13 12,13',8,'Would you like to taste my wrath?|The silverback grilla is native to this area.|Heaters gonna heat.|That’s a recipe for disaster.','Yes?|What? I\'m busy you know|A FINE CHOICE#SHOUT|Soup man, how’s it going?','',''),(19,'Marcel','In search of lost time',11,15,'2,2','sd=001&sh=003/154,154,154&lg=001/98,90,32&ch=202/255,210,179&lh=001/255,210,179&rh=001/255,210,179&hd=001/255,204,153&ey=001&fc=001/255,204,153&hr=203/98,98,98&rs=001/255,210,179&ls=001/255,210,179&bd=001/255,204,153','hr-831-41.lg-270-88.ch-809-68.hd-180-1.sh-300-63','7,14 8,14 9,14 10,14 11,14 7,15 8,15 9,15 10,15 11,15 7,16 8,16 9,16 10,16 11,16',3,'','','',''),(20,'Chloe','Service with a smile',6,30,'2,2','sd=001&sh=001/255,115,41&lg=999/255,255,255&ch=006/35,134,182&lh=001/255,203,1522&rh=001/255,203,152&hd=001/255,203,152&ey=001&fc=001/255,203,152&hr=003/250,50,2&rs=002/35,134,182&ls=002/35,134,182&bd=001/255,203,152','hr-500-59.sh-730-107.ch-650-107.hd-600-1.lg-696-96','6,29 6,30',36,'I need to get out of the ice cream booth and into the DJ booth!|Ow there goes my eardrum!#SHOUT|I wish I looked that good in a bikini|When will I, will I be a famous Habbo who gets on the VIP list?|I\'m a fiery redhead - come here boys!','There you go.','Hello sweetie|Hi, how can I help?|Well hello there','Argh,Lemon,Coke,Cola'),(22,'Berith','Serving you with a smile :)',11,0,'4,4','sd=001&sh=002/148,98,32&lg=005/230,49,57&ch=201/255,255,255&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=506/103,78,59&rs=002/255,255,255&ls=002/255,255,255&bd=001/215,175,125','hr-836-37.lg-715-72.ch-822-62.hd-600-1.sh-730-62','6,0 7,0 8,0 9,0 10,0 11,0 12,0 6,1 7,1 8,1 9,1 10,1 11,1 12,1',28,'It\'s pretty cool working here, I must say|Maybe some day I will become a club member...|Who knew that someone like me would end up working here?','','Sorry? I didn\'t catch that|Hello there!|That\'s my name, don\'t wear it out',''),(23,'DJ von Beathoven','Turn the music up!',18,9,'4,4','sd=001&sh=001/36&lg=001&ch=005/163&lh=001/171,122,89&rh=001/171,122,89&hd=001/171,122,89&ey=001&fc=001/171,122,89&hr=931/255,255,255&rs=002/163&ls=002/163&bd=001/8','hr-110-42.ch-235-107.sh-290-80.he-1604-62.hd-180-3.lg-270-62','17,7 17,8 17,9 18,8 18,9',28,'','','',''),(26,'Amber','On the crest of a wave',11,2,'4,4','sd=001&sh=002/148,98,32&lg=200/120,66,21&ch=018/255,230,57&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=023/255,230,50&rs=003/255,230,57&ls=003/255,230,57&bd=001/215,175,125','hr-575-36.sh-730-67.ch-685-93.hd-600-1.lg-827-67','10,0 11,0 12,0 10,1 11,1 12,1 13,1 10,2 11,2 12,2 13,2',26,'Ask a guide for safety hints and tips. They have an guide badge.|P2S is giving your furni away!|I got this job by smiling sweetly at Redtiz for 40 minutes.|Be safe, not sorry! Learn to protect yourself|Quench it!|Glad to be of service!|Oh to be a star! Perhaps one day soon I\'ll be recognised?','This should quench your thirst!|\r\nThirst quenching, soul refreshing!','Hello, come for some safety tips? Ask a guide!','Water,Drink,Chocolate'),(27,'Maarit',':)',11,2,'4,4','sd=001&sh=001/255,115,41&lg=003/0,0,0&ch=018/0,0,0&lh=001/255,203,1522&rh=001/255,203,152&hd=001/255,203,152&ey=001&fc=001/255,203,152&hr=507/225,204,120&rs=001/255,255,255&ls=001/255,255,255&bd=001/255,255,255','hr-836-35.sh-907-62.ch-685-110.hd-600-1.lg-705-110','22,4 23,4 24,4 25,4 26,4 22,5 23,5 24,5 25,5 26,5',19,'','','',''),(28,'ScubaJoe','',26,10,'4,4','sd=001&sh=001/255,255,255&lg=006/255,255,255&ch=003/255,255,255&lh=001/145,98,55&rh=001/145,98,55&hd=001/145,98,55&ey=001&fc=001/145,98,55&hr=008/145,98,55&rs=002/255,255,255&ls=002/255,255,255&bd=001/8','hr-125-37.ch-225-62.sh-290-80.hd-180-5.lg-270-62','26,10 27,10 28,10',60,'','','','Tea,Cola,Drink;Water'),(29,'Skye','On the top of the world',3,0,'4,4','sd=001&sh=002/148,98,32&lg=003/84,98,139&ch=022/97,114,164&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=503/235,240,163&rs=002/97,114,164&ls=002/97,114,164&bd=001/215,175,125','hr-833-34.lg-705-78.ch-819-78.hd-605-2.sh-730-68','1,0 2,0 3,0 4,0',14,'Above the clouds, freedom must be limitless...|I\'m sooo tired! *yawn*','Sure.|Roger that.','Hi, how can I help?|I can\'t hear you, the air is too thin up here!#WHISPER','Cola,Coke,Coffee,Latte'),(31,'Jem','Don\'t look down',1,10,'2,2','sd=001&sh=002/255,115,131&lg=005/255,115,131&ch=015/255,189,189&lh=001/230,200,162&rh=001/230,200,162&hd=001/230,200,162&ey=002&fc=001/230,200,162&hr=501/165,90,24&hrb=501/2,3,4&rs=003/230,200,162&ls=003/230,200,162&bd=001/230,200,162','hr-832-61.lg-715-97.ch-650-71.hd-600-10.sh-730-97','0,8 0,9 0,10 0,11 0,12 0,13 1,8 1,9 1,10 1,11 1,12 1,13 1,14',38,'Quiet please, I\'m thinking#SHOUT|Purchase tickets at the machine by the pool.|It makes me dizzy to move too quickly!|Drink anyone?|Gerbils are good :)|Calm down|Habbo Staff making Habbos smile since 2001','There you go.','You calling? I\'m listening...|I\'m with ya...What\'s up?|Jem\'s the name, drinks are my game|That\'s my name, don\'t wear it out!','Water,Cola,Lemonade'),(32,'Gino','The master of pizza!',1,4,'4,4','sd=001&sh=001/255,0,0&lg=001/255,255,255&ch=995/255,255,255&lh=001/254,202,1508&rh=001/254,202,1508&hd=001/254,202,150&ey=001/254,202,150&fc=001/254,202,1508&hr=802/255,255,255&rs=002/255,255,255&ls=002/255,255,255&bd=001/254,202,150','ha-1024-62.ch-230-62.sh-300-62.hd-180-1.lg-285-62','0,2 0,3 0,4 1,2 1,3 1,4',15,'Grab a pizza and enjoy the magnificent view!','','',''),(33,'Carlo','The master of pizza',1,3,'4,4','sd=001/0&hr=799/255,255,255&hd=002/255,204,153&ey=001/0&fc=001/255,204,153&bd=001/255,204,153&lh=001/255,204,153&rh=001/255,204,153&ch=003/255,255,255&ls=001/255,255,255&rs=001/255,255,255&lg=004/255,255,255&sh=004/255,255,255','lg-285-62.ch-220-62.hd-180-1.sh-300-62','1,1 2,1 3,1 4,1 5,1',15,'Food of the gods.|Gino, do not forget to wash the dishes!','Would you like a drink with that?|Not a problem','I don\'t understand you.#WHISPER','Pizza,Water,Drink'),(38,'Ingemar','Snowballs, schnowballs',39,18,'4,4','sd=001&sh=001/255,255,255&lg=006/255,255,255&ch=001/255,255,255&lh=001/145,98,55&rh=001/145,98,55&hd=001/215,175,125&ey=001&fc=001/145,98,55&ha=10/255,255,255&rs=001/255,255,255&ls=001/255,255,255&bd=001/255,203,152','ha-1010-62.ch-210-62.sh-290-80.hd-180-2.lg-270-62','37,17 37,18 38,18 39,18 40,18',67,'You people are my best customer ever, I like you.|Somewhere in America, there\'s a street named after my dad|Snowballmachines give you snowballs fast|Use the scenery to your advantage','','Watcha! Welcome to the coolest club in the whole hotel','Coffee,Latte'),(39,'Lofar','Service without gravity :)',2,0,'4,4','sd=001&sh=001/194,227,232&lg=001/255,255,255&ch=001/255,255,255&lh=001/240,213,179&rh=001/240,213,179&hd=001/240,213,179&ey=001/254,202,150&fc=001/240,213,179&hr=888/255,255,255&rs=001/255,255,255&ls=001/255,255,255&bd=001/240,213,179','hr-155-35.ha-1023-62.ch-210-62.sh-290-80.hd-180-1.lg-270-62','1,0 2,0 3,0 4,0 1,1 2,1 3,1 4,1',12,'This cafe is out of this world...|A space fish is usually called starfish.|I would have gone to space, but the cost is astronomical!|Two astronauts who were dating, met up for a launch date.|Becoming a space pilot is not easy. It requires a good altitude.','Here you go!|Drink up!|Here\'s the %drink%|Spacylicious!','Sorry, I can\'t hear you in this space suit|What\'s that? Must be the lack of gravity','Drink,Water,Cola'),(40,'Eric  ',':)',1,15,'2,2','sd=001&sh=001/36&lg=201&ch=005/163,20,20&lh=001/171,122,89&rh=001/171,122,89&hd=001/171,122,89&ey=001&fc=001/171,122,89&hr=014/255,255,255&rs=002/163,20,20&ls=002/163,20,20&bd=001/8 look=2,2','lg-281-62.ch-235-73.hd-180-5.sh-290-77','9,18 9,16 9,17 9,19 9,20 9,21 9,22 9,23 8,18 8,16 8,17 8,19 8,20 8,21 8,22 8,23',6,'','','',''),(41,'Laura','Keeps you cool',15,3,'4,4','sd=001&sh=002/148,98,32&lg=005/230,49,57&ch=911/255,255,255&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=017/103,78,59&rs=002/255,255,255&ls=002/255,255,255&bd=001/215,175,125','hr-836-37.lg-715-72.ch-822-62.hd-600-1.sh-730-62','14,3 15,3',31,'Has anyone seen my bikini? I need to cool down!#SHOUT','','',''),(42,'Ray','Chill out and have a coconut!',22,26,'2,2','sd=001&hd=001/201,143,113&fc=001/201,143,113&bd=001/201,143,113&rh=001/201,143,113&lh=001/201,143,113&hr=504/223,218,190&lg=201/230,49,57&sh=002/246,172,49&rs=003/201,143,113&ls=003/201,143,113&ch=501/246,172,49look=2,2','hr-829-34.lg-281-72.ch-803-62.hd-180-19.sh-295-68','22,24 23,24 22,25 23,25 22,26 23,26 22,27 23,27 22,28 23,28 22,29 23,29 22,30 23,30',25,'Official Fansite are voted by YOU, the Habbo community!|Did you know the Official Fansites are changed every 3 months?|If they aren\'t listed once you click the billboard then they aren\'t Official!|Once refreshed, visit an Official Fansite!|Click the billboard now to visit our Official Fansites!|Official Fansites have great events, comps and radio shows!','Refreshing!|Here you are, with extra coconut milk, only for you ;)|Here you go, hope you like the umbrella.|You sure are thirsty, huh?|You can only have one at a time!|That\'s my name! As in the beams of golden sunshine and not the sunglasses.|Hi my name is what? my name is who? my name is...ray','','Cola,Coke,Coconut Milk'),(43,'Tao','Tea is serenity',10,4,'4,4','sd=001&sh=001/36&lg=001&ch=002/163,20,20&lh=001/171,122,89&rh=001/171,122,89&hd=001/171,122,89&ey=001&fc=001/171,122,89&hr=791/255,255,255&rs=001/163,20,20&ls=001/163,20,20&bd=001/8','lg-270-62.ch-210-73.hd-207-3.fa-1208-62.sh-290-81','8,2 9,2 10,2 11,2 8,3 9,3 10,3 11,3 8,4 9,4 10,4 11,4',18,'','','','Tea,Drink'),(44,'Harry','Happy to help',8,21,'2,2','sd=001&sh=003/41,41,41&lg=006/51,51,51&ch=202/139,24,32&lh=001/255,210,179&rh=001/255,210,179&hd=001/255,204,153&ey=001&fc=001/255,204,153&hr=203/103,78,59&hrb=203/2,3,4&rs=001/255,255,255&ls=001/255,255,255&bd=001/255,204,153','hr-831-61.ch-809-62.sh-290-110.hd-180-1.lg-285-64','9,18 9,16 9,17 9,19 9,20 9,21 9,22 9,23 8,18 8,16 8,17 8,19 8,20 8,21 8,22 8,23',1,'Please keep it down people are trying to think!#SHOUT|Only use the Call for help in an emergency!|Want to know more about Habbo Hotel? Ask a Habbo Guide!|Is it me or is something BIG about to happen?|In Trouble? Call for Moderator assistance using the Blue Question Mark!|There\'s no such thing as a free lunch or free credits!','Why Hello there! *Shakes Habbo Hand* My name\'s Harry.|Hello, Hello, Hello!|Hello and welcome to Habbo Hotel! Enjoy your stay! :)','Why Hello there! *Shakes Habbo Hand* My name\'s Harry.|Hello, Hello, Hello!|Hello and welcome to Habbo Hotel! Enjoy your stay! :)','Water'),(45,'Miho','My katana thinks you\'re cute!',14,25,'2,2','sd=001&sh=001/36&lg=200/204,204,204&ch=204/204,204,204&lh=001/215,175,125&rh=001/215,175,125&hd=001/215,175,125&ey=001&fc=001/215,175,125&hr=504/50,91,106&rs=002/204,204,204&ls=002/204,204,204&bd=001/8','hr-829-53.lg-280-62.ch-245-62.hd-180-2.sh-290-80','14,24 14,25',27,'Zen Garden is the ultimate in relaxation|Listen to the breeze blowing through the leaves|Welcome to my garden a place of quiet reflection...|Listen to the breeze blowing through the leaves...','I hope you make peace with this|Relax with this|Relaxation can be achieved this this','That is my name.|Say again - it\'s a bit noisy in here#WHISPER|You bring confusion to my mind, and pain to my ears...#WHISPER|','Water');
/*!40000 ALTER TABLE `rooms_bots` ENABLE KEYS */;
UNLOCK TABLES;