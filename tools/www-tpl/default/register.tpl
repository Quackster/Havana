
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.register_register }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.register_rss }}" href="{{ site.sitePath }}/rss" />
	
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/process.css" type="text/css" />

<script type="text/javascript">
document.habboLoggedIn = false;
var habboName = null;
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }


</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/registration.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/registration.js" type="text/javascript"></script>
    <script type="text/javascript">		
        L10N.put("register.tooltip.name", "{{ locale.register_your_name_can_contain_lowercase_and_uppercase_letters_numbers_and_the_characters|escape('js') }}");
        L10N.put("register.tooltip.password", "{{ locale.register_your_password_must_have_at_least_six_characters_and_it_must_contain_both_letters_and_numbers|escape('js') }}");
        L10N.put("register.error.password_required", "{{ locale.register_please_enter_a_password|escape('js') }}");
        L10N.put("register.error.password_too_short", "{{ locale.register_your_password_should_be_at_least_six_characters_long|escape('js') }}");
        L10N.put("register.error.password_numbers", "{{ locale.register_you_need_to_have_at_least_one_number_or_special_character_in_your_password|escape('js') }}");
        L10N.put("register.error.password_letters", "{{ locale.register_you_need_to_have_at_least_one_lowercase_or_uppercase_letter_in_your_password|escape('js') }}");
        L10N.put("register.error.retyped_password_required", "{{ locale.register_please_re_enter_your_password|escape('js') }}");
        L10N.put("register.error.retyped_password_notsame", "{{ locale.register_your_passwords_do_not_match_please_try_again|escape('js') }}");
        L10N.put("register.error.retyped_email_required", "{{ locale.register_please_type_your_email_again|escape('js') }}");
        L10N.put("register.error.retyped_email_notsame", "{{ locale.register_emails_don_t_match|escape('js') }}");
        L10N.put("register.tooltip.namecheck", "{{ locale.register_click_here_to_check_your_name_is_free|escape('js') }}");
        L10N.put("register.tooltip.retypepassword", "{{ locale.register_please_re_enter_your_password_text|escape('js') }}");
        L10N.put("register.tooltip.personalinfo.disabled", "{{ locale.register_please_choose_your|escape('js') }} {{ site.siteName }} {{ locale.register_character_name_first|escape('js') }}");
        L10N.put("register.tooltip.namechecksuccess", "{{ locale.register_congratulations_the_name_is_available|escape('js') }}");
        L10N.put("register.tooltip.passwordsuccess", "{{ locale.register_your_password_is_now_secure|escape('js') }}");
        L10N.put("register.tooltip.passwordtooshort", "{{ locale.register_the_password_you_have_chosen_is_too_short|escape('js') }}");
        L10N.put("register.tooltip.passwordnotsame", "{{ locale.register_password_not_the_same_please_re_type_it|escape('js') }}");
        L10N.put("register.tooltip.invalidpassword", "{{ locale.register_the_password_you_have_chosen_is_invalid_please_choose_a_new_password|escape('js') }}");
        L10N.put("register.tooltip.email", "{{ locale.register_please_enter_your_email_address_you_need_to_activate_your_account_using_this_address_so_please_use_your_real_address|escape('js') }}");
        L10N.put("register.tooltip.retypeemail", "{{ locale.register_please_re_enter_your_email_address|escape('js') }}");
        L10N.put("register.tooltip.invalidemail", "{{ locale.register_please_enter_a_valid_email_address|escape('js') }}");
        L10N.put("register.tooltip.emailsuccess", "{{ locale.register_you_have_provided_a_valid_email_address_thanks|escape('js') }}");
        L10N.put("register.tooltip.emailnotsame", "{{ locale.register_your_retyped_email_doesn_t_match|escape('js') }}");
        L10N.put("register.tooltip.enterpassword", "{{ locale.register_please_enter_a_password_text|escape('js') }}");
        L10N.put("register.tooltip.entername", "{{ locale.register_please_enter_a_name_for_your|escape('js') }} {{ site.siteName }} {{ locale.register_character|escape('js') }}");
        L10N.put("register.tooltip.enteremail", "{{ locale.register_please_enter_your_email_address|escape('js') }}");
        L10N.put("register.tooltip.enterbirthday", "{{ locale.register_please_give_your_date_of_birth_you_need_this_later_to_get_password_reminders_etc|escape('js') }}");
        L10N.put("register.tooltip.acceptterms", "{{ locale.register_please_accept_the_terms_and_conditions|escape('js') }}");
        L10N.put("register.tooltip.invalidbirthday", "{{ locale.register_please_supply_a_valid_birthdate|escape('js') }}");
        L10N.put("register.tooltip.emailandparentemailsame","{{ locale.register_you_parent_s_email_and_your_email_cannot_be_the_same_please_provide_a_different_one|escape('js') }}");
        L10N.put("register.tooltip.entercaptcha","{{ locale.register_enter_the_code|escape('js') }}");
        L10N.put("register.tooltip.captchavalid","{{ locale.register_invalid_code|escape('js') }}");
        L10N.put("register.tooltip.captchainvalid","{{ locale.register_invalid_code_please_try_again|escape('js') }}");
		L10N.put("register.error.parent_permission","{{ locale.register_you_need_to_tell_your_parents_about_this_service|escape('js') }}");

        RegistrationForm.parentEmailAgeLimit = -1;
        L10N.put("register.message.parent_email_js_form", "{{ locale.register_div_n_t_div_class_register_label_because_you_are_under_one_six_and_in_accordance_with_industry_best_practice_guidelines_we_require_your_parent_or_guardian_s_email_address_div_n_t_div_id_parentemail_error_box_n_div_class_register_error_n_div_class_rounded_rounded_blue_id_parentemail_error_box_container_n_div_id_parentemail_error_box_content_n_please_enter_your_email_address_n_div_n_div_n_div_n_t_div_n_t_div_class_register_label_label_for_register_parentemail_bubble_parent_or_guardian_s_email_address_label_div_n_t_div_class_register_label_input_type_text_name_bean_parentemail_id_register_parentemail_bubble_class_register_text_black_size_one_five_div_n_n_n_div|escape('js') }}");

        RegistrationForm.isCaptchaEnabled = true;
         L10N.put("register.message.captcha_js_form", "{{ locale.register_div_n_div_id_recaptcha_image_class_register_label_n_img_id_captcha_src|escape('js') }}{{ site.sitePath }}{{ locale.register_captcha_jpg_t_one_five_three_eight_nine_zero_seven_five_five_seven_register_one_alt_width_two_zero_zero_height_six_zero_n_div_n_div_class_register_label_id_captcha_reload_n_img_src|escape('js') }}{{ site.staticContentPath }}{{ locale.register_web_gallery_v_two_images_shared_icons_reload_icon_gif_width_one_five_height_one_five_n_a_href_i_can_t_read_the_code_please_give_me_another_one_a_n_div_n_div_class_register_label_label_for_register_captcha_bubble_type_in_the_security_code_shown_in_the_image_above_label_div_n_div_class_register_input_input_type_text_name_bean_captcharesponse_id_register_captcha_bubble_class_register_text_black_value_size_one_five_div_n_div|escape('js') }}");

        L10N.put("register.message.age_limit_ban", "{{ locale.register_div_n_p_nsorry_but_you_cannot_register_because_you_are_too_young_if_you_entered_an_incorrect_date_of_birth_by_accident_please_try_again_in_a_few_hours_n_p_n_n_p_style_text_align_left_n_input_type_button_class_submit_id_register_parentemail_cancel_value_cancel_onclick_registrationform_cancel_agelimit_true_n_p_n_div|escape('js') }}");
        RegistrationForm.ageLimit = -1;
        RegistrationForm.banHours = 24;
        HabboView.add(function() {
            Rounder.addCorners($("register-avatar-editor-title"), 4, 4, "rounded-container");
			{% if registerCaptchaInvalid == false %}
            RegistrationForm.init(true);
			{% else %}
			RegistrationForm.init(false);
			{% endif %}
                    });

        HabboView.add(function() {
            var swfobj = new SWFObject("{{ site.sitePath }}/flash/HabboRegistration.swf", "habboreg", "435", "400", "8");
            swfobj.addParam("base", "{{ site.sitePath }}/flash/");
            swfobj.addParam("wmode", "opaque");
            swfobj.addParam("AllowScriptAccess", "always");
            swfobj.addVariable("figuredata_url", "{{ site.sitePath }}/xml/figuredata.xml");
            swfobj.addVariable("draworder_url", "{{ site.sitePath }}/xml/draworder.xml");
            swfobj.addVariable("localization_url", "{{ site.sitePath }}/xml/figure_editor.xml");
            swfobj.addVariable("habbos_url", "{{ site.sitePath }}/xml/promo_habbos_v2.xml");
            swfobj.addVariable("figure", "{{ registerFigure }}");
            swfobj.addVariable("gender", "{{ registerGender }}");

            swfobj.addVariable("showClubSelections", "0");

            swfobj.write("register-avatar-editor");
            window.habboreg = $("habboreg"); // for MSIE and Flash Player 8
        });

    </script>


