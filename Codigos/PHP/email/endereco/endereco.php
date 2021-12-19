<?php 
// função esconde_email($end_email,criptografar) 
// $end_email     : o endereço de email 
// $criptografar  : true ou false 
// Para descriptografar o email coloque false 
//  conv_hex_to_chars($texto_criptografado,false) 
// 
// Um exemplo de email criptografado é: 
//      656D61696C406573636F6E6469646F2E636F6D2E6272 
// que vai dar em: 
//      email@escondido.com.br 
// 
// na imagem JPEG ou PNG. 
// 
function esconde_email($end_email,$criptografar=true) { 
    $tamanho = strlen($end_email); 
    $arr =     array('a' => '61','b' => '62','c' => '63','d' => '64','e' => '65','f' => '66','g' => '67','h' => '68','i' => '69','j' => '6A','k' => '6B','l' => '6C','m' => '6D','n' => '6E','o' => '6F','p' => '70','q' => '71','r' => '72','s' => '73','t' => '74','u' => '75','v' => '76','w' => '77','x' => '78','y' => '79','z' => '7A','@' => '40','_' => '5F','.' => '2E','-' => '2D','=' => '3D','A' => '41','B' => '42','C' => '43','D' => '44','E' => '45','F' => '46','G' => '47','H' => '48','I' => '49','J' => '4A','K' => '4B','L' => '4C','M' => '4D','N' => '4E','O' => '4F','P' => '50','Q' => '51','R' => '52','S' => '53','T' => '54','U' => '55','V' => '56','W' => '57','X' => '58','Y' => '59','Z' => '5A','0' => '30','1' => '31','2' => '32','3' => '33','4' => '34','5' => '35','6' => '36','7' => '37','8' => '38','9' => '39' ); 
    $inv_arr = array('61' => 'a','62' => 'b','63' => 'c','64' => 'd','65' => 'e','66' => 'f','67' => 'g','68' => 'h','69' => 'i','6A' => 'j','6B' => 'k','6C' => 'l','6D' => 'm','6E' => 'n','6F' => 'o','70' => 'p','71' => 'q','72' => 'r','73' => 's','74' => 't','75' => 'u','76' => 'v','77' => 'w','78' => 'x','79' => 'y','7A' => 'z','40' => '@','5F' => '_','2E' => '.','2D' => '-','3D' => '=','41' => 'A','42' => 'B','43' => 'C','44' => 'D','45' => 'E','46' => 'F','47' => 'G','48' => 'H','49' => 'I','4A' => 'J','4B' => 'K','4C' => 'L','4D' => 'M','4E' => 'N','4F' => 'O','50' => 'P','51' => 'Q','52' => 'R','53' => 'S','54' => 'T','55' => 'U','56' => 'V','57' => 'W','58' => 'X','59' => 'Y','5A' => 'Z','30' => '0','31' => '1','32' => '2','33' => '3','34' => '4','35' => '5','36' => '6','37' => '7','38' => '8','39' => '9'); 
    if($criptografar) 
    { 
        for ($i=0; $i < $tamanho; $i++) 
        { 
            $resultado .= $arr[$end_email{$i}]; 
        } 
    }else{ 
        $metade_tamanho = $tamanho/2; 
        $i = 0; 
        $posicao = 0; 
        while($i < $metade_tamanho ) 
        { 
            $pedaco = substr($end_email ,$posicao ,2); 
            $resultado .= $inv_arr[$pedaco]; 
            $posicao = $posicao + 2; 
            $i++; 
        } 
    } 
    return $resultado; 
} 
// $str ='nome@email.com.br'; 
// $text = conv_hex_to_chars($str,true); 
// $tipo = 'png'; // tipo de imagem que vc quer. PNG ou JPG? 
// ############################################ 
$tipo = 'png'; // tipo de imagem que vc quer. PNG ou JPG? 
$t = !empty($HTTP_GET_VARS['t'])? $HTTP_GET_VARS['t'] : '6E656E68756D5F656D40696C'; 

$text = esconde_email($t,false); 
$text_lenght = strlen($text); 

// tentando gerar de forma automática o tamanho da imagem 
$text_lenght = ($text_lenght * 6.4); 
$x_size = (int) $text_lenght; // largura da imagem 
$y_size = 15; // altura da imagem 

