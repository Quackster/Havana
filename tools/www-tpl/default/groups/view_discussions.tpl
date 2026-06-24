
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.groups_view_discussions_group_home }} {% autoescape 'html' %}{{ group.getName }}{% endautoescape %} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.groups_view_discussions_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.groups_view_discussions_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/myhabbo.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/skins.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/dialogs.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/control.textarea.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/myhabbo.css" type="text/css" />
	<link href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/assets.css" type="text/css" rel="stylesheet" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/homeview.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/lightwindow.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/homeauth.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/group.css" type="text/css" />

<style type="text/css">
    #playground, #playground-outer {
	    width: 922px;
	    height: 1360px;
    }
</style>

<script type="text/javascript">
document.observe("dom:loaded", function() { initView({{ group.id }}, {% if session.loggedIn == false %}-1{% else %}{{ playerDetails.id }}{% endif %}); });
</script>

<link href="{{ site.staticContentPath }}/web-gallery/styles/discussions.css" type="text/css" rel="stylesheet"/>
<meta name="description" content="{{ locale.groups_view_discussions_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.groups_view_discussions_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.groups_view_discussions_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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

<meta name="build" content="{{ locale.groups_view_discussions_havanaweb }}" />
</head>

{% if session.loggedIn == false %}
<body id=" " class=" anonymous ">
{% else %}
<body id=" " class=" ">
{% endif %}
{% include "../base/header.tpl" %}

