<?
$english_day = date("l");
switch($english_day)
{
	case "Monday":
		$portuguese_day = "Segunda-Feira";
		break;
	case "Tuesday":
		$portuguese_day = "Terça-Feira";
		break;
	case "Wednesday":
		$portuguese_day = "Quarta-Feira";
		break;
	case "Thursday":
		$portuguese_day = "Quinta-Feira";
		break;	
	case "Friday":
		$portuguese_day = "Sexta-Feira";
		break;
	case "Saturday":
		$portuguese_day = "Sábado";
		break;
	case "Sunday":
		$portuguese_day = "Domingo";
		break;
}

$english_month = date("n");

switch($english_month)
{
	case "1":
		$portuguese_month = "Janeiro";
		break;
	case "2":
		$portuguese_month = "Fevereiro";
		break;
	case "3":
		$portuguese_month = "Março";
		break;
	case "4":
		$portuguese_month = "Abril";
		break;
	case "5":
		$portuguese_month = "Maio";
		break;
	case "6":
		$portuguese_month = "Junho";
		break;
	case "7":
		$portuguese_month = "Julho";
		break;
	case "8":
		$portuguese_month = "Agosto";
		break;
	case "9":
		$portuguese_month = "Setembro";
		break;
	case "10":
		$portuguese_month = "Outubro";
		break;
	case "11":
		$portuguese_month = "Novembro";
		break;
	case "12":
		$portuguese_month = "Dezembro";
		break;
}
echo $portuguese_day . ", " . date("d") . " de " . $portuguese_month . " de " . date("Y");
?>
