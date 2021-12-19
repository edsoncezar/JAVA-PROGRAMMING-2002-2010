<? 
        /* Gerador PHP de datas em portugues, escrito por Gustavo Ota e-mail: sushinabrasa@hotmail.com */ 
        /* Inicio de dia da semana */ 
        $Semana = date("l"); 
         
        if($Semana == "Monday") 
        { 
            print("Segunda-feira"); 
        } 
        if($Semana == "Tuesday") 
        { 
            print("Ter&ccedil;a-feira"); 
        } 
        if($Semana == "Wednesday") 
        { 
            print("Quarta-feira"); 
        } 
        if($Semana == "Thursday") 
        { 
            print("Quinta-feira"); 
        } 
        if($Semana == "Friday") 
        { 
            print("Sexta-feira"); 
        } 
        if($Semana == "Saturday") 
        { 
            print("S&aacute;bado"); 
        } 
        if($Semana == "Sunday") 
        { 
            print("Domingo"); 
        } 
        $virgula = ", "; 
         
            print("$virgula"); 
             
        /* Fim de dia da semana */ 
        /* Inicio de dia do mes */ 
        $Dia = date("d"); 
         
            print("$Dia de "); 
             
        /* Fim de dia do mes */ 
        /* Inicio de mes */ 
        $Mes = date("n"); 
         
        if($Mes == "1") 
        { 
            print("janeiro"); 
        } 
        if($Mes == "2") 
        { 
            print("fevereiro"); 
        } 
        if($Mes == "3") 
        { 
            print("mar&ccedil;o"); 
        } 
        if($Mes == "4") 
        { 
            print("abril"); 
        } 
        if($Mes == "5") 
        { 
            print("maio"); 
        } 
        if($Mes == "6") 
        { 
            print("junho"); 
        } 
        if($Mes == "7") 
        { 
            print("julho"); 
        } 
        if($Mes == "8") 
        { 
            print("agosto"); 
        } 
        if($Mes == "9") 
        { 
            print("setembro"); 
        } 
        if($Mes == "10") 
        { 
            print("outubro"); 
        } 
        if($Mes == "11") 
        { 
            print("novembro"); 
        } 
        if($Mes == "12") 
        { 
            print("dezembro"); 
        } 
        $de = " de "; 
         
            print("$de"); 
             
        /* Fim de mes */ 
        /* Inicio de ano */ 
        $Ano = date("Y"); 
         
            print("$Ano."); 
        /* Fim de ano */ 
         
?> 