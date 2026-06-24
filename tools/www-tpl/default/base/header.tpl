{% if session.loggedIn == false %}

<div id="overlay"></div>

<div id="header-container">
	<div id="header" class="clearfix">
		<h1><a href="{{ site.sitePath }}/"></a></h1>
       <div id="subnavi">
            <div id="subnavi-user">
                <div class="clearfix">&nbsp;</div>
                <p>
				        <a href="{{ site.sitePath }}/client" id="enter-hotel-open-medium-link" target="client" onclick="HabboClient.openOrFocus(this); return false;">{{ locale.base_header_enter }} {{ site.siteName }} {{ locale.base_header_hotel }}</a>
                </p>
            </div>
            <div id="subnavi-login">
                <form action="{{ site.sitePath }}/account/submit" method="post" id="login-form">
            		<input type="hidden" name="page" value="/community" />
                    <ul>
                        <li>
                            <label for="login-username" class="login-text"><b>{{ locale.base_header_username }}</b></label>

                            <input tabindex="1" type="text" class="login-field" name="username" id="login-username" />
		                    <a href="#" id="login-submit-new-button" class="new-button" style="float: left; display:none"><b>{{ locale.base_header_log_in }}</b><i></i></a>
                            <input type="submit" id="login-submit-button" value="{{ locale.base_header_log_in }}" class="submit"/>
                        </li>
                        <li>
                            <label for="login-password" class="login-text"><b>{{ locale.base_header_password }}</b></label>
                            <input tabindex="2" type="password" class="login-field" name="password" id="login-password" />
                            <input tabindex="3" type="checkbox" name="_login_remember_me" value="true" id="login-remember-me" />

                            <label for="login-remember-me" class="left">{{ locale.base_header_remember_me }}</label>
                        </li>
                    </ul>
                </form>
                <div id="subnavi-login-help" class="clearfix">
                    <ul>
                        <li class="register"><a href="{{ site.sitePath }}/account/password/forgot" id="forgot-password"><span>{{ locale.base_header_i_forgot_my_password_username }}</span></a></li>
                    	<li><a href="{{ site.sitePath }}/register"><span>{{ locale.base_header_register }}</span></a></li>

                    </ul>
                </div>
<div id="remember-me-notification" class="bottom-bubble" style="display:none;">
	<div class="bottom-bubble-t"><div></div></div>
	<div class="bottom-bubble-c">
					{{ locale.base_header_by_selecting_remember_me_you_will_stay_signed_in_on_this_computer_until_you_click_sign_out_if_this_is_a_public_computer_please_do_not_use_this_feature }}	</div>
	<div class="bottom-bubble-b"><div></div></div>
</div>

            </div>
        </div>
		<script type="text/javascript">
			LoginFormUI.init();
			RememberMeUI.init("right");
		</script> 
		<ul id="navi">
			<li id="tab-register-now"><a href="{{ site.sitePath }}/register">{{ locale.base_header_register_now }}</a><span></span></li>
			
			{% if session.currentPage == "community" %}
			<li class="selected">
				<strong>{{ locale.base_header_community }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/community">{{ locale.base_header_community }}</a>			<span></span>
			</li>
			{% endif %}
			
			{% if session.currentPage == "credits" %}
			<li class="selected">
				<strong>{{ locale.base_header_coins }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/credits">{{ locale.base_header_coins }}</a>			<span></span>
			</li>
			{% endif %}
			
			{% if session.currentPage == "games" %}
			<li class="selected">
				<strong>{{ locale.base_header_games }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/games">{{ locale.base_header_games }}</a>			<span></span>
			</li>
			{% endif %}
			
		</ul>

        <div id="habbos-online"><div class="rounded"><span>{{ site.formattedUsersOnline }} {{ site.siteName }}{{ locale.base_header_s_online }}</span></div></div>
	</div>
</div>

{% else %}

<div id="overlay"></div>
<div id="header-container">
	<div id="header" class="clearfix">
		<h1><a href="{{ site.sitePath }}/"></a></h1>
       <div id="subnavi">
			<div id="subnavi-user">
				<ul>
					<li id="myfriends"><a href="#"><span>{{ locale.base_header_my_friends }}</span></a><span class="r"></span></li>
					<li id="mygroups"><a href="#"><span>{{ locale.base_header_my_groups }}</span></a><span class="r"></span></li>
					<li id="myrooms"><a href="#"><span>{{ locale.base_header_my_rooms }}</span></a><span class="r"></span></li>
				</ul>
			</div>
            <div id="subnavi-search">
                <div id="subnavi-search-upper">

                <ul id="subnavi-search-links">
                    <li><a href="{{ site.sitePath }}/help" target="habbohelp" onclick="openOrFocusHelp(this); return false">{{ locale.base_header_help }}</a></li>
					<li><a href="{{ site.sitePath }}/account/logout" class="userlink" id="signout">{{ locale.base_header_sign_out }}</a></li>
				</ul>
                </div>
            </div>
            <div id="to-hotel">
                        {% if site.serverOnline %}
						<a href="{{ site.sitePath }}/client" class="new-button green-button" target="client" onclick="HabboClient.openOrFocus(this); return false;"><b>{{ locale.base_header_enter }} {{ site.siteName }} 
                        {{ locale.base_header_hotel }}</b><i></i></a>
                        {% else %}
                        <div id="hotel-closed-medium">{{ site.siteName }} {{ locale.base_header_hotel_is_offline }}</div>
                        {% endif %}
			</div>
        </div>
		<script type="text/javascript">
		L10N.put("purchase.group.title", "{{ locale.base_header_create_a_group|escape('js') }}");
		document.observe("dom:loaded", function() {
            $("signout").observe("click", function() {
                HabboClient.close();
            });
        });
        </script>
		<ul id="navi">
			{% if session.currentPage == "me" %}
			<li class="selected">
				<strong>{{ playerDetails.getName() }} </strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/me">{{ playerDetails.getName() }}</a>			<span></span>
			</li>
			{% endif %}
			
			{% if session.currentPage == "community" %}
			<li class="selected">
				<strong>{{ locale.base_header_community }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/community">{{ locale.base_header_community }}</a>			<span></span>
			</li>
			{% endif %}
			
			{% if session.currentPage == "credits" %}
			<li class="selected">
				<strong>{{ locale.base_header_coins }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/credits">{{ locale.base_header_coins }}</a>			<span></span>
			</li>
			{% endif %}
			
			{% if session.currentPage == "games" %}
			<li class="selected">
				<strong>{{ locale.base_header_games }}</strong>			<span></span>
			</li>
			{% else %}
			<li>
				<a href="{{ site.sitePath }}/games">{{ locale.base_header_games }}</a>			<span></span>
			</li>
			{% endif %}
			{% if playerDetails.getRank().getRankId() >= 6 %}
				<li id="tab-register-now"><a href="{{ site.sitePath }}/allseeingeye/hk/">{{ locale.base_header_housekeeping }}</a><span></span></li>
			{% endif %}
			
		</ul>

        <div id="habbos-online"><div class="rounded"><span>{{ site.formattedUsersOnline }} {{ site.siteName }}{{ locale.base_header_s_online }}</span></div></div>
	</div>
</div>

{% endif %}
