
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_friend_management_my_details }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_friend_management_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.profile_friend_management_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/settings.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/settings.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/friendmanagement.css" type="text/css" />


<meta name="description" content="{{ locale.profile_friend_management_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_friend_management_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_friend_management_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_friend_management_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_friend_management_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_friend_management_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_friend_management_account_settings }}    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_friend_management_club }}</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
<div class="content">
<div class="habblet-container" style="float:left; width:210px;">
<div class="cbb settings">

<h2 class="title">{{ locale.profile_friend_management_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">{{ locale.profile_friend_management_my_clothing }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">{{ locale.profile_friend_management_my_preferences }}</a>
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">{{ locale.profile_friend_management_my_email }}</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">{{ locale.profile_friend_management_email_changing_verification }}</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">{{ locale.profile_friend_management_my_password }}</a>
                </li><li class="selected">{{ locale.profile_friend_management_friend_management }}
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_friend_management_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>

<div id="friend-management" class="habblet-container">
{% autoescape 'html' %}
                <div class="cbb clearfix settings">
                    <h2 class="title">{{ locale.profile_friend_management_friend_management }}</h2>
                    <div id="friend-management-container" class="box-content">
<!-- <div class="rounded" style="background-color: orange; color: white">
<strong>{{ locale.profile_friend_management_attention }}</strong><br />
{{ locale.profile_friend_management_this_is_not_functional_as_of_this_moment }}<br />
</div>
<br /> -->
                        <div id="category-view" class="clearfix">
                            <div id="search-view">
                                {{ locale.profile_friend_management_search_for_a_friend_below }}
				                <div id="friend-search" class="friendlist-search">
					                <input type="text" maxlength="32" id="friend_query" class="friend-search-query" />
					                <a class="friendlist-search new-button search-icon" id="friend-search-button"><b><span></span></b><i></i></a>
					            </div>
                            </div>
							<div id="category-list">
								<div id="friends-category-title">
								{{ locale.profile_friend_management_friend_categories }}
								</div>
								<div class="category-default category-item selected-category" id="category-item-0">{{ locale.profile_friend_management_friends }}</div>
								{% for category in categories %}
								<div id="category-item-{{ category.getId() }}" class="category-item ">
										<div class="category-name" id="category-{{ category.getId() }}">
												<span class="open-category" id="category-name-{{ category.getId() }}">{{ category.getName() }}</span>
												<span id="category-field-{{ category.getId() }}" style="display:none"><input class="edit-category-name" maxlength="32" id="category-input-{{ category.getId() }}" type="text" value="{{ category.getName() }}"/></span>
										</div>
										<div id="category-button-delete-{{ category.getId() }}" class="friendmanagement-small-icons friendmanagement-remove delete-category-tip"></div>
										<div id="category-button-edit-{{ category.getId() }}" class="friendmanagement-small-icons edit-category"></div>

										<div id="category-button-cancel-{{ category.getId() }}" style="display:none" class="friendmanagement-small-icons friendmanagement-remove cancel-edit-category"></div>
										<div id="category-button-save-{{ category.getId() }}" style="display:none" class="friendmanagement-small-icons friendmanagement-save save-category"></div>
								</div>
								{% endfor %}
								<input type="text" maxlength="32" id="category-name" class="create-category" />
								<div id="add-category-button" class="friendmanagement-small-icons add-category-item add-category"></div>
							</div>
						</div>
                        <div id="friend-list" class="clearfix">
{% include "profile/profile_widgets/friend_view_category.tpl" %}
  </div>
</div>
</div>
{% endautoescape %}
</div>
  </div>
<script type="text/javascript">
				L10N.put("friendmanagement.tooltip.deletefriends", "{{ locale.profile_friend_management_p_are_you_sure_you_want_to_delete_these_selected_friends_p_div_class_friendmanagement_small_icons_friendmanagement_save_friendmanagement_tip_delete_n_a_class_friends_delete_button_id_delete_friends_button_delete_a_n_div_n_div_class_friendmanagement_small_icons_friendmanagement_remove_friendmanagement_tip_cancel_n_a_id_cancel_delete_friends_cancel_a_n_div_n_n|escape('js') }}");
        L10N.put("friendmanagement.tooltip.deletefriend", "{{ locale.profile_friend_management_p_are_you_sure_you_want_to_delete_this_friend_p_div_class_friendmanagement_small_icons_friendmanagement_save_friendmanagement_tip_delete_n_a_id_delete_friend_friend_id_delete_a_n_div_n_div_class_friendmanagement_small_icons_friendmanagement_remove_friendmanagement_tip_cancel_n_a_id_remove_friend_can_friend_id_cancel_a_n_div|escape('js') }}");
        L10N.put("friendmanagement.tooltip.deletecategory", "{{ locale.profile_friend_management_p_are_you_sure_you_want_to_delete_this_category_p_div_class_friendmanagement_small_icons_friendmanagement_save_friendmanagement_tip_delete_n_a_class_delete_category_button_id_delete_category_category_id_delete_a_n_div_n_div_class_friendmanagement_small_icons_friendmanagement_remove_friendmanagement_tip_cancel_n_a_id_cancel_cat_delete_category_id_cancel_a_n_div|escape('js') }}");
  new FriendManagement({ currentCategoryId: 0, pageListLimit: 30, pageNumber: 1});
</script>


<script type="text/javascript">
HabboView.run();

</script>


<div id="column3" class="column">
				<div class="habblet-container ">
						<div class="ad-container">
						{% include "../base/ads_container.tpl" %}
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

{% include "../base/footer.tpl" %}
	
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>