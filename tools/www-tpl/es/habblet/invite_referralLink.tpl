<h3>¡Disfruta más de {{ site.siteName }} con amigas de la vida real!</h3>

<div class="copytext">

    <p>Envía este enlace a tu amigo por correo electrónico o chat. Si usan {{ site.siteName }} de forma activa, serás recompensado con una insignia.</p>

    <textarea cols="50" rows="2" onclick="this.focus();this.select()" readonly="readonly" style="width:100%">{{ site.sitePath }}/register?referral={{ playerDetails.id }}</textarea>

</div>