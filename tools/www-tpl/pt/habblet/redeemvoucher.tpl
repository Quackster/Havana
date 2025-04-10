<ul>

    <li class="even icon-purse">
        <div>You Currently Have:</div>
        <span class="purse-balance-amount">{{ playerDetails.credits }} Coins</span>
        <div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Account transactions</a></div>
    </li>

    <li class="odd">
        <div class="box-content">

            <div>Digite seu voucher (sem espaços):</div>
            <input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
            <a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>Gerar</b><i></i></a>
        </div>
    </li>
</ul>
<ul>
<div id="purse-redeem-result">
				{% if voucherResult == 'error' %}
        <div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                Código não encontrado. tende novamente!           
						</div> 
        </div>
				{% elseif voucherResult == 'too_new' %}
				<div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                Desculpe, sua conta é muito nova para utilizar este código.           
						</div> 
        </div>
				{% else %}
				<div class="redeem-success"> 
            <div class="rounded rounded-green"> 
                Voucher utilizado com sucessso!        
						</div> 
        </div>
				{% endif %}
</div>