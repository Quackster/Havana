## Kepler-www
This repository contains the frontend web assets (gamedata) for [Kepler](https://github.com/Quackster/Kepler/), private Habbo Hotel server software.

These files are intended to be served to clients connecting to a Kepler server.

## Purpose
Kepler-www provides:

* Furnidata
* Figure data
* Furniture files
* Client files

This repository mirrors the behavior of the traditional gamedata or web build hosting used by Habbo Hotel, and is essential for running a fully functional Kepler server environment.

## Download

You can download the latest gamedata package from the [Releases](https://github.com/Quackster/Kepler-www/releases) page.

Each release contains a .zip archive of the current version, ready to be hosted on your own web server.

## Loader HTML

Here's the HTML for the Kepler loader.

```php
<?php
$ssoParam = '';
if (isset($_GET['sso'])) {
    $ssoValue = htmlspecialchars($_GET['sso'], ENT_QUOTES, 'UTF-8');
    $ssoParam = "use.sso.ticket=1;sso.ticket={$ssoValue}";
}
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Kepler</title>
</head>
<body bgcolor="black">
    <div align="center">
        <object 
            classid="clsid:166B1BCA-3F9C-11CF-8075-444553540000" 
            codebase="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=10,8,5,1,0" 
            id="habbo" width="720" height="540">
            
            <param name="src" value="http://localhost/dcr/14.1_b8/habbo.dcr">
            <param name="swRemote" value="swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Habbo Hotel' swContextMenu='true'">
            <param name="swStretchStyle" value="none">
            <param name="swText" value="">
            <param name="bgColor" value="#000000">
            <?php if ($ssoParam): ?>
                <param name="sw6" value="<?= $ssoParam ?>">
            <?php endif; ?>
            <param name="sw2" value="connection.info.host=localhost;connection.info.port=12321">
            <param name="sw4" value="connection.mus.host=localhost;connection.mus.port=12322">
            <param name="sw3" value="client.reload.url=http://localhost/">
            <param name="sw1" value="site.url=http://www.habbo.co.uk;url.prefix=http://www.habbo.co.uk">
            <param name="sw5" value="external.variables.txt=http://localhost/gamedata/external_variables.txt;external.texts.txt=http://localhost/gamedata/external_texts.txt">

            <embed 
                src="http://localhost/dcr/14.1_b8/habbo.dcr" 
                bgColor="#000000" 
                width="720" 
                height="540"
                swRemote="swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Habbo Hotel' swContextMenu='true'"
                swStretchStyle="none" 
                swText=""
                <?php if ($ssoParam): ?>
                sw6="<?= $ssoParam ?>"
                <?php endif; ?>
                sw2="connection.info.host=localhost;connection.info.port=12321"
                sw4="connection.mus.host=localhost;connection.mus.port=12322"
                sw3="client.reload.url=http://localhost/"
                sw1="site.url=http://www.habbo.co.uk;url.prefix=http://www.habbo.co.uk"
                sw5="external.variables.txt=http://localhost/gamedata/external_variables.txt;external.texts.txt=http://localhost/gamedata/external_texts.txt"
                type="application/x-director"
                pluginspage="http://www.macromedia.com/shockwave/download/">
            </embed>
        </object>
    </div>
</body>
</html>
```

## Contributing

This repository is mostly static and updated to match the expected format for Kepler. Pull requests to fix or update gamedata files (e.g. from newer releases or to improve compatibility) are welcome.

## License
This project is provided for educational and development purposes. Refer to the main Kepler repository for overall licensing and contribution guidelines.
