system("cls");
(%param)=&config();
$url    = $param{URL};
$port   = $param{POR};
$valid  = $param{VAL};
if ($valid eq "S"){
	$user   = $param{USR};
	$passwd = $param{PAS};
}
use LWP::UserAgent;

$ua = LWP::UserAgent->new;
$request = HTTP::Request->new(GET => $url);
if ($valid eq "S"){
	$request->authorization_basic($user, $passwd);
}
$respons = $ua->request($request);
$ua->proxy(['http', 'ftp'] , $url);
$h = new HTTP::Headers;
%accepts = $h->clone;
print $accepts->[1]->[1];
if ($respons->is_success) { 
	(@datos_t)=split(/\//, $url);
	$server = uc($datos_t[2]);
	# Webserver
	if (-e "sonda-w.flg"){
		$mensaje1 = "postemsg -S tec -r HARMLESS -m \"ReprogramacionDepositos:Verificacion de Webserver: OK\" hostname=$server aplicacion=ReprogramacionDepositos instancia=sonda BCRIO_rprgdep_webserver_up BCRIO_RPRGDEP";
		unlink("sonda-w.flg");
		if (-e "sonda-n.flg"){
			unlink("sonda-n.flg");
		}
	}
} else {
	(@datos_t)=split(/\//, $url);
	$server = uc($datos_t[2]);
	($status)=&conectiv($server);
	if ($status eq "OK"){
		# Webserver
		$mensaje1 = "postemsg -S tec -r CRITICAL -m \"ReprogramacionDepositos:Verificacion de Webserver FAIL Verificar Estado del Servicio y/o ASP: Error\" hostname=$server aplicacion=ReprogramacionDepositos instancia=sonda BCRIO_rprgdep_webserver_down BCRIO_RPRGDEP";
		open( FLAG, ">sonda-w.flg");
		print FLAG "fail\n";
		close(FLAG);
	}else{
		# Webserver
		$mensaje1 = "postemsg -S tec -r CRITICAL -m \"ReprogramacionDepositos:Verificacion de Webserver FAIL Verificar Estado de RED del Equipo: Error\" hostname=$server aplicacion=ReprogramacionDepositos instancia=sonda BCRIO_rprgdep_webserver_down BCRIO_RPRGDEP";
		open( FLAG, ">sonda-n.flg");
		print FLAG "fail\n";
		close(FLAG);
	}
}
system($mensaje1);
my $cont = $respons->content;
#Especifico Ruben Rodriguez.
	if ($cont =~ /FREEUSER=ERROR/){
		# IATX
		$mensaje1 = "postemsg -S tec -r CRITICAL -m \"ReprogramacionDepositos:Verificacion de ASP Webserver FAIL Verificar Estado del Servicio ASP: Error\" hostname=$server aplicacion=ReprogramacionDepositos instancia=sonda BCRIO_rprgdep_application_error BCRIO_RPRGDEP";
		open( FLAG, ">sonda-a.flg");
		print FLAG "fail\n";
		close(FLAG);
	}else{
		# IATX
		if (-e "sonda-a.flg"){
			$mensaje1 = "postemsg -S tec -r HARMLESS -m \"ReprogramacionDepositos:Verificacion de ASP Webserver : OK\" hostname=$server aplicacion=ReprogramacionDepositos instancia=sonda BCRIO_rprgdep_application_ok BCRIO_RPRGDEP";
			unlink("sonda-a.flg");
		}
	}
system($mensaje1);
if ($cont =~ /^4\d\d/) {
	die "$cont";
}
#print $cont;

sub codekey{
	use MIME::Base64;	
	($var, $ent) = @_;
	if($var eq E){
		$out = encode_base64($ent);
	}elsif($var eq D){
		$out = decode_base64($ent);
	}
	return($out);
}

sub config {
	open(CFG, "<sonda.ini");
	%param = ();
	while (<CFG>){
		$linea = $_;
		chomp($linea);
		($campo, $class, $info)=split(/\;/, $linea);
		chomp($campo, $class, $info);
		if ($class eq "C"){
			($content)=&codekey("D", $info);
			$param{$campo}=$content;
		}else{
			$content=$info;
			$param{$campo}=$content;
		}
	}
	close(CFG);
	return(%param);
}
sub conectiv {
	($host)=@_;
	use Net::Ping;
	$p = Net::Ping->new("icmp");
	$ping_v = $p->ping($host, 2);
	if (!$ping_v){
		$status = "FAIL";
	}else{
		$status = "OK";
	}
	$p->close();
	return($status);
}

##</code><code>##

$verif = "N";
while ($verif eq "N"){
	&inicio();
	($verif)=&verifica1();
}
if ($valid eq "S"){
	&validacion();
}
&make();
exit;
sub inicio {
	system("cls");
	print "Modulo de Configuración de Sonda Web Services\n";
	print "----------------------------------------------------------------------------------------\n\n";
	print " URL ( ejm. http://www.sinapse.com.ar/ )    : ";
	$url = <STDIN>;
	chomp($url);
	print " PORT ( ejm. 4444 )                         : ";
	$port = <STDIN>;
	chomp($port);
	print " Requiere Validacion ([S/s] Si - [N/n] No ) : ";
	$valid1 = <STDIN>;
	chomp($valid1);
	$valid  = uc(substr($valid1,0,1));
	print "\n [SYSTEM] Verificando datos ingresados\n\n";
	if ($valid eq "S"){
		if (!$port){
			print " [SYSTEM] URL especificada : ".$url."           con Validacion\n";
		}else{
			print " [SYSTEM] URL especificada : ".$url.":".$port." con Validacion\n";
		}
	}else{
		if (!$port){
			print " [SYSTEM] URL especificada : ".$url."           sin Validacion\n";
		}else{
			print " [SYSTEM] URL especificada : ".$url.":".$port." sin Validacion\n";
		}
	}
	print "\n";
	return();
}
sub verifica1 {
	print " Son correctos ([S/s] Si - [N/n] No )       : ";
	$verif1 = <STDIN>;
	chomp($verif1);
	$verif  = uc(substr($verif1,0,1));
	return($verif);
}
sub getNamePass {
	use Term::ReadKey;
	print " UserName                                   : ";
	my $username = <STDIN>;
	chomp ($username);
	print " Password                                   : ";
	ReadMode 'noecho';
	my $pass = ReadLine 0;
	chomp($pass);
	print "\n";
	return ($username,$pass);
}
sub validacion {
	print "\n [SYSTEM] El Site requiere autorizacion \n\n";
	($user,$pass)=&getNamePass();
	print "\n [SYSTEM] Encryptando Informacion vital \n\n";
	chomp($user, $pass);
	($user)=&codekey("E", $user);
	($pass)=&codekey("E", $pass);
	chomp($user, $pass);
	return();
}
sub make {
	print "\n [SYSTEM] Generando sonda.ini \n\n";
	open(CFG,">sonda.ini");
	print CFG "URL;N;$url\n";
	print CFG "POR;N;$port\n";
	print CFG "VAL;N;$valid\n";
	if ($valid eq "S"){
		print CFG "USR;C;$user\n";
		print CFG "PAS;C;$pass\n";
	}
	return();
}
sub codekey{
	use MIME::Base64;	
	($var, $ent) = @_;
	if($var eq E){
		$out = encode_base64($ent);
	}elsif($var eq D){
		$out = decode_base64($ent);
	}
	return($out);
}