<div id="content-container">

	{% if session.currentPage == "games" %}
	<div id="navi2-container" class="pngbg">
		<div id="navi2" class="pngbg clearfix">
		<ul>
				<li class="">
					<a href="/games">{{ locale.groups_view_discussions_games }}</a>
				</li>
				<li class="{% if group.getAlias() == 'battleball_rebound' %}selected{% endif %}">
					<a href="/groups/battleball_rebound">{{ locale.groups_view_discussions_battleball_rebound }}</a>
				</li>
				<li class="{% if group.getAlias() == 'snow_storm' %}selected{% endif %}">
					<a href="/groups/snow_storm">{{ locale.groups_view_discussions_snowstorm }}</a>
				</li>
				<li class="{% if group.getAlias() == 'wobble_squabble' %}selected{% endif %}">
					<a href="/groups/wobble_squabble">{{ locale.groups_view_discussions_wobble_squabble }}</a>
				</li>
				<li class="{% if group.getAlias() == 'lido' %}selected{% endif %} last">
					<a href="/groups/lido">{{ locale.groups_view_discussions_lido_diving }}</a>
				</li>
		</ul>
		</div>
	</div>
	{% endif %}
	
	{% if session.currentPage == "community" %}
	<div id="navi2-container" class="pngbg">
		<div id="navi2" class="pngbg clearfix">
			<ul>
				<li class="">
					<a href="{{ site.sitePath }}/community">{{ locale.groups_view_discussions_community }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/articles">{{ locale.groups_view_discussions_news }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/tag">{{ locale.groups_view_discussions_tags }}</a>    		</li>
				<!-- <li class="">
					<a href="{{ site.sitePath }}/community/photos">{{ locale.groups_view_discussions_photos }}</a>    		</li> -->
				<li class="">
					<a href="{{ site.sitePath }}/community/events">{{ locale.groups_view_discussions_events }}</a>    		</li>
				<li class=" last">
					<a href="{{ site.sitePath }}/community/fansites">{{ locale.groups_view_discussions_fansites }}</a>    		</li>
			</ul>
		</div>
	</div>
	{% endif %}

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="mypage-wrapper" class="cbb blue">
<div class="box-tabs-container box-tabs-left clearfix">
	{% if editMode %}

	{% else %}
		{% if (session.loggedIn) and (group.hasAdministrator(playerDetails.getId())) %}
		<a href="#" id="myhabbo-group-tools-button" class="new-button dark-button edit-icon" style="float:left"><b><span></span>{{ locale.groups_view_discussions_edit }}</b><i></i></a>	
		{% endif %}
		<div class="myhabbo-view-tools">
		{% if session.loggedIn == false %}
		<a href="#" id="reporting-button" style="display: none;">{{ locale.groups_view_discussions_show_report_buttons }}</a>
		{% else %}
			{% if (group.isPendingMember(playerDetails.getId()) == false) %}
				{% if (group.isMember(playerDetails.getId()) == false) %}
					{% if group.getGroupType() == 0 or group.getGroupType() == 3 %}
					<a href="{{ site.sitePath }}/groups/actions/join?groupId=101" id="join-group-button">{{ locale.groups_view_discussions_join }}</a>
					{% elseif group.getGroupType() == 1 %}
					<a href="{{ site.sitePath }}/groups/actions/join?groupId=101" id="join-group-button">{{ locale.groups_view_discussions_request_membership }}</a>	
					{% endif %}
					<a href="#" id="reporting-button" style="display: none;">{{ locale.groups_view_discussions_show_report_buttons }}</a>
				{% else %}
					{% if group.getOwnerId() != playerDetails.getId() %}
					<a href="{{ site.sitePath }}/groups/actions/leave?groupId=101" id="leave-group-button">{{ locale.groups_view_discussions_leave_group }}</a>
					{% endif %}
					
					{% if groupMember.isFavourite(group.id) %}
					<a href="#" id="deselect-favorite-button">{{ locale.groups_view_discussions_remove_favorite }}</a>
					{% else %}
					<a href="#" id="select-favorite-button">{{ locale.groups_view_discussions_make_favorite }}</a>
					{% endif %}
				{% endif %}
			{% endif %}
		{% endif %}
	</div>
	{% endif %}

    <h2 class="page-owner">
		{% autoescape 'html' %}
    	{{ group.getName }} 
		{% endautoescape %}
		{% if group.getGroupType() == 1 %}<img src="{{ site.staticContentPath }}/web-gallery/images/groups/status_exclusive_big.gif" width="18" height="16" alt="{{ locale.groups_view_discussions_exclusive_group }}" title="{{ locale.groups_view_discussions_exclusive_group }}" class="header-bar-group-status" />{% elseif group.getGroupType() == 2%}<img src="{{ site.staticContentPath }}/web-gallery/images/groups/status_closed_big.gif" width="18" height="16" alt="myhabbo.headerbar.closed_group" title="myhabbo.headerbar.closed_group" class="header-bar-group-status" />{% endif %}   				    </h2>
    <ul class="box-tabs">
        <li><a href="{{ group.generateClickLink() }}">{{ locale.groups_view_discussions_front_page }}</a><span class="tab-spacer"></span></li>
        <li class="selected"><a href="{{ group.generateClickLink() }}/discussions">{{ locale.groups_view_discussions_discussion_forum }} {% if ((group.getForumType().getId() == 1) or (group.getForumPermission().getId() >= 1)) %}<img src="{{ site.staticContentPath }}/web-gallery/images/grouptabs/privatekey.png" title="{{ locale.groups_view_discussions_private_forum }}" alt="{{ locale.groups_view_discussions_private_forum }}" />{% endif %}</a><span class="tab-spacer"></span></li>
    </ul>
</div>
<div id="mypage-content">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="content-1col">
            <tr>
                <td valign="top" style="width: 750px;" class="habboPage-col rightmost">
                    <div id="discussionbox">
					{% if (canViewForum == false) %}
					<div class="box-content">

<h1>{{ locale.groups_view_discussions_oops }}</h1>

<p>
        {{ locale.groups_view_discussions_view_forums_denied_please_check_that_you_are_logged_in_and_have_the_appropriate_rights_to_view_the_forums_if_you_are_logged_in_and_still_can_t_view_the_forums_the_group_may_be_private_if_so_you_need_to_join_the_group_in_order_to_view_the_forums }} 
 <br />
</p>

</div>
{% else %}
					
<div id="group-topiclist-container">

<div class="topiclist-header clearfix">
		{% if session.loggedIn == false %}
		{{ locale.groups_view_discussions_please_sign_in_to_post_new_threads }}
		{% elseif canPostForum %}
        <input type="hidden" id="email-verfication-ok" value="1"/>
        <a href="#" id="newtopic-upper" class="new-button verify-email newtopic-icon" style="float:left"><b><span></span>{{ locale.groups_view_discussions_new_thread }}</b><i></i></a>
		{% endif %}
        <div class="page-num-list">
    {{ locale.groups_view_discussions_view_page }}
{% if currentPage != 1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/1">&lt;&lt;</a> 
{% endif %}

{% if previousPage5 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage5 }}">{{ previousPage5 }}</a>
{% endif %}

{% if previousPage4 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage4 }}">{{ previousPage4 }}</a>
{% endif %}

