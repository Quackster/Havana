{% if session.loggedIn %}

<div id="hc-habblet">
    <div id="hc-membership-info" class="box-content">
	{% if playerDetails.hasClubSubscription() %}
	<p>{{ locale.base_hc_status_you_have }} {{ hcDays }} {{ site.siteName }} {{ locale.base_hc_status_club_day_s_left }}</p>
	<p>{{ locale.base_hc_status_you_have_been_a_member_for }} {{ hcSinceMonths }} {{ locale.base_hc_status_month_s }}</p>
	{% else %}
	<p>{{ locale.base_hc_status_you_are_not_a_member_of }} {{ site.siteName }} {{ locale.base_hc_status_club }}</p>
	{% endif %}
    </div>
    <div id="hc-buy-container" class="box-content">
        <div id="hc-buy-buttons" class="hc-buy-buttons rounded rounded-hcred">
            <form class="subscribe-form" method="post">
                <input type="hidden" id="settings-figure" name="figureData" value="">
                <input type="hidden" id="settings-gender" name="newGender" value="">
                <table width="100%">
                    <tr>
                        <td>
		                    <a class="new-button fill" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">{{ locale.base_hc_status_buy_one_month_s }}</b><i></i></a>
                        </td>
                        <td width="45%">{{ locale.base_hc_status_purchase }} {{ clubChoiceDays1 }} {{ locale.base_hc_status_days }}<br/> {{ clubChoiceCredits1 }} {{ locale.base_hc_status_coins }}</td>
                    </tr>
                    <tr>

                        <td>
		                    <a class="new-button fill" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">{{ locale.base_hc_status_buy_three_month_s }}</b><i></i></a>
                        </td>
                        <td width="45%">{{ locale.base_hc_status_purchase }} {{ clubChoiceDays2 }} {{ locale.base_hc_status_days }}<br/> {{ clubChoiceCredits2 }} {{ locale.base_hc_status_coins }}</td>
                    </tr>
                    <tr>
                        <td>

		                    <a class="new-button fill" id="subscribe3" href="#" onclick='habboclub.buttonClick(3, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">{{ locale.base_hc_status_buy_six_month_s }}</b><i></i></a>
                        </td>
                        <td width="45%">{{ locale.base_hc_status_purchase }} {{ clubChoiceDays3 }} {{ locale.base_hc_status_days }}<br/> {{ clubChoiceCredits3 }} {{ locale.base_hc_status_coins }}</td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
{% else %}
						<div id="hc-habblet" class='box-content'>
{{ locale.base_hc_status_please_sign_in_to_see_your }} {{ site.siteName }} {{ locale.base_hc_status_club_status }}</div>

{% endif %}