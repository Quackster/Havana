{% if session.loggedIn %}

<div id="hc-habblet">
    <div id="hc-membership-info" class="box-content"><center>
	{% if playerDetails.hasClubSubscription() %}
	<p>Você tem {{ hcDays }} {{ site.siteName }} dia(s) restante(s).</p>
	<p>Você tem sido membro há {{ hcSinceMonths }} mês(es)</p>
	{% else %}
	<p>Você ainda não é membro do {{ site.siteName }} Club</p>
	{% endif %}
    </center></div>
    <div id="hc-buy-container" class="box-content">
        <div id="hc-buy-buttons" class="hc-buy-buttons rounded rounded-hcred">
            <form class="subscribe-form" method="post">
                <input type="hidden" id="settings-figure" name="figureData" value="">
                <input type="hidden" id="settings-gender" name="newGender" value="">
                <table width="100%">
                    <tr>
                        <td>
		                    <a class="new-button fill" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">1 mês</b><i></i></a>
                        </td>
                        <td width="45%">Comprar {{ clubChoiceDays1 }} dias<br/> {{ clubChoiceCredits1 }} Moedas</td>
                    </tr>
                    <tr>

                        <td>
		                    <a class="new-button fill" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">3 meses</b><i></i></a>
                        </td>
                        <td width="45%">Comprar {{ clubChoiceDays2 }} dias<br/> {{ clubChoiceCredits2 }} Moedas</td>
                    </tr>
                    <tr>
                        <td>

		                    <a class="new-button fill" id="subscribe3" href="#" onclick='habboclub.buttonClick(3, "HABBO CLUB"); return false;'><b style="padding-left: 3px; padding-right: 3px;">6 meses</b><i></i></a>
                        </td>
                        <td width="45%">Comprar {{ clubChoiceDays3 }} dias<br/> {{ clubChoiceCredits3 }} Moedas</td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
{% else %}
						<div id="hc-habblet" class='box-content'>
Por favor faça login para ver o status do seu {{ site.siteName }} Club</div>

{% endif %}