{% if previousPage3 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage3 }}">{{ previousPage3 }}</a>
{% endif %}

{% if previousPage2 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage2 }}">{{ previousPage2 }}</a>
{% endif %}

{% if previousPage1 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage1 }}">{{ previousPage1 }}</a>
{% endif %}
{{ currentPage }}
{% if nextPage1 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage1 }}">{{ nextPage1 }}</a>
{% endif %}

{% if nextPage2 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage2 }}">{{ nextPage2 }}</a>
{% endif %}

{% if nextPage3 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage3 }}">{{ nextPage3 }}</a>
{% endif %}

{% if nextPage4 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage4 }}">{{ nextPage4 }}</a>
{% endif %}

{% if nextPage5 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage5 }}">{{ nextPage5 }}</a>
{% endif %}

{% if pages != currentPage %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ pages }}">&gt;&gt;</a> 
{% endif %}
</div>
</div>
<table class="group-topiclist" border="0" cellpadding="0" cellspacing="0" id="group-topiclist-list">
	<tr class="topiclist-columncaption">
		<td class="topiclist-columncaption-topic">{{ locale.groups_view_discussions_thread_and_first_poster }}</td>
		<td class="topiclist-columncaption-lastpost">{{ locale.groups_view_discussions_last_post }}</td>
		<td class="topiclist-columncaption-replies">{{ locale.groups_view_discussions_replies }}</td>
		<td class="topiclist-columncaption-views">{{ locale.groups_view_discussions_views }}</td>
	</tr>
{% set num = 0 %}
{% for topic in discussionTopics %}
	{% if num % 2 == 0 %}
	<tr class="topiclist-row-even">
	{% else %}
	<tr class="topiclist-row-odd">
	{% endif %}
		<td class="topiclist-rowtopic" valign="top">
			<div class="topiclist-row-content">
			<a class="topiclist-link {% if topic.isStickied() %}icon icon-sticky{% endif %}" href="{{ group.generateClickLink() }}/discussions/{{ topic.getId() }}/id">
			{% autoescape 'html' %}{{ topic.getTopicTitle }}{% endautoescape %}</a>
			<span class="topiclist-row-topicsticky">
			{% if topic.isOpen() == false %}
			<img src="{{ site.staticContentPath }}/web-gallery/images/groups/status_closed.gif" title="{{ locale.groups_view_discussions_closed_thread }}" alt="{{ locale.groups_view_discussions_closed_thread }}">
			{% endif %}
			</span>
			{{ locale.groups_view_discussions_page }}
                <a href="{{ group.generateClickLink() }}/discussions/{{ topic.getId() }}/id/page/1" class="topiclist-page-link">1</a>
				{% if topic.getRecentPages()|length > 0 %}
                ...
				{% endif %}
				{% for page in topic.getRecentPages() %}
                    <a href="{{ group.generateClickLink() }}/discussions/{{ topic.getId() }}/id/page/{{ page }}" class="topiclist-page-link">{{ page }}</a>
				{% endfor %})
			<br/>
			<span><a class="topiclist-row-openername" href="{{ site.sitePath }}/home/{{ topic.getCreatorName() }}">{{ topic.getCreatorName() }}</a></span>
			
				<span class="latestpost-today">{{ topic.getCreatedDate('MMM dd, yyyy') }}</span>
			<span class="latestpost">({{ topic.getCreatedDate('h:mm a') }})</span>
			{% if (session.loggedIn) and (topic.isNew()) %}
			<span class="topiclist-row-topicnew">{{ locale.groups_view_discussions_new }} <img src="{{ site.staticContentPath }}/web-gallery/images/discussions/New_arrow.gif" alt="{{ locale.groups_view_discussions_new }}"/></span>{% endif %}			</div>
							</div>

		</td>
		<td class="topiclist-lastpost" valign="top">
		    <a class="lastpost-page-link" href="{{ group.generateClickLink() }}/discussions/{{ topic.getId() }}/id/page/{{ topic.getReplyPages() }}">
				<span class="lastpost-today">{{ topic.getLastMessage('MMM dd, yyyy') }}</span>
            <span class="lastpost">({{ topic.getLastMessage('h:mm a') }})</span></a><br />
			<span class="topiclist-row-writtenby">{{ locale.groups_view_discussions_by }}</span> <a class="topiclist-row-openername" href="{{ site.sitePath }}/home/{{ topic.getLastReplyName() }}">{{ topic.getLastReplyName() }}</a>

		</td>
 		<td class="topiclist-replies" valign="top">{{ topic.getReplyCount() - 1 }}</td>
 		<td class="topiclist-views" valign="top">{{ topic.getViews() }}</td>
	</tr>
	{% set num = num + 1 %}
{% endfor %}
</table>
<div class="topiclist-footer clearfix">
        {% if canPostForum %}<a href="#" id="newtopic-lower" class="new-button verify-email newtopic-icon" style="float:left"><b><span></span>{{ locale.groups_view_discussions_new_thread }}</b><i></i></a>{% endif %}
    <div class="page-num-list">
    {{ locale.groups_view_discussions_view_page }}
{% if currentPage != 1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/1">&lt;&lt;</a> 
{% endif %}

{% if previousPage5 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage5 }}">{{ previousPage5 }}</a>
{% endif %}

