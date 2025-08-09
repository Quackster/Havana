<!--
{% if (session.loggedIn == false) or (playerDetails.hasFlashWarning) %}
		<div id="flash-check" style="display: none">
		<div class="rounded" style="background-color: red; color: white; padding:5px 10px 10px 10px">
			<strong>Achtung!</strong><br />
			Du hast Flash nicht installiert oder aktiviert. Für die Wiedergabe dieses Hotels werden Adobe Flash und Shockwave empfohlen..</br></br>
			Da die meisten Internetbrowser die Flash-Unterstützung eingestellt haben, musst du einen portablen Browser mit vorinstalliertem Flash herunterladen. <a style="color:white" href="http://www.mediafire.com/file/o9tknqhdlo655yc/Basilisk-Portable.exe/file">Hier</a> (for <a style="color:white" href="http://forum.ragezone.com/f353/portable-browser-flash-shockwave-basilisk-1192727/">Mehr Info</a> including screenshots).</br>
			Du solltest den <a style="color:white" href="{{ site.sitePath }}/help/shockwave_app">Basilisk Portable downloaden</a> um den Server spielen zu können..</br>
			</br>
			<b>Diese Meldung verschwindet, sobald alle benötigten Programme installiert sind</b>
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