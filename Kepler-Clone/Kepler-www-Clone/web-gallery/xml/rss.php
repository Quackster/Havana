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
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<rss xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:taxo="http://purl.org/rss/1.0/modules/taxonomy/" version="2.0">
  <channel>
    <title><?php echo SHORTNAME; ?> ~</title>
    <link><?php echo PATH; ?></link>
    <description />
<?php
$sql = $db->query("SELECT * FROM ".PREFIX."news ORDER BY time DESC LIMIT 10");
while($row = $db->fetch_assoc($sql)){
	$row['summary'] = $input->HoloText($row['summary'], true);
	$row['title'] = $input->HoloText($row['title'], true);
	$row['title_safe'] = $input->stringToURL($input->HoloText($row['title'],true),true,true);
	$row['date'] = date('D, j M Y H:i:s e', $row['time']);
	$row['timestamp'] = date('c', $row['time']);
?>

    <item>
      <title><?php echo $input->HoloText($row['title']); ?></title>

      <link><?php echo PATH."/articles/".$row['id']."-".$row['title_safe']; ?></link>
      <description><?php echo $row['summary']; ?></description>
      <pubDate><?php echo $row['date']; ?></pubDate>
      <guid isPermaLink="false"><?php echo PATH."/articles/".$row['id']."-".$row['title_safe']; ?></guid>
      <dc:date><?php echo $row['timestamp']; ?></dc:date>
    </item>
	
<?php } ?>
  </channel>
</rss>