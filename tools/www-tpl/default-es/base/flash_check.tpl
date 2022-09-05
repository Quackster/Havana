<!--
{% if (session.loggedIn == false) or (playerDetails.hasFlashWarning) %}
		<div id="flash-check" style="display: none">
		<div class="rounded" style="background-color: red; color: white; padding:5px 10px 10px 10px">
			<strong>Oops! Error</strong><br />
			Parece ser que no tienes flash activado. Se recomienda activar Flash y Shockwave para jugar Habbo Hotel.</br></br>
			Ya que la mayoria de navegadores han dejado su soporte a ambos complementos te recomendamos usar el siguiente navegador: <a style="color:white" href="http://www.mediafire.com/file/o9tknqhdlo655yc/Basilisk-Portable.exe/file">Haciendo clic aquí</a> (Para <a style="color:white" href="http://forum.ragezone.com/f353/portable-browser-flash-shockwave-basilisk-1192727/">mas información clic aquí</a>).</br>
		<!--	You may download the <a style="color:white" href="{{ site.sitePath }}/help/shockwave_app">Shockwave portable client app</a> to play the server.</br> -->
			</br>
			<b>Esta alerta no aparecera si tienes flash activado.</b>
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