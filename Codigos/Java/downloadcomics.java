import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class downloadcomics
{
	/** java arguments to get past proxy server */
	static
	{
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "192.168.9.10");
		System.getProperties().put("proxyPort", "8080");
	}

   public static void main(String args[]) throws Exception
    {
		int totalbytes=0;

		/***
		 * Comic series to download
		 * Garfield = ga
		 * Calvin & Hobbes = ch
		 * Citizen Dog = cd
		 * Bound & Gagged = tmbou
		 * Fred Bassett = tmfba
		 * Animal Crackers = tmani
		 ***/
		String series = "ga";

		DataOutputStream log = new DataOutputStream (new BufferedOutputStream(new FileOutputStream("log"+series+".txt")));

		/***
		 * This is currently set to download comics from Jan 01 to Dec 02
		 * For eighties and nineties, use format year=89; year<=97
		 ***/
		for (int year=1; year<=2; year++)
		{
			for (int month=1; month<=12; month++)
			{
				for (int day=1; day<=31; day++)
				{
					String string_day = String.valueOf(day);
					String string_month = String.valueOf(month);
					String string_year = String.valueOf(year);
					if (string_day.length() < 2)
					{
						string_day = "0" + string_day;
					}
					if (string_month.length() < 2)
					{
						string_month = "0" + string_month;
					}
					if (string_year.length() < 2)
					{
						string_year = "0" + string_year;
					}

					System.out.print("Trying: "+series+string_year+string_month+string_day+" - ");
					log.writeBytes("Trying: "+series+string_year+string_month+string_day+" - ");

					try
					{
						URL url = new URL("http://images.ucomics.com/comics/"+series+"/20"+string_year+"/"+series+string_year+string_month+string_day+".gif");
						URLConnection connection = url.openConnection();
						InputStream is = url.openStream();
						FileOutputStream fos = null;
						String localFile = null;

						StringTokenizer st = new StringTokenizer(url.getFile(), "/");
						while (st.hasMoreTokens())
						{
							localFile = st.nextToken();
						}

						fos = new FileOutputStream(series+string_year+string_month+string_day+".gif");
						int oneChar, filesize=0;

						while ((oneChar = is.read()) != -1)
						{
							fos.write(oneChar);
							filesize++;
						}

						is.close();
						fos.close();
						totalbytes+=filesize;

						if (filesize == 0)
						{
							System.out.print(" failed\n");
						}


						System.out.print("Downloaded\n");
						log.writeBytes (filesize+" bytes copied\n");
					}
					catch (FileNotFoundException e)
					{
					System.out.print("File Not Found\n");
					log.writeBytes("File Not Found\n");
					}
				}
			}
		}

		//System.out.println(totalbytes/1000+"k downloaded \n");

		log.close();
	}
}
