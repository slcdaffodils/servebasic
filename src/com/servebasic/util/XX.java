package com.servebasic.util;

import java.io.*;
import java.net.*;
//scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
class XX
{
  public static void main (String [] args) throws IOException, URISyntaxException
  {
   if (args.length != 1)
   {
     System.err.println ("usage: java URLDemo1 url");
     return;
   }

  // URL url = new URL (args [0]);
   URI url = new URI(args[0]);
   System.out.println ("Authority = " +
             url.getAuthority());

   System.out.println ("Default port = " +
             url.getPort());

   System.out.println ("File = " +
             url.getScheme());

   System.out.println ("Host = " +
             url.getHost ());

   System.out.println ("Path = " +
             url.getPath ());

   System.out.println ("Port = " +
             url.getPort ());


   System.out.println ("Query = " +
             url.getQuery ());



   System.out.println ("User Info = " +
             url.getUserInfo ());

   System.out.print ('\n');
//
//   InputStream is = url.openStream ();
//
//   int ch;
//   while ((ch = is.read ()) != -1)
//     System.out.print ((char) ch);
//
//   is.close ();
  }
}