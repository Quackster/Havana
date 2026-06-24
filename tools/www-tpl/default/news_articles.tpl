
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	{% if newsPage == "custom_event" %}
	<title>{{ site.siteName }}{{ locale.news_articles_space_academy_points }}</title>	
	{% else %}
	<title>{{ site.siteName }}{{ locale.news_articles_news }} {{ ("currentArticle" is present) ? currentArticle.title : "No news" }} </title>
	{% endif %}
	
<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ locale.news_articles_retro_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>

<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "a";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<meta name="description" content="{{ locale.news_articles_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free_retro_today }}" />
<meta name="keywords" content="{{ locale.news_articles_retro_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

{% if "currentArticle" is present %}
<meta name="twitter:title" content="{{ currentArticle.title }}" />
<meta name="twitter:description" content="{{ currentArticle.shortstory }}" />
{% if currentArticle.articleImage != "" %}
<meta name="twitter:image" content="{{ currentArticle.articleImage }}" />
{% endif %}
{% endif %}

<!--[if IE 8]>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie8.css" type="text/css" />
<![endif]-->
<!--[if lt IE 8]>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie.css" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie6.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/pngfix.js" type="text/javascript"></script>
<script type="text/javascript">
try { document.execCommand('BackgroundImageCache', false, true); } catch(e) {}
</script>

<style type="text/css">
body { behavior: url({{ site.staticContentPath }}/web-gallery/js/csshover.htc); }
</style>

<![endif]-->
<meta name="build" content="{{ site.siteName }}" />
</head>
{% if session.loggedIn == false %}
<body id="news" class="anonymous ">
{% else %}
<body id="news" class=" ">
{% endif %}
{% include "base/header.tpl" %}
   
<div id="content-container">

