<html>
<head>
  <title>{{ locale.account_submit_redirecting }}</title>
  <meta http-equiv="content-type" content="text/html" />
  <style type="text/css">body { background-color: #e3e3db; text-align: center; font: 11px Verdana, Arial, Helvetica, sans-serif; } a { color: #fc6204; }</style>
</head>
<body>

<script type="text/javascript">window.location.replace('{{ site.sitePath }}/?page=&username={{ username }}&rememberme={{ rememberMe }}');</script><noscript><meta http-equiv="Refresh" content="0;URL={{ site.sitePath }}/?page=&username={{ username }}&rememberme={{ rememberMe }}"></noscript>

<p class="btn">{{ locale.account_submit_if_you_are_not_automatically_redirected_please }} <a href="{{ site.sitePath }}/?page=&username={{ username }}&rememberme={{ rememberMe }}" id="manual_redirect_link">{{ locale.account_submit_click_here }}</a></p>


</body>
</html>
