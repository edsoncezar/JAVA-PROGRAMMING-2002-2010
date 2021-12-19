#!/bin/sh
# Books passage for a Customer on a particular Cruise in a specified Cabin
# Uses command-line parameters:   
# BookPassage.bat <customerID> <cruiseID> <cabinID> <price>
#
ant run.client_122b -Dparam.cust=$1 -Dparam.cruise=$2 -Dparam.cabin=$3 -Dparam.price=$4
