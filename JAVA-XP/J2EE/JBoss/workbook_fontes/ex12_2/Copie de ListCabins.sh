#!/bin/sh
# Lists available cabins for a specific Cruise having a desired number of beds
# Uses command-line parameters:   
# ListCabins <cruiseID> <bedCount>
#
ant client_127 -Dparam.cruise=$1 -Dparam.bedcount=$2
