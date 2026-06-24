
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}</title>
	
	<script type="text/javascript" src="{{ site.staticContentPath }}/web-gallery/maintenance/new/js/jquery.min.js"></script>
	<link href="{{ site.staticContentPath }}/web-gallery/maintenance/new/style/maintenance.css" rel="stylesheet" type="text/css" />
	

</head>
<body>
<div id="container">
	<div id="content">
		<div id="header" class="clearfix">
			<h1><span></span></h1>
		</div>
		<div id="process-content">

<div class="fireman">

<h1>{{ locale.maintenance_maintenance_break }}</h1>

<p>
{{ locale.maintenance_sorry_habbo_is_being_worked_on_at_the_moment }}</p>
<p>{{ locale.maintenance_we_ll_be_back_soon_we_promise }}</p>
</div>

<div class="tweet-container">

<h2>{{ locale.maintenance_what_s_going_on }}</h2>

<div class="tweet"></div>
<a class="twitter-timeline" href="https://twitter.com/ShockwaveHabbo?ref_src=twsrc%5Etfw">{{ locale.maintenance_tweets_by_classic_habbo }}</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
</div>

{% include "base/footer.tpl" %}

</body>
</html>