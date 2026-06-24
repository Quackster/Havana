{% include "base/email_header.tpl" %}
<p style="font-size: 18px;"><b>{{ locale.account_email_account_recovery }}</b></p>
<p>{{ locale.account_email_hello }} {{ playerName }},</a></p>
<p><b><a style="color: #66b3ff" href="{{ site.sitePath }}/account/password/recovery?id={{ playerId }}&code={{ recoveryCode }}">{{ locale.account_email_please_recover_your_account_by_clicking_here }}</a>.</b></p>
<p>{{ locale.account_email_this_link_can_only_be_used_once_and_will_lead_you_to_a_page_to_set_your_password_it_expires_after_one_day_and_nothing_will_happen_if_it_s_not_used }}</p>
<hr>
<p>{{ locale.account_email_if_you_did_not_request_an_account_recovery_you_can_delete_this_email }}</p>
{% include "base/email_footer.tpl" %}