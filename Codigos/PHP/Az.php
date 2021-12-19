    //**************************************
    //     
    // Name: A - Z links the easy way
    // Description:To create A - Z links qui
    //     ckly and easily for your site.
    // By: Louie Simpson
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.289/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <? for($i=65;$i<91;$i++) { ?>
    	<a href="<?=$PHP_SELF?>?letter=<?=chr($i)?>"><?=chr($i)?></a>
    <?	} ?>

