
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_change_email_my_details }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_change_email_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.profile_change_email_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
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


<meta name="description" content="{{ locale.profile_change_email_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_change_email_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_change_email_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_change_email_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_change_email_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_change_email_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_change_email_account_settings }}    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_change_email_club }}</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div>
<div class="content">
<div class="habblet-container" style="float:left; width:210px;">
<div class="cbb settings">

<h2 class="title">{{ locale.profile_change_email_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">{{ locale.profile_change_email_my_clothing }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">{{ locale.profile_change_email_my_preferences }}</a>
                </li><li class="selected">{% if accountActivated %}{{ locale.profile_change_email_my_email }}{% else %}{{ locale.profile_change_email_email_changing_verification }}{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">{{ locale.profile_change_email_my_password }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">{{ locale.profile_change_email_friend_management }}</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_change_email_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">{{ locale.profile_change_email_change_your_email_settings }}</h2>
            <div class="box-content">

{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<!-- <span id="confirmform-texts">
<form action="{{ site.sitePath }}/profile/resendconfirmation" method="post" id="confirmform">
<input type="hidden" name="tab" value="3" />
<input type="hidden" name="__app_key" value="{{ locale.profile_change_email_phpretro }}" />
<input type="hidden" name="resendconfirmation" value="" />

<h3>{{ locale.profile_change_email_you_haven_t_confirmed_that_your_email_is_valid }}</h3>

<p class="last">
{{ locale.profile_change_email_how_to_activate_my_account_and_confirm_my_email_click_the_button_and_send_a_confirmation_email_to_your_email_account_click_the_link_from_the_email_message_and_you_are_done_click_the_button_below_if_you_want_us_to_send_you_another_account_activation_message }}</p>

<div class="settings-buttons">
<a href="#" class="new-button" style="display: none" id="confirmform-submit"><b>{{ locale.profile_change_email_activate_my_email_address }}</b><i></i></a>
<noscript><input type="submit" value="{{ locale.profile_change_email_activate_my_email_address }}" name="save" class="submit" /></noscript>

</div>

</form>

<br/><br/>
</span> -->

<form action="{{ site.sitePath }}/profile/emailupdate" method="post" id="emailform">
<input type="hidden" name="tab" value="3" />
<input type="hidden" name="__app_key" value="{{ locale.profile_change_email_havanaweb }}" />

<div class="settings-step">

	<h4>1.</h4>
	<div class="settings-step-content">

<h3>{{ locale.profile_change_email_give_your_current_details }}</h3>

<p>
 <label for="currentpassword">{{ locale.profile_change_email_current_password }}</label><br />
 <input type="password" size="32" maxlength="32" name="password" id="currentpassword" class="currentpassword " />
</p>

<!-- 
<div><label for="birthdate">{{ locale.profile_change_email_birthday }}</label></div>
<div id="required-birthday" ><select name="month" id="month" class="dateselector"><option value="">{{ locale.profile_change_email_month }}</option><option value="1">{{ locale.profile_change_email_january }}</option><option value="2">{{ locale.profile_change_email_february }}</option><option value="3">{{ locale.profile_change_email_march }}</option><option value="4">{{ locale.profile_change_email_april }}</option><option value="5">{{ locale.profile_change_email_may }}</option><option value="6">{{ locale.profile_change_email_june }}</option><option value="7">{{ locale.profile_change_email_july }}</option><option value="8">{{ locale.profile_change_email_august }}</option><option value="9">{{ locale.profile_change_email_september }}</option><option value="10">{{ locale.profile_change_email_october }}</option><option value="11">{{ locale.profile_change_email_november }}</option><option value="12">{{ locale.profile_change_email_december }}</option></select> <select name="day" id="day" class="dateselector"><option value="">{{ locale.profile_change_email_day }}</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option></select> <select name="year" id="year" class="dateselector"><option value="">{{ locale.profile_change_email_year }}</option><option value="2009">2009</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option><option value="1904">1904</option><option value="1903">1903</option><option value="1902">1902</option><option value="1901">1901</option><option value="1900">1900</option></select> </div>
-->

</div>

</div>
<div class="settings-step">

	<h4>2.</h4>
	<div class="settings-step-content">

<h3>{{ locale.profile_change_email_enter_your_new_email_address }}</h3>

<p>{{ locale.profile_change_email_make_sure_you_type_in_your_email_address_correctly_email_verification_link_is_sent_to_that_address }}</p>

<p>
<label for="email">{{ locale.profile_change_email_email_address }}</label><br />
<input type="text" name="email" id="email" size="32" maxlength="48" value="{{ playerDetails.email }}" />
</p>

<p>
 <input name="directemail" id="directemail"  type="checkbox"> <label for="directemail">{{ locale.profile_change_email_yes_i_want_the_latest_retro_news_sent_straight_to_my_inbox }}</label>
</p>

	</div>
</div>

<div class="settings-step">

	<h4>3.</h4>
	<div class="settings-step-content">

<h3>
<label for="bean_captcha" class="registration-text">{{ locale.profile_change_email_type_in_the_security_code_shown_in_the_image_below }}</label>
</h3>

<div id="captcha-code-error"></div>

<p></p>

<div class="register-label" id="captcha-reload">
    <p>
        <img src="{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif" width="15" height="15" alt=""/>
        <a id="captcha-reload-link" href="#">{{ locale.profile_change_email_i_can_t_read_the_code_please_give_me_another_one }}</a>
    </p>
</div>

<script type="text/javascript">
document.observe("dom:loaded", function() {
    Event.observe($("captcha-reload"), "click", function(e) {Utils.reloadCaptcha()});
});
</script>

<p id="captcha-container">
    <img id="captcha" src="{{ site.sitePath }}/captcha.jpg?t={{ randomNumber }}" alt="" width="200" height="50" />

</p>

<p>
<input type="text" name="captcha" id="captcha-code" value="" class="registration-text required-captcha" />
</p>
    </div>
</div>

<div class="settings-buttons">
<a href="#" class="new-button" style="display: none" id="emailform-submit"><b>{{ locale.profile_change_email_save_changes }}</b><i></i></a>
<noscript><input type="submit" value="{{ locale.profile_change_email_save_changes }}" name="save" class="submit" /></noscript>
</div>                        
</form>

<script type="text/javascript">
$("emailform-submit").observe("click", function(e) { e.stop(); $("emailform").submit(); });
$("emailform-submit").show();
</script>
<script type="text/javascript">
$("confirmform-texts").hide();
</script>                
</div></div></div></div>
</div>

<script type="text/javascript">
HabboView.run();

</script>


</body>
</html>