    //**************************************
    //     
    // Name: CleanupSpaces
    // Description:This simple function remo
    //     ves all unnecessary spaces from a string
    //     . It removes all whitespaces and spaces 
    //     when there are more than one in a row.
    // By: Nola Stowe
    //
    // Inputs:a string
    //
    // Returns:a string
    //
    // Assumes:Assumed it is given a string
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.358/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    //*********
    // CleanupSpaces: Function for removing 
    //     multiple spaces (more than 1 in row)
    // leading, and trailing spaces
    //*********
    function CleanupSpaces($s) {
    return trim(eregi_replace("[[:space:]]{2,}", " ", $s));
    }
