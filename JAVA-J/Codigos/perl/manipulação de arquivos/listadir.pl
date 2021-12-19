#!/bin/perl
$diretorio = "./";

opendir(diretorio, "$diretorio");
@lista = readdir(diretorio);
closedir(diretorio);

foreach $arquivo(@lista)
{
  if ($arquivo eq "senhas.pwd"){print "senhas:->"}
  if ($arquivo eq "me_pule.jfs") {next}
  print qq~$arquivo~. "\n";
}