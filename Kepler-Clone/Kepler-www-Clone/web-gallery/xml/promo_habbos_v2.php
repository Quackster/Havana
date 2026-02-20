<?php
/*================================================================+\
|| # PHPRetro - An extendable virtual hotel site and management
|+==================================================================
|| # Copyright (C) 2009 Yifan Lu. All rights reserved.
|| # http://www.yifanlu.com
|| # Parts Copyright (C) 2009 Meth0d. All rights reserved.
|| # http://www.meth0d.org
|| # All images, scripts, and layouts
|| # Copyright (C) 2009 Sulake Ltd. All rights reserved.
|+==================================================================
|| # PHPRetro is provided "as is" and comes without
|| # warrenty of any kind. PHPRetro is free software!
|| # License: GNU Public License 3.0
|| # http://opensource.org/licenses/gpl-license.php
\+================================================================*/

$page['dir'] = '\xml';
require_once('../includes/core.php');

header("Content-Type: text/xml");

if($settings->find("site_generate_promo_habbos") == 0){
	if(!file_exists('./xml/promo_habbos_v2.xml') || ($user->user("rank") > 4 && (isset($_GET['recache']) && $_GET['recache'] == "true"))){
		$fh = fopen('./xml/promo_habbos_v2.xml', 'w');
		fwrite($fh, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<habbos url=\"".PATH."/habbo-imaging/avatar/\">\n");
		$generator = new HoloFigureCheck();
		$i = 0;
		while ($i < 30) {
			if($input->IsEven($i)){ $gender = "M"; }else{ $gender = "F"; }
			$figure = $generator->generateFigure(false,$gender);
			fwrite($fh, "<habbo gender=\"".strtolower($figure[1])."\" figure=\"".$figure[0]."\" hash=\"".$user->avatarURL($figure[0],"b,3,3,sml,1,0",1)."\" />\n");
			$i++;
		}
		fwrite($fh, "</habbos>");
		echo file_get_contents('./xml/promo_habbos_v2.xml');
	}else{
		echo file_get_contents('./xml/promo_habbos_v2.xml');
	}
}else{
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<habbos url="<?php echo PATH; ?>/habbo-imaging/avatar/">
<?php
$generator = new HoloFigureCheck();
$i = 0;
while ($i < $settings->find("site_generate_promo_habbos")) {
$i++;
$figure = $generator->generateFigure(false);
?>

<habbo gender="<?php echo strtolower($figure[1]); ?>" figure="<?php echo $figure[0]; ?>" hash="<?php echo $user->avatarURL($figure[0],"b,3,3,sml,1,0",1); ?>" />
<?php } ?>
</habbos>
<?php } ?>