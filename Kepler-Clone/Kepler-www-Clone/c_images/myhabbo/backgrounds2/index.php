<?php
 if ($handle = opendir('.')) {
   while (false !== ($file = readdir($handle)))
      {
          if ($file != "." && $file != "..")
	  {
          	$thelist .= '<img src="'.$file.'">';
          }
       }
  closedir($handle);
  }
?>
<P><?=$thelist?></p>