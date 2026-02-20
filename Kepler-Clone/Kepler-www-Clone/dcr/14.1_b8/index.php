<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Habbo Hotel ~</title>
</head>

<body bgcolor='black'<!-- leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'-->>
<div align='center'>
<object classid='clsid:166B1BCA-3F9C-11CF-8075-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=10,8,5,1,0' id='habbo' width='720' height='540'>
<param name='src' value='http://localhost/v14/habbo.dcr'>
<param name='swRemote' value='swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Habbo Hotel' swContextMenu='true' '>
<param name='swStretchStyle' value='none'>
<param name='swText' value=''>

<param name='bgColor' value='#000000'>
<?php if (isset($_GET['sso'])) { ?>
<param name='sw6' value='use.sso.ticket=1;sso.ticket=<?php echo $_GET['sso']; ?>'>
<?php } ?>
<param name='sw2' value='connection.info.host=localhost;connection.info.port=12321'>
<param name='sw4' value='connection.mus.host=localhost;connection.mus.port=12322'>
<param name='sw3' value='client.reload.url=http://localhost/'>
<param name='sw1' value='site.url=http://www.habbo.ch;url.prefix=http://www.habbo.ch'>
<param name='sw5' value='external.variables.txt=http://localhost/v14/external_vars.txt;external.texts.txt=http://localhost/v14/external_texts.txt'>
<embed src='http://localhost/v14/habbo.dcr' bgColor='#000000' width='720' height='540' swRemote='swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Habbo Hotel' swContextMenu='true'' swStretchStyle='none' swText='' type='application/x-director' pluginspage='http://www.macromedia.com/shockwave/download/'
<?php if (isset($_GET['sso'])) { ?>
sw6='use.sso.ticket=1;sso.ticket=<?php echo $_GET['sso']; ?>'
<?php } ?>
sw2='connection.info.host=localhost;connection.info.port=12321'
sw4='connection.mus.host=localhost;connection.mus.port=12322'
sw3='client.reload.url=http://localhost/'
sw1='site.url=http://www.habbo.ch;url.prefix=http://www.habbo.co.uk'
sw5='external.variables.txt=http://localhost/v14/external_vars.txt;external.texts.txt=http://localhost/v14/external_texts.txt'></embed>
</object>
</div>
</body>

</html>
