<?php 
    if ($radiobutton == "linux"){ 
       $f = fopen("linux.txt","r"); 
       $conta = fread($f,filesize("linux.txt")); 
       fclose($f); 
        
       $conta +=1; 
       $f = fopen("linux.txt","w"); 
       fputs($f,$conta); 
       fclose($f); 
    } 
    else{ 
       $f = fopen("windows.txt","r"); 
       $conta = fread($f,filesize("windows.txt")); 
       fclose($f); 
        
       $conta += 1; 
       $f = fopen("windows.txt","w"); 
       fputs($f,$conta); 
       fclose($f); 
    } 
     
    $file2 = fopen ("windows.txt","r"); 
    $tot_windows = fread($file2,filesize("windows.txt")); 
    fclose($file2); 
     
    $file1 = fopen ("linux.txt","r"); 
    $tot_linux = fread($file1,filesize("linux.txt")); 
    fclose($file1); 
     
    $total = $tot_linux + $tot_windows; 
     
    $perc_linux   = round(($tot_linux * 100)/$total,2); 
     
    $perc_windows = round(($tot_windows * 100)/$total,2); 
     
    $tamanho_linux   = ($perc_linux * 316)/100; 
    $tamanho_windows = ($perc_windows * 316)/100; 
     
     


echo "<html>"; 

echo "<body bgcolor=#FFFFFF>"; 
echo "<table width=75% border=1>"; 
  echo "<tr>"; 
   
  echo "<td bgcolor=#009999><font color=#FFFFFF><b>Resultados</b></font></td>"; 
  echo "</tr>"; 
  echo "<tr>"; 
    echo "<td>"; 
      echo "<p>Windows</p>"; 
       
      echo "<p><img src=imagem.jpg width=$tamanho_windows height=17> $perc_windows %</p>"; 
    echo "</td>"; 
  echo "</tr>"; 
  echo "<tr>"; 
    echo "<td>"; 
      echo "<p>Linux</p>"; 
      echo "<p><img src=imagem.jpg width=$tamanho_linux height=17> $perc_linux %</p>"; 
    echo "</td>"; 
  echo "</tr>"; 
echo "</table>"; 
echo "<h1>Total de votos: $total</h1>"; 

echo "<h5>Desenvolvido por Guilherme Pardi</h5>"; 
echo "</body>"; 
echo "</html>"; 

?> 