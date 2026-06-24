
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.register_disabled_register }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.register_disabled_rss }}" href="{{ site.sitePath }}/rss" />
	
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
        L10N.put("register.tooltip.name", "{{ locale.register_disabled_your_name_can_contain_lowercase_and_uppercase_letters_numbers_and_the_characters|escape('js') }}");
        L10N.put("register.tooltip.password", "{{ locale.register_disabled_your_password_must_have_at_least_six_characters_and_it_must_contain_both_letters_and_numbers|escape('js') }}");
        L10N.put("register.error.password_required", "{{ locale.register_disabled_please_enter_a_password|escape('js') }}");
        L10N.put("register.error.password_too_short", "{{ locale.register_disabled_your_password_should_be_at_least_six_characters_long|escape('js') }}");
        L10N.put("register.error.password_numbers", "{{ locale.register_disabled_you_need_to_have_at_least_one_number_or_special_character_in_your_password|escape('js') }}");
        L10N.put("register.error.password_letters", "{{ locale.register_disabled_you_need_to_have_at_least_one_lowercase_or_uppercase_letter_in_your_password|escape('js') }}");
        L10N.put("register.error.retyped_password_required", "{{ locale.register_disabled_please_re_enter_your_password|escape('js') }}");
        L10N.put("register.error.retyped_password_notsame", "{{ locale.register_disabled_your_passwords_do_not_match_please_try_again|escape('js') }}");
        L10N.put("register.error.retyped_email_required", "{{ locale.register_disabled_please_type_your_email_again|escape('js') }}");
        L10N.put("register.error.retyped_email_notsame", "{{ locale.register_disabled_emails_don_t_match|escape('js') }}");
        L10N.put("register.tooltip.namecheck", "{{ locale.register_disabled_click_here_to_check_your_name_is_free|escape('js') }}");
        L10N.put("register.tooltip.retypepassword", "{{ locale.register_disabled_please_re_enter_your_password_text|escape('js') }}");
        L10N.put("register.tooltip.personalinfo.disabled", "{{ locale.register_disabled_please_choose_your|escape('js') }} {{ site.siteName }} {{ locale.register_disabled_character_name_first|escape('js') }}");
        L10N.put("register.tooltip.namechecksuccess", "{{ locale.register_disabled_congratulations_the_name_is_available|escape('js') }}");
        L10N.put("register.tooltip.passwordsuccess", "{{ locale.register_disabled_your_password_is_now_secure|escape('js') }}");
        L10N.put("register.tooltip.passwordtooshort", "{{ locale.register_disabled_the_password_you_have_chosen_is_too_short|escape('js') }}");
        L10N.put("register.tooltip.passwordnotsame", "{{ locale.register_disabled_password_not_the_same_please_re_type_it|escape('js') }}");
        L10N.put("register.tooltip.invalidpassword", "{{ locale.register_disabled_the_password_you_have_chosen_is_invalid_please_choose_a_new_password|escape('js') }}");
        L10N.put("register.tooltip.email", "{{ locale.register_disabled_please_enter_your_email_address_you_need_to_activate_your_account_using_this_address_so_please_use_your_real_address|escape('js') }}");
        L10N.put("register.tooltip.retypeemail", "{{ locale.register_disabled_please_re_enter_your_email_address|escape('js') }}");
        L10N.put("register.tooltip.invalidemail", "{{ locale.register_disabled_please_enter_a_valid_email_address|escape('js') }}");
        L10N.put("register.tooltip.emailsuccess", "{{ locale.register_disabled_you_have_provided_a_valid_email_address_thanks|escape('js') }}");
        L10N.put("register.tooltip.emailnotsame", "{{ locale.register_disabled_your_retyped_email_doesn_t_match|escape('js') }}");
        L10N.put("register.tooltip.enterpassword", "{{ locale.register_disabled_please_enter_a_password_text|escape('js') }}");
        L10N.put("register.tooltip.entername", "{{ locale.register_disabled_please_enter_a_name_for_your|escape('js') }} {{ site.siteName }} {{ locale.register_disabled_character|escape('js') }}");
        L10N.put("register.tooltip.enteremail", "{{ locale.register_disabled_please_enter_your_email_address|escape('js') }}");
        L10N.put("register.tooltip.enterbirthday", "{{ locale.register_disabled_please_give_your_date_of_birth_you_need_this_later_to_get_password_reminders_etc|escape('js') }}");
        L10N.put("register.tooltip.acceptterms", "{{ locale.register_disabled_please_accept_the_terms_and_conditions|escape('js') }}");
        L10N.put("register.tooltip.invalidbirthday", "{{ locale.register_disabled_please_supply_a_valid_birthdate|escape('js') }}");
        L10N.put("register.tooltip.emailandparentemailsame","{{ locale.register_disabled_you_parent_s_email_and_your_email_cannot_be_the_same_please_provide_a_different_one|escape('js') }}");
        L10N.put("register.tooltip.entercaptcha","{{ locale.register_disabled_enter_the_code|escape('js') }}");
        L10N.put("register.tooltip.captchavalid","{{ locale.register_disabled_invalid_code|escape('js') }}");
        L10N.put("register.tooltip.captchainvalid","{{ locale.register_disabled_invalid_code_please_try_again|escape('js') }}");
		L10N.put("register.error.parent_permission","{{ locale.register_disabled_you_need_to_tell_your_parents_about_this_service|escape('js') }}");

        RegistrationForm.parentEmailAgeLimit = -1;
        L10N.put("register.message.parent_email_js_form", "{{ locale.register_disabled_div_n_t_div_class_register_label_because_you_are_under_one_six_and_in_accordance_with_industry_best_practice_guidelines_we_require_your_parent_or_guardian_s_email_address_div_n_t_div_id_parentemail_error_box_n_div_class_register_error_n_div_class_rounded_rounded_blue_id_parentemail_error_box_container_n_div_id_parentemail_error_box_content_n_please_enter_your_email_address_n_div_n_div_n_div_n_t_div_n_t_div_class_register_label_label_for_register_parentemail_bubble_parent_or_guardian_s_email_address_label_div_n_t_div_class_register_label_input_type_text_name_bean_parentemail_id_register_parentemail_bubble_class_register_text_black_size_one_five_div_n_n_n_div|escape('js') }}");

        RegistrationForm.isCaptchaEnabled = true;
         L10N.put("register.message.captcha_js_form", "{{ locale.register_disabled_div_n_div_id_recaptcha_image_class_register_label_n_img_id_captcha_src|escape('js') }}{{ site.sitePath }}{{ locale.register_disabled_captcha_jpg_t_one_five_three_eight_nine_zero_seven_five_five_seven_register_one_alt_width_two_zero_zero_height_six_zero_n_div_n_div_class_register_label_id_captcha_reload_n_img_src|escape('js') }}{{ site.staticContentPath }}{{ locale.register_disabled_web_gallery_v_two_images_shared_icons_reload_icon_gif_width_one_five_height_one_five_n_a_href_i_can_t_read_the_code_please_give_me_another_one_a_n_div_n_div_class_register_label_label_for_register_captcha_bubble_type_in_the_security_code_shown_in_the_image_above_label_div_n_div_class_register_input_input_type_text_name_bean_captcharesponse_id_register_captcha_bubble_class_register_text_black_value_size_one_five_div_n_div|escape('js') }}");

        L10N.put("register.message.age_limit_ban", "{{ locale.register_disabled_div_n_p_nsorry_but_you_cannot_register_because_you_are_too_young_if_you_entered_an_incorrect_date_of_birth_by_accident_please_try_again_in_a_few_hours_n_p_n_n_p_style_text_align_left_n_input_type_button_class_submit_id_register_parentemail_cancel_value_cancel_onclick_registrationform_cancel_agelimit_true_n_p_n_div|escape('js') }}");
        RegistrationForm.ageLimit = -1;
        RegistrationForm.banHours = 24;
        HabboView.add(function() {
            Rounder.addCorners($("register-avatar-editor-title"), 4, 4, "rounded-container");
			{% if captchaInvalid == false %}
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


<meta name="description" content="{{ locale.register_disabled_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.register_disabled_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.register_disabled_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.register_disabled_havanaweb }}" />
</head>
<body id="register" class="process-template secure-page">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">

			{% include "base/frontpage_header.tpl" %}
			<div id="process-content">	        	
			<div id="column1" class="column">


			     		<p>{{ locale.register_disabled_registration_is_currently_disabled_please_come_back_another_time }}</p>			
		
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

		</div>	 
</div>
</div>
</div>
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