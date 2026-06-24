<ul>

    <li class="even icon-purse">
        <div>{{ locale.habblet_redeemvoucher_you_currently_have }}</div>
        <span class="purse-balance-amount">{{ playerDetails.credits }} {{ locale.habblet_redeemvoucher_coins }}</span>
        <div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">{{ locale.habblet_redeemvoucher_account_transactions }}</a></div>
    </li>

    <li class="odd">
        <div class="box-content">

            <div>{{ locale.habblet_redeemvoucher_enter_voucher_code_without_spaces }}</div>
            <input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
            <a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>{{ locale.habblet_redeemvoucher_enter }}</b><i></i></a>
        </div>
    </li>
</ul>
<ul>
<div id="purse-redeem-result">
				{% if voucherResult == 'error' %}
        <div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                {{ locale.habblet_redeemvoucher_your_redeem_code_could_not_be_found_please_try_again }}            
						</div> 
        </div>
				{% elseif voucherResult == 'too_new' %}
				<div class="redeem-error"> 
            <div class="rounded rounded-red"> 
                {{ locale.habblet_redeemvoucher_sorry_your_account_is_too_new_and_cannot_redeeem_this_voucher }}            
						</div> 
        </div>
				{% else %}
				<div class="redeem-success"> 
            <div class="rounded rounded-green"> 
                {{ locale.habblet_redeemvoucher_voucher_redemption_success }}         
						</div> 
        </div>
				{% endif %}
</div>