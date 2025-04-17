<ul>

    <li class="even icon-purse">
        <div>You Currently Have:</div>
        <span class="purse-balance-amount">{{ playerDetails.credits }} Monedas</span>
        <div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Account transactions</a></div>
    </li>

    <li class="odd">
        <div class="box-content">

            <div>Enter voucher code (without spaces):</div>
            <input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
            <a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>OK</b><i></i></a>
        </div>
    </li>
</ul>
<ul>
<div id="purse-redeem-result">
				{% if voucherResult == 'error' %}
        <div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                No se pudo encontrar su código de canalización.Inténtalo de nuevo.            
						</div> 
        </div>
				{% elseif voucherResult == 'too_new' %}
				<div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                Lo siento, su cuenta es demasiado nueva y no puede rediseem este cupón.            
						</div> 
        </div>
				{% else %}
				<div class="redeem-success"> 
            <div class="rounded rounded-green"> 
                Éxito de redención de cupones         
						</div> 
        </div>
				{% endif %}
</div>