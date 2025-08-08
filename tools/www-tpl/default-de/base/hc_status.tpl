{% if session.loggedIn %}

<div id="hc-habblet">
    <div id="hc-membership-info" class="box-content">
	{% if playerDetails.hasClubSubscription() %}
	<p>Du hast {{ hcDays }} {{ site.siteName }} Club Tage übrig.</p>
	<p>Du bist Mitglied seit {{ hcSinceMonths }} Monaten</p>
	{% else %}
	<p>Du bist kein Mitglied des {{ site.siteName }} Club</p>
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
		                    <a class="new-button fill" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Kaufe 1 Monat</b><i></i></a>
                        </td>
                        <td width="45%">Kaufe {{ clubChoiceDays1 }} Tage<br/>für {{ clubChoiceCredits1 }} Münzen</td>
                    </tr>
                    <tr>

                        <td>
		                    <a class="new-button fill" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Kaufe 3 Monate</b><i></i></a>
                        </td>
                        <td width="45%">Kaufe {{ clubChoiceDays2 }} Tage<br/>für {{ clubChoiceCredits2 }} Münzen</td>
                    </tr>
                    <tr>
                        <td>

		                    <a class="new-button fill" id="subscribe3" href="#" onclick='habboclub.buttonClick(3, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Kaufe 6 Monate</b><i></i></a>
                        </td>
                        <td width="45%">Kaufe {{ clubChoiceDays3 }} Tage<br/>für {{ clubChoiceCredits3 }} Münzen</td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
{% else %}
						<div id="hc-habblet" class='box-content'>
Melde dich an zum Anzeigen deines {{ site.siteName }} Club Status</div>

{% endif %}