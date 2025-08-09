<ul>

    <li class="even icon-purse">
        <div>Du hast zurzeit:</div>
        <span class="purse-balance-amount">{{ playerDetails.credits }} Münzen</span>
        <div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Transaktionsverlauf</a></div>
    </li>

    <li class="odd">
        <div class="box-content">

            <div>Gib dein Code ohne Leerzeichen ein:</div>
            <input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
            <a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>Bestätigen</b><i></i></a>
        </div>
    </li>
</ul>
<ul>
<div id="purse-redeem-result">
				{% if voucherResult == 'error' %}
        <div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                Einlösecode konnte nicht gefunden werden. Bitte versuche es erneut..            
						</div> 
        </div>
				{% elseif voucherResult == 'too_new' %}
				<div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                Leider ist dein Konto zu neu und kann diesen Gutschein nicht einlösen..            
						</div> 
        </div>
				{% else %}
				<div class="redeem-success"> 
            <div class="rounded rounded-green"> 
                Einlösung erfolgreich         
						</div> 
        </div>
				{% endif %}
</div>