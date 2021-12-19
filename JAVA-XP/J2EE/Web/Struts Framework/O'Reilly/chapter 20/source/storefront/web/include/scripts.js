function popUpMenu(url,name) {
 newWindow = window.open(url,name,'toolbar=0,location=0,directories=0,status=0,menubar=1,scrollbars=yes,resizable=yes,width=640,height=480');
}


function validateSigninForm(frm) {
  if (frm.email.value.length == 0) {
   alert("Please enter your email address");
   frm.email.focus();
   return false;
  }
  if ((frm.email.value.indexOf("@") == -1) || (frm.email.value.indexOf(".") == -1)) {
    alert("Please enter a valid e-mail address.");
    frm.email.select();
    frm.email.focus();
    return false;
  }
  return true;
}
