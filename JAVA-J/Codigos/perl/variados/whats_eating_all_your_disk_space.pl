du -sk . * | perl -e '
  $sum=<>;     # Get the otal space used from the first line
               # This is so we don't run 'du' twice
  while (<>) {
    ($size, $inode)=split;
    $inode .= "/" if (-d $inode);
    printf("%30s | %5d | %5.2f%%\n",$inode,$size,$size/$sum*100);
  }'
| sort -rn -k 3 | head

