<!--
{% if (session.loggedIn == false) or (playerDetails.hasFlashWarning) %}
		<div id="flash-check" style="display: none">
		<div class="rounded" style="background-color: red; color: white; padding:5px 10px 10px 10px">
			<strong>{{ locale.base_flash_check_attention }}</strong><br />
			{{ locale.base_flash_check_you_do_not_have_flash_installed_or_enabled_adobe_flash_and_shockwave_are_recommended_for_this_hotel_to_be_playable }}</br></br>
			{{ locale.base_flash_check_with_major_internet_browsers_dropping_flash_support_you_will_need_to_download_a_portable_browser_with_flash_pre_installed }} <a style="color:white" href="http://www.mediafire.com/file/o9tknqhdlo655yc/Basilisk-Portable.exe/file">{{ locale.base_flash_check_here }}</a> {{ locale.base_flash_check_for }} <a style="color:white" href="http://forum.ragezone.com/f353/portable-browser-flash-shockwave-basilisk-1192727/">{{ locale.base_flash_check_more_info }}</a> {{ locale.base_flash_check_including_screenshots }}</br>
			{{ locale.base_flash_check_you_may_download_the }} <a style="color:white" href="{{ site.sitePath }}/help/shockwave_app">{{ locale.base_flash_check_shockwave_portable_client_app }}</a> {{ locale.base_flash_check_to_play_the_server }}</br>
			</br>
			<b>{{ locale.base_flash_check_this_alert_will_not_appear_if_you_have_flash_enabled }}</b>
		</div>
		<br/>
		</div>
		
    <script> 
        function hasFlash() { 
            try { 
                return Boolean(new ActiveXObject('ShockwaveFlash.ShockwaveFlash')); 
            } catch (exception) { 
                return ('undefined' != typeof navigator.mimeTypes[ 
                    'application/x-shockwave-flash']); 
            } 
            return false; 
        }
		
		if (hasFlash()) {
			document.getElementById("flash-check").style.display = "none";
		} else {
			document.getElementById("flash-check").style.display = "block";
		}
    </script> 
{% endif %}
-->