$image = imagecreate($x_size, $y_size); 
define('FUNDO', imagecolorallocate($image, 255, 255, 255) );   // A primeira sempre será a cor do fundo 
define('COR_FONTE', imagecolorallocate($image,   0,   0,   0) ); // cor da fonte 

$coord_x = 2;   // coordenada X onde o texto será inserido 
$coord_y = 1;   // coordenada Y onde o texto será inserido 
$font_size = 2; // tamanho da fonte do texto 

imagestring ($image, $font_size, $coord_x, $coord_y, $text, COR_FONTE); 
$tipo = strtolower($tipo); 
if($tipo =='png'){ 
    header('Content-type: image/png'); 
    imagepng($image); 
}elseif($tipo=='jpg'){ 
    header('Content-type: image/jpeg'); 
    imagejpeg($image); 
} 
exit; 
?> 
----------------------- email.php ----------------------------- 

Numa página qualquer sua, adicione a função: 
----------------------- algumapagina.php ----------------------------- 
<?php 
function esconde_email($end_email,$criptografar=true) { 
    $tamanho = strlen($end_email); 
    $arr =     array('a' => '61','b' => '62','c' => '63','d' => '64','e' => '65','f' => '66','g' => '67','h' => '68','i' => '69','j' => '6A','k' => '6B','l' => '6C','m' => '6D','n' => '6E','o' => '6F','p' => '70','q' => '71','r' => '72','s' => '73','t' => '74','u' => '75','v' => '76','w' => '77','x' => '78','y' => '79','z' => '7A','@' => '40','_' => '5F','.' => '2E','-' => '2D','=' => '3D','A' => '41','B' => '42','C' => '43','D' => '44','E' => '45','F' => '46','G' => '47','H' => '48','I' => '49','J' => '4A','K' => '4B','L' => '4C','M' => '4D','N' => '4E','O' => '4F','P' => '50','Q' => '51','R' => '52','S' => '53','T' => '54','U' => '55','V' => '56','W' => '57','X' => '58','Y' => '59','Z' => '5A','0' => '30','1' => '31','2' => '32','3' => '33','4' => '34','5' => '35','6' => '36','7' => '37','8' => '38','9' => '39' ); 
    $inv_arr = array('61' => 'a','62' => 'b','63' => 'c','64' => 'd','65' => 'e','66' => 'f','67' => 'g','68' => 'h','69' => 'i','6A' => 'j','6B' => 'k','6C' => 'l','6D' => 'm','6E' => 'n','6F' => 'o','70' => 'p','71' => 'q','72' => 'r','73' => 's','74' => 't','75' => 'u','76' => 'v','77' => 'w','78' => 'x','79' => 'y','7A' => 'z','40' => '@','5F' => '_','2E' => '.','2D' => '-','3D' => '=','41' => 'A','42' => 'B','43' => 'C','44' => 'D','45' => 'E','46' => 'F','47' => 'G','48' => 'H','49' => 'I','4A' => 'J','4B' => 'K','4C' => 'L','4D' => 'M','4E' => 'N','4F' => 'O','50' => 'P','51' => 'Q','52' => 'R','53' => 'S','54' => 'T','55' => 'U','56' => 'V','57' => 'W','58' => 'X','59' => 'Y','5A' => 'Z','30' => '0','31' => '1','32' => '2','33' => '3','34' => '4','35' => '5','36' => '6','37' => '7','38' => '8','39' => '9'); 
    if($criptografar) 
    { 
        for ($i=0; $i < $tamanho; $i++) 
        { 
            $resultado .= $arr[$end_email{$i}]; 
        } 
    }else{ 
        $metade_tamanho = $tamanho/2; 
        $i = 0; 
        $posicao = 0; 
        while($i < $metade_tamanho ) 
        { 
            $pedaco = substr($end_email ,$posicao ,2); 
            $resultado .= $inv_arr[$pedaco]; 
            $posicao = $posicao + 2; 
            $i++; 
        } 
    } 
    return $resultado; 
} 
$email ='email@escondido.com.br'; 
$text = esconde_email($email); 

?> 
<img src="email.php?t=<?php echo $text; ?>" alt="" border="0"> 
