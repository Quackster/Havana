<?php

$hotel_view = "au";
$https = false;

if (isset($_GET['country'])) {
	$countries = array("au", "at", "br", "ca", "ch", "cn", "de", "dk", "es", "fi", "fr", "it", "jp", "nl", "no", "pl", "ru", "se", "sg", "uk", "us");
	
	if (in_array($_GET['country'], $countries)) {
		$hotel_view = $_GET['country'];
	}
}

if (isset($_GET['ssl'])) {
	$https = ($_GET['ssl'] == "true");
}


header("Content-Type: text/plain");
$texts = file_get_contents('template_variables.txt');

if (!$https) {
	$texts = str_replace("[CDN_IMAGES]", "[HTTP_TAG]://images.classichabbo.com/c_images/", $texts);
} else {
	$texts = str_replace("[CDN_IMAGES]", "[HTTP_TAG]://cdn.classichabbo.com/c_images/", $texts);
}

$texts = str_replace("[HTTP_TAG]", ($https ? "https" : "http"), $texts);
$texts = str_replace("[HOTEL_VIEW_TAG]", $hotel_view, $texts);
$texts = str_replace("[SSL_DOMAIN]", ($https ? "cdn.classichabbo.com" : "images.classichabbo.com"), $texts);

echo $texts;

?>