<meta name="description" content="{{ locale.register_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.register_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.register_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.register_havanaweb }}" />
</head>
<body id="register" class="process-template secure-page">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">
		
		<!-- 
			<p>{{ locale.register_useragent }} <span id="useragent"></span></p>
			<p>{{ locale.register_screensize }} <span id="screensize"></span></p>
			<p>{{ locale.register_ismobile }} <span id="ismobile"></span></p>
			
			   <script>
      const userAgentSpan = document.getElementById('useragent');
      const screenSizeSpan = document.getElementById('screensize');
      const isMobileSpan = document.getElementById('ismobile');
      
      const userAgent = navigator.userAgent;
      const screenWidth = screen.width;
      const screenHeight = screen.height;
      
      const tempMobile = false; //initiate as false
// device detection
if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
    || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) { 
    tempMobile = true;
}
		
		if (!tempMobile) {
			let tempMobile = false;
			
			try {
				tempMobile = window.matchMedia("{{ locale.register_only_screen_and_max_width_seven_six_zero_px|escape('js') }}").matches;
			} catch (e) {
			
			}
		}
      
      userAgentSpan.innerText = userAgent;
      screenSizeSpan.innerText = screenWidth + ' x ' + screenHeight;
      isMobileSpan.innerText = tempMobile? 'true':'false';
    </script>
	-->
			{% include "base/frontpage_header.tpl" %}
			<div id="process-content">	        	<div id="column1" class="column">
			     
			{% if ("registerCaptchaInvalid" is not present) and ("registerEmailInvalid" is not present) %}
							<div class="habblet-container ">		
	    <form method="post" action="{{ site.sitePath }}/register" id="registerform" autocomplete="off">
			{% if referral > 0 %}<input type="hidden" name="referral" id="register-referrer" value="{{ referral }}" />{% endif %}
			<input type="hidden" name="bean.figure" id="register-figure" value="" />
			<input type="hidden" name="bean.gender" id="register-gender" value="" />
			<input type="hidden" name="bean.editorState" id="register-editor-state" value="" />
	        <div id="register-column-right" >
            <div id="register-section-2">
                <div class="rounded rounded-blue">
                    <h2 class="heading"><span class="numbering white">2.</span>{{ locale.register_choose_your_name }}</h2>

                    <fieldset id="register-fieldset-name">
	                    <div class="register-label white">{{ site.siteName }} {{ locale.register_name }}</div>
		                <input type="text" name="bean.avatarName" id="register-name" class="register-text" value="" size="25" />
		                <span id="register-name-check-container" style="display:none">
		                    <a class="new-button search-icon" href="#" id="register-name-check"><b><span></span></b><i></i></a>		                
		                </span>
                    </fieldset>
                    <div id="name-error-box">
				                    </div>

                </div>
            </div>
            <div id="register-section-3">
                <div id="registration-overlay"></div>
	            <div class="cbb clearfix gray">
    	            <h2 class="title heading"><span class="numbering white">3.</span>{{ locale.register_your_details }}	</h2>
    		        <div class="box-content">

			
                        <fieldset id="register-fieldset-password">
	                        <div class="register-label"><label for="register-password">{{ locale.register_my_password_will_be }}</label></div>
	                        <div class="register-label"><input type="password" name="password" id="register-password" class="register-text" size="25" value="" /></div>
	                        <div class="register-label"><label for="register-password2">{{ locale.register_confirm_password }}</label></div>
	                        <div class="register-label"><input type="password" name="retypedPassword" id="register-password2" class="register-text" size="25" value="" /></div>
                        </fieldset>
                        <div id="password-error-box"></div>

				

                        <fieldset>
	                        <div class="register-label"><label>{{ locale.register_i_was_born_on }}</label></div>
								                        <div id="register-birthday"><select name="bean.day" id="bean_day" class="dateselector"><option value="">{{ locale.register_day }}</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option></select> <select name="bean.month" id="bean_month" class="dateselector"><option value="">{{ locale.register_month }}</option><option value="1">{{ locale.register_january }}</option><option value="2">{{ locale.register_february }}</option><option value="3">{{ locale.register_march }}</option><option value="4">{{ locale.register_april }}</option><option value="5">{{ locale.register_may }}</option><option value="6">{{ locale.register_june }}</option><option value="7">{{ locale.register_july }}</option><option value="8">{{ locale.register_august }}</option><option value="9">{{ locale.register_september }}</option><option value="10">{{ locale.register_october }}</option><option value="11">{{ locale.register_november }}</option><option value="12">{{ locale.register_december }}</option></select> <select name="bean.year" id="bean_year" class="dateselector"><option value="">{{ locale.register_year }}</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option><option value="1904">1904</option><option value="1903">1903</option><option value="1902">1902</option><option value="1901">1901</option><option value="1900">1900</option></select> </div>
                        </fieldset>

                        <div id="email-error-box">
				                        </div>


                        <fieldset>
	                        <div class="register-label"><label for="register-email">{{ locale.register_and_my_email_address_is }}</label></div>
	                        <div class="register-label"><input type="text" name="bean.email" id="register-email" class="register-text" value="" size="25" maxlength="48" /></div>
	                        <div class="register-label"><label for="register-email2">{{ locale.register_retype_your_email_address }}</label></div>
	                        <div class="register-label"><input type="text" name="bean.retypedEmail" id="register-email2" class="register-text" value="" size="25" maxlength="48" /></div>
                        </fieldset>

	                    <div id="register-marketing-box">
		                    <input type="checkbox" name="bean.marketing" id="bean_marketing" value="true" checked="checked" />
		                    <label for="bean_marketing">{{ locale.register_yes_please_send_me }} {{ site.siteName }} {{ locale.register_updates_including_the_newsletter }}</label>
	                    </div>                  


                        <fieldset id="register-fieldset-captcha">
							<noscript>
	                            <div class="register-label"><img src="{{ site.sitePath }}/captcha.jpg" /></div>
	                            <div class="register-label"><label for="register-captcha">{{ locale.register_type_in_the_security_code_shown_in_the_image_above }}</label></div>
	                            <div id="captcha_response"><input type="text" name="bean.captchaResponse" id="recaptcha_response_field" class="register-text" value="" size="25" /></div>
							</noscript>
						</fieldset>

                        <div id="terms-error-box">
				                        </div>
                        <fieldset id="register-fieldset-terms">
                            <div class="rounded rounded-darkgray" id="register-terms">
	                            <div id="register-terms-content">
	                                <p><a href="{{ site.sitePath }}/papers/disclaimer" target="_blank" id="register-terms-link">{{ locale.register_terms_of_service }}</a></p>
                                    <p class="last">
                                        <input type="checkbox" name="bean.termsOfServiceSelection" id="register-terms-check" value="true" />
                                        <label for="register-terms-check">{{ locale.register_by_clicking_on_continue_i_confirm_that_i_have_read_and_accept_the_terms_of_use_and_privacy_policy }}</label>
                                    </p>
                                </div>
                            </div>
                        </fieldset>
		            </div>
	            </div>
	            <div id="form-validation-error-box" style="display:none">
                    <div class="register-error">
                        <div class="rounded rounded-red">
                            {{ locale.register_sorry_registration_failed_please_check_the_information_you_gave_in_the_red_boxes }}                        </div>
                    </div>
	            </div>
	        </div>
        </div>
		{% else %}
		
						<div class="habblet-container ">		
	    <form method="post" action="{{ site.sitePath }}/register" id="registerform" autocomplete="off">
	{% if referral > 0 %}<input type="hidden" name="referral" id="register-referrer" value="{{ referral }}" />{% endif %}
	<input type="hidden" name="bean.figure" id="register-figure" value="{{ registerFigure }}" />
	<input type="hidden" name="bean.gender" id="register-gender" value="{{ registerGender }}" />
	<input type="hidden" name="bean.editorState" id="register-editor-state" value="" />
	        <div id="register-column-right" >
            <div id="register-section-2">
                <div class="rounded rounded-blue">
                    <h2 class="heading"><span class="numbering white">2.</span>{{ locale.register_choose_your_name }}</h2>

                    <fieldset id="register-fieldset-name">

	                    <div class="register-label white">{{ site.siteName }} {{ locale.register_name }}</div>
	                    <div class="register-input">{{ registerUsername }}</div>
                    </fieldset>

                </div>
            </div>

		
            <div id="register-section-3">
				<div id="registration-overlay"></div>
	            <div class="cbb clearfix gray">
    	            <h2 class="title heading"><span class="numbering white">3.</span>{{ locale.register_your_details }}</h2>
    		        <div class="box-content">


                        <fieldset id="register-fieldset-password">
	                        <div class="register-label"><label for="register-password">{{ locale.register_my_password_will_be }}</label></div>
	                        <div class="register-input">{{ registerShowPassword }}</div>

                        </fieldset>

                        <fieldset>
	                        <div class="register-label"><label>{{ locale.register_i_was_born_on }}</label></div>
	                        <div class="register-input">{{ registerDay }}/{{ registerMonth }}/{{ registerYear }}</div>
	                    </fieldset>

                        <div id="email-error-box">
                        </div>

                        <fieldset>
	                        <div class="register-label"><label for="register-email">{{ locale.register_and_my_email_address_is }}</label></div>
								<div class="register-input">{{ registerEmail }}</div>
								{% if (registerEmailInvalid) %}
	                            <div id="email-error-box"><div class="register-error"><div class="rounded rounded-red">{{ locale.register_the_email_entered_is_already_used_by_someone_else }}</div></div></div>
								<div class="register-label"><label for="register-email">{{ locale.register_new_email_address }}</label></div>
								<div class="register-label"><input type="text" name="bean.email" id="register-email" class="register-text error" value="" maxlength="48" /></div>
								{% endif %}
	                    </fieldset>

	                    <div id="register-marketing-box">
		                    <input type="checkbox" name="bean.marketing" id="bean_marketing" value="true" checked="checked" />
		                    <label for="bean_marketing">{{ locale.register_yes_please_send_me }} {{ site.siteName }} {{ locale.register_updates_including_the_newsletter }}</label>

	                    </div>


                        <fieldset id="register-fieldset-captcha">

                                <div class="register-label"><img id="captcha" src="{{ site.sitePath }}/captcha.jpg?t={{ randomNum }}&register=1" alt="" width="200" height="60" /></div>
                                <div class="register-label" id="captcha-reload">
                                    <img src="{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif" width="15" height="15"/>
                                    <a href="#">{{ locale.register_i_can_t_read_the_code_please_give_me_another_one }}</a>
                                </div>
								{% if (registerCaptchaInvalid) %}
	                            <div id="captcha-error-box"><div class="register-error"><div class="rounded rounded-red">{{ locale.register_the_code_that_you_filled_in_isn_t_right_please_try_again }}</div></div></div>
								{% endif %}
	                            <div class="register-label"><label for="register-captcha">{{ locale.register_type_in_the_security_code_shown_in_the_image_above }}</label></div>
	                            <div id="captcha_response"><input type="text" name="bean.captchaResponse" id="recaptcha_response_field" class="register-text error" value="" size="25" /></div>
        <script type="text/javascript">
        document.observe("dom:loaded", function() {
            Event.observe($("captcha-reload"), "click", function(e) {Utils.reloadCaptcha()});
        });
        </script>
                        </fieldset>

                        <div id="terms-error-box">
                        </div>

                        <fieldset id="register-fieldset-terms">
                            <div class="rounded rounded-darkgray" id="register-terms">
	                            <div id="register-terms-content">
	                                <p><a href="{{ site.sitePath }}/papers/termsAndConditions" target="_blank" id="register-terms-link">{{ locale.register_terms_of_service }}</a></p>
                                    <p class="last">
                                        <input type="checkbox" name="bean.termsOfServiceSelection" id="register-terms-check" value="true"  checked="checked"/>
                                        <label for="register-terms-check">{{ locale.register_by_clicking_on_continue_i_confirm_that_i_have_read_and_accept_the_terms_of_use_and_privacy_policy }}</label>

                                    </p>
                                </div>
                            </div>
                        </fieldset>
		            </div>
	            </div>
	            <div id="form-validation-error-box" style="display:none">
                    <div class="register-error">
                        <div class="rounded rounded-red">

                            {{ locale.register_sorry_registration_failed_please_check_the_information_you_gave_in_the_red_boxes }}                        </div>
                    </div>
	            </div>
	        </div>


        </div>
		{% endif %}
        <div id="register-column-left">

            <div id="register-avatar-editor-title">

                <h2 class="heading"><span class="numbering white">1.</span>{{ locale.register_create_your }} {{ site.siteName }}</h2>
            </div>

            <div id="avatar-error-box">
            </div>
            <div id="register-avatar-editor">
                <p><b>{{ locale.register_you_don_t_have_flash_installed_this_is_why_we_can_only_show_you_a_selection_of_pre_generated }} {{ site.siteName }}{{ locale.register_s_if_you_install_flash_you_ll_be_able_to_choose_from_the_hundreds_of_different_options }}</b></p>
                <h3>{{ locale.register_girls }}</h3>
				                <div class="register-avatars clearfix">
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomFemaleFigure1 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="F-{{ randomFemaleFigure1 }}" checked />
	                </div>
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomFemaleFigure2 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="F-{{ randomFemaleFigure2 }}" />
	                </div>
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomFemaleFigure3 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="F-{{ randomFemaleFigure3 }}" />
	                </div>
                </div>
                <h3>{{ locale.register_boys }}</h3>
                <div class="register-avatars clearfix">
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomMaleFigure1 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="M-{{ randomMaleFigure1 }}" />
	                </div>
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomMaleFigure2 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="M-{{ randomMaleFigure2 }}" />
	                </div>
						                <div class="register-avatar" style="background-image: url({{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ randomMaleFigure3 }}&size=b&direction=4&head_direction=4&crr=0&gesture=sml&frame=1)">
	                    <input type="radio" name="randomFigure" value="M-{{ randomMaleFigure3 }}" />
	                </div>
	            </div>
                <p>{{ locale.register_if_you_dislike_the }} {{ site.siteName }} {{ locale.register_above_you_may_change_it_later_via_the_account_settings_page }}</p>
            </div>

            <div id="register-buttons">
                <input type="submit" value="{{ locale.register_continue }}" class="continue" id="register-button-continue" />
                <a href="{{ site.sitePath }}/register/cancel" class="cancel">{{ locale.register_exit_registration }}</a>
            </div>
	    </div>
    </form>
	
						
							
					
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

			 

</div>

<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>