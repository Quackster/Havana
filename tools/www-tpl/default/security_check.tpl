<html>
<head>
  <title>{{ locale.security_check_redirecting }}</title>
  <meta http-equiv="content-type" content="text/html" />
  <style type="text/css">body { background-color: #e3e3db; text-align: center; font: 11px Verdana, Arial, Helvetica, sans-serif; } a { color: #fc6204; }</style>
</head>
<body>

<script type="text/javascript">window.location.replace('{{ site.sitePath }}{{ redirectPath }}');</script><noscript><meta http-equiv="Refresh" content="0;URL={{ site.sitePath }}{{ redirectPath }}"></noscript>

<p class="btn">{{ locale.security_check_if_you_are_not_automatically_redirected_please }} <a href="{{ site.sitePath }}{{ redirectPath }}" id="manual_redirect_link">{{ locale.security_check_click_here }}</a></p>


</body>
</html>
