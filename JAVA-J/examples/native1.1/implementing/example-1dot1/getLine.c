#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"


JNIEXPORT jint JNICALL Java_MyTest_getLine(JNIEnv *env, jobject obj, jstring s, jint len)
{
	int i, j;
	jsize strlen;
	char *str;
	const jchar * newstring;
	
	i = 0;
	
	str = (char *)(*env)->GetStringUTFChars(env, s, 0);
 	strlen = (*env)->GetStringLength(env,s);	

	if (strlen != len) {
	   return(-1);
	}


	newstring = (*env)->GetStringChars(env, s, 0);

	// change the integer 
	j = 0;
	for (i = 0; i < 10; i++) {
		len = len + i;
		j = len;
	}
	(*env)->ReleaseStringUTFChars(env, s, str);
	(*env)->ReleaseStringChars(env, s, (const jchar*)newstring);
	return(j);
}
