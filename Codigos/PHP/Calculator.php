//**************************************
    //     
    // Name: Calculator
    // Description:A Calculator with four ba
    //     sic functions. by Derian Conteh-Morgan.
    // By: PHP Code Exchange
    //**************************************
    //     
    
    form action=" <? echo $PHP_SELF ?>" method="post">
    1st number:<input type=text name=num1><br>
    2nd number:<input type=text name=num2><br>
    <p>
    What operation would you like to perform?<br>
    <input type=radio name=operation value="plus" checked>Add<br>
    <input type=radio name=operation value="minus">Subtract<br>
    <input type=radio name=operation value="X">Multiply<br>
    <input type=radio name=operation value="divided by">Divide<br>
    <input type=submit><input type=reset>
    </form>
    </body>
    </html>
    The answer to<? echo $num1;?><?echo $operation;?><? echo $num2;?>
    is:<br>
    <h1>
    <?php
    if ($operation == "plus")
    {$x = $num1 + $num2;
    print $x;}
    elseif ($operation == "minus")
    {$x = $num1 - $num2;
    print $x;}
    elseif ($operation == "X")
    {$x = $num1 * $num2;
    print $x;}
    else
    {$x = $num1 / $num2;
    print $x;}
    ?>
    </h1>
