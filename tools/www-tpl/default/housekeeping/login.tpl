{% include "housekeeping/base/header.tpl" %}
  <body class="hk-login-page">
    <div class="login-wrapper">
      <div class="login-content">
        <div class="login-container">
          <div class="login-box">
            <h1>Log in</h1>
            <form id="loginform" action="/{{ site.housekeepingPath }}/login" method="post">
              <label for="namefield">{{ locale.housekeeping_login_username }}:</label>
              <input type="text" id="namefield" name="hkusername" value="">

              <label for="passwordfield">{{ locale.housekeeping_login_password }}:</label>
              <input type="password" id="passwordfield" name="hkpassword" value="">

              <input type="submit" value="{{ locale.housekeeping_login_sign_in }}">
            </form>
          </div>

          <div class="welcome">
            {% include "housekeeping/base/alert.tpl" %}
            <h2>Welcome Hobbas</h2>
            <p>This service is meant for registered Hobbas. The service is monitored closely and unauthorised access is prohibited.</p>
            <p>Your username and password to the tool are personal. Never give them to anyone under <strong>any</strong> situation.</p>
            <div class="return-link">
              <a href="{{ site.sitePath }}">&#8592; Return to site</a>
            </div>
          </div>
        </div>
      </div>

      <div class="footer">
        theallseeingeye by Quackster<br>
        {{ locale.housekeeping_base_footer_copyright_habbo }}
      </div>
    </div>
  </body>
</html>
