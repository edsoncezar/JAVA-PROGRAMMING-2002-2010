#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

jarray Java_MyTest_createArray(JNIEnv *env, jobject obj, jarray myarray) {

	int j, val;
	jsize sz, start, len;
	jint* parr;
	jarray newIntArray;
	jint intBuf[10];

// constructs a new array that is the same size of the passed in array

	sz = (*env)->GetArrayLength(env, myarray);

	newIntArray = (*env)->NewIntArray(env, sz);

	//set new values for a region within the new array (elements 3:6)
	start = 2;
	len = 4;

	if (start > sz - 1 ) {
	  start = sz - 1;
	  len = 0;
	}
	if ( start + len - 1 > sz) {
	  len = sz - start -1;
	}
	(*env)->GetIntArrayRegion(env, newIntArray, start, len, &intBuf[0]);
	for (j=0;j<len;j++) {
	  intBuf[j] = j+1;
	}
	(*env)->SetIntArrayRegion(env, newIntArray, start, len, &intBuf[0]);
	
	return(newIntArray);
}
