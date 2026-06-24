
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_change_password_my_details }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_change_password_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.profile_change_password_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
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


<meta name="description" content="{{ locale.profile_change_password_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_change_password_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_change_password_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_change_password_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_change_password_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_change_password_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_change_password_account_settings }}    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_change_password_club }}</a>
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

<h2 class="title">{{ locale.profile_change_password_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">{{ locale.profile_change_password_my_clothing }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">{{ locale.profile_change_password_my_preferences }}</a>
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">{{ locale.profile_change_password_my_email }}</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">{{ locale.profile_change_password_email_changing_verification }}</a>
				{% endif %}
                </li><li class="selected">{{ locale.profile_change_password_my_password }}
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">{{ locale.profile_change_password_friend_management }}</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_change_password_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">{{ locale.profile_change_password_change_your_password_settings }}</h2>
            <div class="box-content">

{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<p>{{ locale.profile_change_password_if_someone_told_you_to_come_here_and_change_your_password_in_return_for_a_reward_do_not_change_your_password }}</p>

<form action="{{ site.sitePath }}/profile/passwordupdate" method="post" id="pwform">

<input type="hidden" name="tab" value="4" />
<input type="hidden" name="__app_key" value="{{ locale.profile_change_password_havanaweb }}" />

<div class="settings-step">

	<h4>1.</h4>
	<div class="settings-step-content">

<h3>{{ locale.profile_change_password_give_your_current_details }}</h3>

<p>
 <label for="currentpassword">{{ locale.profile_change_password_current_password }}</label><br />
 <input type="password" size="32" name="currentpassword" id="currentpassword" class="currentpassword " />
</p>

<!-- <div>
<div><label for="birthdate">{{ locale.profile_change_password_birthday }}</label></div>
<div id="required-birthday" ><select name="month" id="month" class="dateselector"><option value="">{{ locale.profile_change_password_month }}</option><option value="1">{{ locale.profile_change_password_january }}</option><option value="2">{{ locale.profile_change_password_february }}</option><option value="3">{{ locale.profile_change_password_march }}</option><option value="4">{{ locale.profile_change_password_april }}</option><option value="5">{{ locale.profile_change_password_may }}</option><option value="6">{{ locale.profile_change_password_june }}</option><option value="7">{{ locale.profile_change_password_july }}</option><option value="8">{{ locale.profile_change_password_august }}</option><option value="9">{{ locale.profile_change_password_september }}</option><option value="10">{{ locale.profile_change_password_october }}</option><option value="11">{{ locale.profile_change_password_november }}</option><option value="12">{{ locale.profile_change_password_december }}</option></select> <select name="day" id="day" class="dateselector"><option value="">{{ locale.profile_change_password_day }}</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option></select> <select name="year" id="year" class="dateselector"><option value="">{{ locale.profile_change_password_year }}</option><option value="2009">2009</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option><option value="1904">1904</option><option value="1903">1903</option><option value="1902">1902</option><option value="1901">1901</option><option value="1900">1900</option></select> </div>

</div>-->

	</div>
</div>
<div class="settings-step">

	<h4>2.</h4>
	<div class="settings-step-content">

<h3>{{ locale.profile_change_password_enter_new_password }}</h3>

<p>{{ locale.profile_change_password_your_new_password_must_have_at_least_six_characters_your_password_can_include_lowercase_uppercase_letters_and_numbers }}</p>

<p>
 <label for="bean_password">{{ locale.profile_change_password_new_password }}</label><br /> 
 <input type="password" size="32" name="newpassword"
    value=""
    id="bean_password" class="required-password required-password2 " />
</p>

<p>
 <label for="bean_retypedPassword">{{ locale.profile_change_password_new_password_again }}</label><br />
 <input type="password" size="32" name="newpasswordconfirm"
 value="" 
 id="bean_retypedPassword" class="required-retypedPassword required-retypedPassword2 " />
</p>

	</div>

</div>

<div class="settings-step">

	<h4>3.</h4>
	<div class="settings-step-content">

<h3>
<label for="bean_captcha" class="registration-text">{{ locale.profile_change_password_type_in_the_security_code_shown_in_the_image_below }}</label>
</h3>

<div id="captcha-code-error"></div>

<p></p>

<div class="register-label" id="captcha-reload">
    <p>
        <img src="{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif" width="15" height="15" alt=""/>
        <a id="captcha-reload-link" href="#">{{ locale.profile_change_password_i_can_t_read_the_code_please_give_me_another_one }}</a>
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
<a href="#" class="new-button" style="display: none" id="pwform-submit"><b>{{ locale.profile_change_password_change_password }}</b><i></i></a>
<noscript><input type="submit" value="{{ locale.profile_change_password_change_password }}" name="save" class="submit" /></noscript>

</div>

</form>

<script type="text/javascript">
$("pwform-submit").observe("click", function(e) { e.stop(); $("pwform").submit(); });
$("pwform-submit").show();
</script>

</div></div></div></div>

</div>

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>