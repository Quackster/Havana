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
$data = new xml_sql;

header("Content-Type: text/xml");
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
echo "<habbos>\n";

$sql = $data->select1();
while ($row = $db->fetch_row($sql)) {

	$form_badge = $user->GetUserBadge($row[1]);
	$form_group_badge = $user->GetUserGroupBadge($row[0]);

	if($form_badge != false){
		$form_badge = $settings->find("site_c_images_path").$settings->find("site_badges_path").$form_badge.".gif";
	} else {
		$form_badge = "";	
	}

	if($form_group_badge != false){
		$form_group_badge = "groupBadge=\"".PATH."/habbo-imaging/badge/".$form_group_badge.".gif\"";
	} else {
		$form_group_badge = "";
	}

	if($user->IsUserOnline($row[0]) == true){
	$status = "1";
	} else {
	$status = "0";
	}

	printf("<habbo id=\"%s\" name=\"%s\" motto=\"%s\" url=\"".PATH."/home/%s\" image=\"".$user->avatarURL($row[3],"b,4,3,sml,1,0")."\" badge=\"%s\" status=\"%s\" %s />\n", $row[0], $row[1], $input->HoloText($row[2]), $row[1], $form_badge, $status, $form_group_badge);

}

echo "</habbos>";
?>