{% if previousPage4 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage4 }}">{{ previousPage4 }}</a>
{% endif %}

{% if previousPage3 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage3 }}">{{ previousPage3 }}</a>
{% endif %}

{% if previousPage2 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage2 }}">{{ previousPage2 }}</a>
{% endif %}

{% if previousPage1 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ previousPage1 }}">{{ previousPage1 }}</a>
{% endif %}
{{ currentPage }}
{% if nextPage1 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage1 }}">{{ nextPage1 }}</a>
{% endif %}

{% if nextPage2 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage2 }}">{{ nextPage2 }}</a>
{% endif %}

{% if nextPage3 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage3 }}">{{ nextPage3 }}</a>
{% endif %}

{% if nextPage4 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage4 }}">{{ nextPage4 }}</a>
{% endif %}

{% if nextPage5 != -1 %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ nextPage5 }}">{{ nextPage5 }}</a>
{% endif %}

{% if pages != currentPage %}
<a href="{{ group.generateClickLink() }}/discussions/page/{{ pages }}">&gt;&gt;</a> 
{% endif %}
</div>
</div>
</div>

<script type="text/javascript">
L10N.put("myhabbo.discussion.error.topic_name_empty", "{{ locale.groups_view_discussions_topic_name_cannot_be_empty|escape('js') }}");
L10N.put("register.error.security_code", "{{ locale.groups_view_discussions_the_security_code_was_invalid_please_try_again|escape('js') }}");
Discussions.initialize("{{ group.getId() }}", "", null);
Discussions.captchaPublicKey = "1566956860";
Discussions.captchaUrl = "{{ site.sitePath }}/captcha.jpg?t=";
</script>
{% endif %}
                    </div>
					
                </td>
                <td style="width: 4px;"></td>
                <td valign="top" style="width: 164px;">

</div>
    <div class="habblet ">
		    </div>
                </td>
            </tr>
        </table>
    </div>
  </div>

<script type="text/javascript">
	Event.observe(window, "load", observeAnim);
	document.observe("dom:loaded", function() {
		initDraggableDialogs();
	});
