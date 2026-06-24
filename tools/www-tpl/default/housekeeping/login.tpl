{% include "housekeeping/base/header.tpl" %}
  <body>
    <div class="panel">
      <div class="header_left">&nbsp;<br />&nbsp;<br />&nbsp;<br /><a href="{{ site.sitePath }}"><img src="{{ site.staticContentPath }}/housekeeping/images/header_logo.png" alt="{{ site.siteName }}"></a></div>
      <div class="header_right"><img src="{{ site.staticContentPath }}/housekeeping/images/header_tm1.gif" alt=""></div>

      <div class="panel_title">
        <span class="text">{{ locale.housekeeping_base_navigation_havana_web }} {{ locale.housekeeping_base_header_housekeeping }}</span>
        <div class="close_button"><a href="{{ site.sitePath }}"><img src="{{ site.staticContentPath }}/housekeeping/images/button_close.gif" alt="{{ locale.housekeeping_base_navigation_home }}"></a></div>
      </div>

      <div class="topborder"></div>
      <div class="page_title">
        <img src="{{ site.staticContentPath }}/housekeeping/images/icons/cfh.gif" class="pticon" alt="">
        <span class="page_name_shadow">{{ locale.housekeeping_login_housekeeping_text }}</span>
        <span class="page_name">{{ locale.housekeeping_login_housekeeping_text }}</span>
      </div>
      <div class="page_main">
        <table border="0" cellpadding="0" cellspacing="0" height="100%">
          <tbody>
            <tr height="100%">
              <td class="page_main_left">
                <div class="loginuser">{{ locale.housekeeping_login_please_sign_in_to_access_the_housekeeping }}</div>
                <div class="hr"></div>
                <div class="text">
                  <form id="loginform" action="/{{ site.housekeepingPath }}/login" method="post">
                    <strong>{{ locale.housekeeping_login_username }}:</strong><br />
                    <input type="text" size="20" name="hkusername" id="namefield" value="" /><br />
                    <strong>{{ locale.housekeeping_login_password }}:</strong><br />
                    <input type="password" size="20" name="hkpassword" value="" />
                    <div class="button left"><input type="submit" value="{{ locale.housekeeping_login_sign_in }}"></input></div>
                  </form>
                </div>
                <div class="hr"></div>
                <div class="text">
                  {{ locale.housekeeping_login_oops }} <a href="{{ site.sitePath }}">{{ locale.housekeeping_login_take_me_back_please }}</a>
                </div>
              </td>
              <td class="page_main_right">
                <div class="page_content">
                  {% include "housekeeping/base/alert.tpl" %}
                  <div class="login_top">
                    <img src="{{ site.staticContentPath }}/housekeeping/images/workman_habbo_down.gif" alt="" /><br />
                    {{ locale.housekeeping_base_navigation_havana_web }}
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="page_footer">
        <div class="buttons">
          <input type="button" class="footer_button" value="{{ locale.housekeeping_base_footer_homepage }}" onclick="window.location.href='{{ site.sitePath }}/'"></input>
        </div>
      </div>
      <div class="copylight">{{ locale.housekeeping_base_footer_powered_by }} {{ locale.housekeeping_base_navigation_havana_web }}<br />{{ locale.housekeeping_base_footer_design_credit }}<br />{{ locale.housekeeping_base_footer_copyright_habbo }}</div>
    </div>
  </body>
</html>
