//Jabin Sufianto
//ISE 582 Hw#1: due sept 11 2003

public class PPProfit {
	public static void main (String argv[]){
		//declare all variables and also assign values to them
		// values are from the e-mail order form
		int posqty=4; //quantity of poster
		int frameqty=2; //quantity of posters to be framed
		int cropx1=0;
		int cropx2=16;
		int cropy1=0;
		int cropy2=20;
		int x1=0;
		int x2=0;
		int y1=0;
		int y2=0;
		int price = 0;
		// poster type
		int postype = 3;
		int posprice = 0;
		// shipping option: 1 = muggle mail, 2 = Floo network, 3 = owl post
		int shipopt= 2;
		// poster size: 1 = 24*32, 2 = 16*24, 3 = 8*12, 4 = own specification
		int possize= 4; 
		int posdim = 0; //used in calculation of poster dimensions
		int frametype= 2;
		
		if (shipopt>=4){
			System.out.println("ERROR: Undefined shipping option.\n");
		} else if (shipopt < 1){
			System.out.println("ERROR: Undefined shipping option.\n");
		}

		if (possize>4){
			System.out.println("ERROR: Undefined poster size.\n");
		} else if (possize < 1){
			System.out.println("ERROR: Undefined poster size.\n");
		}

		//verify the frame option
		if (frametype>=4){
			System.out.println("ERROR: Undefined frame type.\n");
		} else if (frametype < 1){
			System.out.println("ERROR: Undefined frame type.\n");
		}

		if (postype == 1){
			// dobey poster
			posprice = 1*493 + 2*29 + 12;
		} else if (postype == 2){
			//harry poster
			posprice = 1*493 + 4*29;
		}else if (postype ==3){
			//car poster
			posprice = 1*493 + 1*29 + 16;
		}else if (postype == 4){
			//hawkes poster
			posprice = 1*493 + 2*29 + 3;
		}


		//verify that the cropping size is reasonable
		if (possize == 4){
			if (cropx1 >=24){
				System.out.println("ERROR: Unreasonable cropping x1.\n");
			} else if (cropx1 <0){
				System.out.println("ERROR: Unreasonable cropping x1.\n");
			} else if (cropx2 <=0){
				System.out.println("ERROR: Unreasonable cropping x2.\n");
			} else if (cropx2 > 24){
				System.out.println("ERROR: Unreasonable cropping x2.\n");
			} else if (cropy1 < 0){
				System.out.println("ERROR: Unreasonable cropping y1.\n");
			} else if (cropy1 >= 32){
				System.out.println("ERROR: Unreasonable cropping y1.\n");
			} else if (cropy2 <=0){
				System.out.println("ERROR: Unreasonable cropping y2.\n");
			} else if (cropy2 > 32){
				System.out.println("ERROR: Unreasonable cropping y2.\n");
			}
		}

		// calculating the price of poster
		if (possize == 1){
			x1 = 0;
			x2 = 24;
			y1 = 0;
			y2 = 32;
			int tempnum = (x2-x1)*(y2-y1);
			float tempratio = (float)tempnum/(24*32);
			float tempprice = (posprice * tempratio);
			price = (int)tempprice * posqty;
		} else if (possize == 2){
			System.out.println("16*24\n");
			x1 = 0;
			x2 = 16;
			y1 = 0;
			y2 = 24;
			int tempnum = (x2-x1)*(y2-y1);
			float tempratio = (float)tempnum/(24*32);
			float tempprice = (posprice * tempratio);
			price = (int)tempprice * posqty;
		} else if (possize == 3){
			x1 = 0;
			x2 = 8;
			y1 = 0;
			y2 = 12;
			int tempnum = (x2-x1)*(y2-y1);
			float tempratio = (float)tempnum/(24*32);
			float tempprice = (posprice * tempratio);
			price = (int)tempprice * posqty;			
		} else if (possize == 4){
			x1 = cropx1;
			x2 = cropx2;
			y1 = cropy1;
			y2 = cropy2;
			int tempnum = (x2-x1)*(y2-y1);
			float tempratio = (float)tempnum/(24*32);
			float tempprice = (posprice * tempratio);
			price = (int)tempprice * posqty;
		}

		
		//calculating the cost of frame(s)
		if (frametype == 1){
			// ash frame
			price = price + (17*29)*frameqty;
		} else if (frametype == 2){
			//mahogany frame
			price = price + (23*29)*frameqty;
		} else if (frametype == 3){
			//twigs frame
			price = price + (11)*frameqty;
		}

		//calculating the cost of frame(s)
		if (shipopt == 1){
			// muggle mail
			price = price + 16*posqty;
		} else if (shipopt == 2){
			//floo network
			price = price + ((2*29) + 11)*posqty;
		} else if (shipopt == 3){
			//owl post
			price = price + 12*posqty;
		}

		// print out the total cost
		int galleon = 0;
		int sickle = 0;
		int knut = 0; 
		galleon = price / 493;
		sickle = (price - (galleon*493))/29;
		knut = price - (galleon*493) - (sickle*29);
		System.out.println("The total cost is: "+ galleon +" galleon, " + sickle + " sickles, " 
			+ knut + " knuts.\n");
		System.out.println("Thanks for shopping with us.\n");
	}
}