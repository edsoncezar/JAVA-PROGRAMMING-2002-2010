<script language="javascript"><!--
  function RetornaHora(){
		var tmp = document.getElementById("hora").value.split(":");
		var s = tmp[2];	var m = tmp[1];	var h = tmp[0];
		s++;
		if (s > 59){ s = 0;	m++; }
		if (m > 59){ m = 0;	h++; }
		if (h > 23) h = 0;
		s = new String(s); if (s.length < 2) s = "0" + s;
		m = new String(m); if (m.length < 2) m = "0" + m;
		h = new String(h); if (h.length < 2) h = "0" + h;
		
		var temp = h + ":" + m + ":" + s;
		document.getElementById("hora").value = temp;
	}
	
// -->
</script>
A hora do servidor é: <input type="text" size="9" id="hora" readonly="true" value="<? echo date('H:i:s'); ?>" style="border:0;">
<script language="javascript">
	window.setInterval('RetornaHora()', 1000);
</script>