
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ locale.maintenance_old_habbo }}</title>
	
	<script type="text/javascript" src="https://retro.alex-dev.org/web-gallery/maintenance/new/js/jquery.min.js"></script>
	<script type="text/javascript" src="https://retro.alex-dev.org/web-gallery/maintenance/new/js/jquery.tweet.js"></script>
	
	<link href="https://retro.alex-dev.org/web-gallery/maintenance/new/style/maintenance.css" rel="stylesheet" type="text/css" />
	

</head>
<body>
<div id="container">
	<div id="content">
		<div id="header" class="clearfix">
			<h1><span></span></h1>
		</div>
		<div id="process-content">

<div class="fireman">

<h1>{{ locale.maintenance_old_maintenance_break }}</h1>

<p>
{{ locale.maintenance_old_sorry_habbo_is_being_worked_on_at_the_moment }}</p>
<p>{{ locale.maintenance_old_we_ll_be_back_soon_we_promise }}</p>
</div>

<div class="tweet-container">

<h2>{{ locale.maintenance_old_what_s_going_on }}</h2>

<div class="tweet"></div>

</div>

{% include "base/footer.tpl" %}


<script type='text/javascript'>
$(document).ready(function(){
  $(".tweet").tweet({
    username: "phpretro",
    count: 5
  });
});
</script>

</body>
</html>