</script>

    </div>
{% include "../base/footer.tpl" %}
</div>

</div>


<div class="cbb topdialog" id="guestbook-form-dialog">
	<h2 class="title dialog-handle">{{ locale.groups_view_discussions_edit_guestbook_entry }}</h2>
	
	<a class="topdialog-exit" href="#" id="guestbook-form-dialog-exit">X</a>
	<div class="topdialog-body" id="guestbook-form-dialog-body">

<div id="guestbook-form-tab">
<form method="post" id="guestbook-form">
    <p>
        {{ locale.groups_view_discussions_note_the_message_length_must_not_exceed_two_zero_zero_characters }}        <input type="hidden" name="ownerId" value="1" />
	</p>
	<div>
	    <textarea cols="15" rows="5" name="message" id="guestbook-message"></textarea>
    <script type="text/javascript">
        bbcodeToolbar = new Control.TextArea.ToolBar.BBCode("guestbook-message");
        bbcodeToolbar.toolbar.toolbar.id = "bbcode_toolbar";
		        var colors = { "red" : ["#d80000", "Red"],
            "orange" : ["#fe6301", "Orange"],
            "yellow" : ["#ffce00", "Yellow"],
            "green" : ["#6cc800", "Green"],
            "cyan" : ["#00c6c4", "Cyan"],
            "blue" : ["#0070d7", "Blue"],
            "gray" : ["#828282", "Gray"],
            "black" : ["#000000", "Black"]
        };
        bbcodeToolbar.addColorSelect("Color", colors, true);
    </script>

<div id="linktool">
    <div id="linktool-scope">
        <label for="linktool-query-input">{{ locale.groups_view_discussions_create_link }}</label>
        <input type="radio" name="scope" class="linktool-scope" value="1" checked="checked"/>{{ locale.groups_view_discussions_habbos }}        <input type="radio" name="scope" class="linktool-scope" value="2"/>{{ locale.groups_view_discussions_rooms }}        <input type="radio" name="scope" class="linktool-scope" value="3"/>{{ locale.groups_view_discussions_groups }}    </div>
    <input id="linktool-query" type="text" name="query" value=""/>
    <a href="#" class="new-button" id="linktool-find"><b>{{ locale.groups_view_discussions_find }}</b><i></i></a>
    <div class="clear" style="height: 0;"><!-- --></div>

    <div id="linktool-results" style="display: none">
    </div>
    <script type="text/javascript">
        linkTool = new LinkTool(bbcodeToolbar.textarea);
    </script>
</div>
    </div>

	<div class="guestbook-toolbar clearfix">
		<a href="#" class="new-button" id="guestbook-form-cancel"><b>{{ locale.groups_view_discussions_cancel }}</b><i></i></a>
		<a href="#" class="new-button" id="guestbook-form-preview"><b>{{ locale.groups_view_discussions_preview }}</b><i></i></a>	
	</div>

</form>
</div>
<div id="guestbook-preview-tab">&nbsp;</div>
	</div>
</div>	
<div class="cbb topdialog" id="guestbook-delete-dialog">
	<h2 class="title dialog-handle">{{ locale.groups_view_discussions_delete_entry }}</h2>
	
	<a class="topdialog-exit" href="#" id="guestbook-delete-dialog-exit">X</a>
	<div class="topdialog-body" id="guestbook-delete-dialog-body">
<form method="post" id="guestbook-delete-form">
	<input type="hidden" name="entryId" id="guestbook-delete-id" value="" />

	<p>{{ locale.groups_view_discussions_are_you_sure_you_want_to_delete_this_entry }}</p>
	<p>
		<a href="#" id="guestbook-delete-cancel" class="new-button"><b>{{ locale.groups_view_discussions_cancel }}</b><i></i></a>
		<a href="#" id="guestbook-delete" class="new-button"><b>{{ locale.groups_view_discussions_delete }}</b><i></i></a>
	</p>
</form>
	</div>
</div>
<div id="group-tools" class="bottom-bubble">
	<div class="bottom-bubble-t"><div></div></div>
	<div class="bottom-bubble-c">
