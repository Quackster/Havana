{% if session.loggedIn %}

<div id="hc-habblet">
    <div id="hc-membership-info" class="box-content">
	{% if playerDetails.hasClubSubscription() %}
	<p>Tienes {{ hcDays }} días de {{ site.siteName }} Club restantes.</p>
	<p>Has sido miembro de {{ site.siteName }} Club por {{ hcSinceMonths }} meses</p>
	{% else %}
	<p>No formas parte del {{ site.siteName }} Club</p>
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
		                    <a class="new-button fill" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Comprar 1 mes</b><i></i></a>
                        </td>
                        <td width="45%">Subscribirte por {{ clubChoiceDays1 }} días<br/> {{ clubChoiceCredits1 }} Créditos</td>
                    </tr>
                    <tr>

                        <td>
		                    <a class="new-button fill" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Comprar 3 meses</b><i></i></a>
                        </td>
                        <td width="45%">Subscribirte por {{ clubChoiceDays2 }} dias<br/> {{ clubChoiceCredits2 }} Créditos</td>
                    </tr>
                    <tr>
                        <td>

		                    <a class="new-button fill" id="subscribe3" href="#" onclick='habboclub.buttonClick(3, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">Comprar 6 meses</b><i></i></a>
                        </td>
                        <td width="45%">Subscribirte por {{ clubChoiceDays3 }} días<br/> {{ clubChoiceCredits3 }} Créditos</td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
{% else %}
						<div id="hc-habblet" class='box-content'>
Logeate para ver tu status del {{ site.siteName }} Club</div>

{% endif %}