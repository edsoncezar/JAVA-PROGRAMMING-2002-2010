sub cdonts_mailer
{
  my(%mail_props) = @_;

  unless (exists $mail_props{'to'} and defined $mail_props{'to'})
  {
    carp "No Addressee to send mail to.\n";
    return 0;
  }

  #
  # Create the mail object.
  #
  my $cdonts = new Win32::OLE('CDONTS.NewMail');
  return 0 unless ($cdonts);

  #
  # Set the properties.
  #
  $cdonts->{'Subject'} = $mail_props{'subject'} || '[No Subject]';
  $cdonts->{'To'} = join(";", split(/[ ,;]+/, $mail_props{'to'}));
  $cdonts->{'Body'} = $mail_props{'body'} || "\r\n";
  $cdonts->{'From'} = $mail_props{'from'};
  $cdonts->{'Cc'} = $mail_props{'cc'};
  $cdonts->{'Bcc'} = $mail_props{'bcc'};
  $cdonts->{'Importance'} = (exists $mail_props{'importance'})?
        $mail_props{'importance'}:
        1;  # 2=high, 1=normal, 0=low
  $cdonts->{'BodyFormat'} = (exists $mail_props{'bodyformat'})?
        $mail_props{'bodyformat'}:
        1;  # 0=html; 1=plaintext
  $cdonts->{'MailFormat'} = (exists $mail_props{'mailformat'})?
        $mail_props{'mailformat'}:
        1;  # 0=Mime; 1=plaintext

  #
  # If there are any files to attach, check to see if
  # it's appropriate to do so before attaching.
  #
  foreach my $attach_file (@{ $mail_props{'attachments'} })
  {
    if ($attach_file and -f $attach_file)
    {
      $cdonts->AttachFile($attach_file);
    }
    else
    {
      carp "Failed to attach '$attach_file' to this message.\n";
    }
  }

  #
  # Send it off.
  #
  return 1 if ($cdonts->Send());

  carp "Error: '$!'";
  return 0;
}