<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		{% if newsPage == "news" %}
		{% set articleLink = "articles" %}
		<ul>
			<li class="">
			<a href="{{ site.sitePath }}/community">{{ locale.news_articles_community }}</a>			</li>
    		<li class="selected">
				{{ locale.news_articles_news_text }}    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/tag">{{ locale.news_articles_tags }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/community/events">{{ locale.news_articles_events }}</a>    		</li>
    		<li class="last">
				<a href="{{ site.sitePath }}/community/fansites">{{ locale.news_articles_fansites }}</a>    		</li>
    		<!-- <li class="last">
				<a href="{{ site.sitePath }}/events/steampunk">{{ locale.news_articles_steampunk }}</a>    		</li> 
			-->
		</ul>
		{% endif %}
		{% if newsPage == "events" %}
		{% set articleLink = "community/events" %}
		<ul>
			<li class="">
			<a href="{{ site.sitePath }}/community">{{ locale.news_articles_community }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/articles">{{ locale.news_articles_news_text }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/tag">{{ locale.news_articles_tags }}</a>    		</li>
    		<li class="selected">
				{{ locale.news_articles_events }}			</li>
    		<li class="last">
				<a href="{{ site.sitePath }}/community/fansites">{{ locale.news_articles_fansites }}</a>    		</li>
    		<!-- <li class="last">
				<a href="{{ site.sitePath }}/events/steampunk">{{ locale.news_articles_steampunk }}</a>    		</li> 
			-->
		</ul>
		{% endif %}
		{% if newsPage == "fansites" %}
		{% set articleLink = "community/fansites" %}
		<ul>
			<li class="">
			<a href="{{ site.sitePath }}/community">{{ locale.news_articles_community }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/articles">{{ locale.news_articles_news_text }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/tag">{{ locale.news_articles_tags }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/community/events">{{ locale.news_articles_events }}</a>    		</li>
    		<li class="selected last">
				{{ locale.news_articles_fansites }}			</li>
			<!-- 
    		<li class="selected">
				{{ locale.news_articles_fansites }}			</li>
    		<li class="last">
				<a href="{{ site.sitePath }}/events/steampunk">{{ locale.news_articles_steampunk }}</a>    		</li>
			-->
		</ul>
		{% endif %}
		
		<!-- 
		{% if newsPage == "custom_event" %}
		{% set articleLink = "articles" %}
		<ul>
			<li class="">
			<a href="{{ site.sitePath }}/community">{{ locale.news_articles_community }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/articles">{{ locale.news_articles_news_text }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/tag">{{ locale.news_articles_tags }}</a>    		</li>
			<li class="">
				<a href="{{ site.sitePath }}/community/photos">{{ locale.news_articles_photos }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/community/events">{{ locale.news_articles_events }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/community/fansites">{{ locale.news_articles_fansites }}</a>    		</li>
    		<li class="selected last">
				{{ locale.news_articles_steampunk }}			</li>
		</ul>
		{% endif %}
		-->
    </div>
</div>

	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
		{% if newsPage == "custom_event" %}
		<div id="column1" class="column" style="width: 770px">
		{% else %}
		<div id="column1" class="column">     		
			<div class="habblet-container ">		
				<div class="cbb clearfix default ">
						{% if newsPage == "news" %}
						<h2 class="title">{{ locale.news_articles_news_text }}</h2>
						{% endif %}
						{% if newsPage == "events" %}
						<h2 class="title">{{ locale.news_articles_events }}</h2>
						{% endif %}
						{% if newsPage == "fansites" %}
						<h2 class="title">{{ locale.news_articles_fansites }}</h2>
						{% endif %}
						<div id="article-archive">
						{% if monthlyView %}
							{% for month in months.entrySet() %}
								{% include "habblet/news_sidebar.tpl" with { "articles": month.getValue(), "header": month.getKey() } %}
							{% endfor %}
						{% elseif archiveView %}
							{% for archived in archives.entrySet() %}
								{% include "habblet/news_sidebar.tpl" with { "articles": archived.getValue(), "header": archived.getKey() } %}
							{% endfor %}
						{% else %}
							{% include "habblet/news_sidebar.tpl" with { "articles": articlesToday, "header": 'Today' } %}
							{% include "habblet/news_sidebar.tpl" with { "articles": articlesYesterday, "header": 'Yesterday' } %}
							{% include "habblet/news_sidebar.tpl" with { "articles": articlesThisWeek, "header": 'Last week' } %}
							{% include "habblet/news_sidebar.tpl" with { "articles": articlesThisMonth, "header": 'Last month' } %}
							{% include "habblet/news_sidebar.tpl" with { "articles": articlesPastYear, "header": 'This year' } %}
						{% endif %}
						{% if (not archiveView) %}
						<a href="{{ site.sitePath }}/{{ articleLink }}/archive">{{ locale.news_articles_more_news_raquo }}</a>
						{% endif %}		
					</div>
				</div>
			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
		</div>
		<div id="column2" class="column">
		{% endif %}
			<div class="habblet-container ">		
				<div class="cbb clearfix notitle ">
					<div id="article-wrapper">
					    {% if "currentArticle" is present %}
						<h2>{{ currentArticle.title }}</h2>
						{% if newsPage != "custom_event" %}
						<div class="article-meta">{{ locale.news_articles_posted }} {{ currentArticle.getDate() }}
							<!-- <a href="{{ site.sitePath }}/{{ articleLink }}/category/{{ currentArticle.getCategoryLower() }}"> -->
							
							{% set num = 1 %}
							{% if (currentArticle.getCategories().length) > 0 %}
							{% for category in currentArticle.getCategories() %}				
								<a href="{{ site.sitePath }}/{{ articleLink }}/category/{{ category.getIndex() }}">{{ category.getLabel() }}</a>{% if num < (currentArticle.getCategories()|length) %},{% endif %}{% set num = num + 1 %}
							{% endfor %}
							{% endif %}			
							<!-- </a> -->
						</div>
						{% endif %}
												
						{% if currentArticle.articleImage != "" %}
						<img src="{{ currentArticle.articleImage }}" class="article-image"/>
						{% endif %}
						<p class="summary">{{ currentArticle.shortstory }}</p>
		
						<div class="article-body">
							<p>{{ currentArticle.getEscapedStory() }}</p>

							{% if newsPage != "custom_event" %}
							<div class="article-author">- {{ currentArticle.getAuthor() }}</div>
							{% endif %}
							
							<script type="text/javascript" language="Javascript">
								document.observe("dom:loaded", function() {
									$$('{{ locale.news_articles_article_images_a|escape('js') }}').each(function(a) {
										Event.observe(a, 'click', function(e) {
											Event.stop(e);
											Overlay.lightbox(a.href, "{{ locale.news_articles_image_is_loading|escape('js') }}");
										});
									});
									
									$$('a.article-5').each(function(a) {
										a.replace(a.innerHTML);
									});
								});
							</script>
						</div>
						{% endif %}
					</div>
				</div>
			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
		</div>
<script type="text/javascript">
HabboView.run();
</script>
<script type="text/javascript">
HabboView.run();

</script>


<div id="column3" class="column">
				<div class="habblet-container ">
						<div class="ad-container">
						{% include "base/ads_container.tpl" %}
						</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
</div>

<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

    </div>
{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>