<h3>{{ locale.groups_view_discussions_edit_group }}</h3>

<ul>
	{% if (hasMember and groupMember.getMemberRank().getRankId() >= 2) %}
	<li><a href="{{ site.sitePath }}/groups/actions/startEditingSession/{{ group.id }}" id="group-tools-style">{{ locale.groups_view_discussions_modify_page }}</a></li>
	{% endif %}
	{% if (hasMember and groupMember.getMemberRank().getRankId() == 3) %}
	<li><a href="#" id="group-tools-settings">{{ locale.groups_view_discussions_settings }}</a></li>	<li><a href="#" id="group-tools-badge">{{ locale.groups_view_discussions_badge }}</a></li>
	{% endif %}
	{% if (group.getGroupType() != 3) and (hasMember and groupMember.getMemberRank().getRankId() >= 2) %}
	<li><a href="#" id="group-tools-members">{{ locale.groups_view_discussions_members }}</a></li>
	{% endif %}
</ul>

	</div>
	<div class="bottom-bubble-b"><div></div></div>
</div>

<div class="cbb topdialog black" id="dialog-group-settings">
	
	<div class="box-tabs-container">
<ul class="box-tabs">
	<li class="selected" id="group-settings-link-group"><a href="#">{{ locale.groups_view_discussions_group_settings }}</a><span class="tab-spacer"></span></li>
	<li id="group-settings-link-forum"><a href="#">{{ locale.groups_view_discussions_forum_settings }}</a><span class="tab-spacer"></span></li>
	<li id="group-settings-link-room"><a href="#">{{ locale.groups_view_discussions_room_settings }}</a><span class="tab-spacer"></span></li>
</ul>
</div>

	<a class="topdialog-exit" href="#" id="dialog-group-settings-exit">X</a>
	<div class="topdialog-body" id="dialog-group-settings-body">
<p style="text-align:center"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></p>
	</div>
</div>	

<script language="JavaScript" type="text/javascript">
Event.observe("dialog-group-settings-exit", "click", function(e) {
    Event.stop(e);
    closeGroupSettings();
}, false);
</script><div class="cbb topdialog black" id="group-memberlist">
	
	<div class="box-tabs-container">
<ul class="box-tabs">
	<li class="selected" id="group-memberlist-link-members"><a href="#">{{ locale.groups_view_discussions_members }}</a><span class="tab-spacer"></span></li>
	<li id="group-memberlist-link-pending"><a href="#">{{ locale.groups_view_discussions_pending_members }}</a><span class="tab-spacer"></span></li>
</ul>
</div>

	<a class="topdialog-exit" href="#" id="group-memberlist-exit">X</a>
	<div class="topdialog-body" id="group-memberlist-body">
<div id="group-memberlist-members-search" class="clearfix">
    
    <a id="group-memberlist-members-search-button" href="#" class="new-button"><b>{{ locale.groups_view_discussions_search }}</b><i></i></a>
    <input type="text" id="group-memberlist-members-search-string"/>
</div>
<div id="group-memberlist-members" style="clear: both"></div>

<div id="group-memberlist-members-buttons" class="clearfix">
	{% if (hasMember and groupMember.getMemberRank().getRankId() == 3) %}
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-give-rights"><b>{{ locale.groups_view_discussions_give_rights }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-revoke-rights"><b>{{ locale.groups_view_discussions_revoke_rights }}</b><i></i></a>{% endif %}
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-remove"><b>{{ locale.groups_view_discussions_remove }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button" id="group-memberlist-button-close"><b>{{ locale.groups_view_discussions_close }}</b><i></i></a>
</div> 
<div id="group-memberlist-pending" style="clear: both"></div>
<div id="group-memberlist-pending-buttons" class="clearfix">
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-accept"><b>{{ locale.groups_view_discussions_accept }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-decline"><b>{{ locale.groups_view_discussions_reject }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button" id="group-memberlist-button-close2"><b>{{ locale.groups_view_discussions_close }}</b><i></i></a>
</div>
	</div>
</div>

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>
