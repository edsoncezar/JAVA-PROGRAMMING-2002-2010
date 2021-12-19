#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

jint Java_MyTest_getIntValue(JNIEnv *env, jobject obj, jarray myarray,
			     jarray dblarray) {
	
	int j, val;
	jsize sz;
	int arr[10];
	int *pa;
	jint* parr;


	sz = (*env)->GetArrayLength(env, myarray);
	printf("Size of int array: %d\n", sz);
	sz = (*env)->GetArrayLength(env, dblarray);
	printf("Size of double array: %d\n", sz);
	
	// get the individual array values //
	parr = (*env)->GetIntArrayElements(env, myarray, 0);
	
	for (j = 0; j< 10; j++) {
	   val = (int) *(parr+j);
	   printf("Value of array element: %d\n", val);
	}

	// change the array values //
	for (j = 0; j<10; j++) {
	   parr[j] = 33;
	}
	for (j=0; j<10;j++) {
	   val = (int) *(parr+j);
	   printf("Value of array element: %d\n", val);
	}
	(*env)->ReleaseIntArrayElements(env,myarray, parr, 0);
	return(j);
}
