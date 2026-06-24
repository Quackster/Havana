{% include "base/email_header.tpl" %}
<p style="font-size: 18px;"><b>{{ locale.account_email_email_activation_text }}</b></p>
<p>{{ locale.account_email_thanks_for_activating_your_email_at }} <a style="color: #66b3ff" href="{{ site.sitePath }}/?email">{{ site.emailHotelName }}</a>.</p>
<p><b><a style="color: #66b3ff" href="{{ site.sitePath }}/account/activate?id={{ playerId }}&code={{ activationCode }}">{{ locale.account_email_please_activate_your_account_by_clicking_here }}</a>.</b></p>
<p>{{ locale.account_email_here_are_your_user_details }}</p>
<p style="margin-left: 30px;"><b>{{ site.siteName}} {{ locale.account_email_name }}</b> {{ playerName }}</p>
<p style="margin-left: 30px;"><b>{{ locale.account_email_email }}</b> {{ playerEmail }}</p>
<p>{{ locale.account_email_keep_this_information_safe_you_need_your_username_and_email_to_reset_your_password_if_you_forget_it }}</br>
<hr>
<p>{{ locale.account_email_here_s_some_other_stuff_you_may_want_to_do }}</p>
</ul>
	<li style="padding-left: 30px;"><a style="color: #66b3ff" href="{{ site.sitePath }}/profile?tab=2">{{ locale.account_email_change_account_settings }}</a>.</li>
	<li style="padding-left: 30px;"><a style="color: #66b3ff" href="{{ site.sitePath }}/?emaildelete">{{ locale.account_email_completely_delete_this_email_address_from_your_user_profile }}</a>.</li>
</ul>
{% include "base/email_footer.tpl" %}