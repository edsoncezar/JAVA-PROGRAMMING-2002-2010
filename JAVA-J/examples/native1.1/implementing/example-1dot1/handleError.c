#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

jint Java_MyTest_handleError(JNIEnv *env, jobject obj, jint ival) {
	jint ret, testInt;
	jclass myClass, cls;
	jfieldID fld, fld2, fld3;
	jdouble retDbl;
	jfloat retFlt;
	jmethodID mid;
	jstring msg;
	jobject eobj;

	ret = 0;
	myClass = (*env)->GetObjectClass(env, obj);
	if (myClass == 0) return 1;
	
	// get fieldID
	fld = (*env)->GetFieldID(env, myClass, "myInt","I");
	if ((*env)->ExceptionOccurred(env)) {
		printf("Caught exception.\n");
		return 2;
	}
	// get field value
	ret =  (*env)->GetIntField(env, obj, fld);
	if ((*env)->ExceptionOccurred(env)) return 3;

	printf("Current value of myInt: %d\n", ret);
	ret = ival;

	//check that new value is within limits
	if (ival <0 || ival > 99) {
	   cls = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
	   if (cls == 0) return;
	   mid = (*env)->GetMethodID(env, cls, "<init>", "(Ljava/lang/String;)V");
	   if ((*env)->ExceptionOccurred(env)) return;
	   printf("Construct new string msg \n");
	   msg = (*env)->NewStringUTF(env, "Special error");
	   if ((*env)->ExceptionOccurred(env)) return;
	   printf("Construct new object.\n");
	   eobj = (*env)->NewObject(env, cls, mid, msg);
	   if ((*env)->ExceptionOccurred(env)) return;
	   (*env)->Throw(env, eobj);
	}
	   
 	(*env)->SetIntField(env, obj, fld, ret);
	if ((*env)->ExceptionOccurred(env)) return 4;
	printf("New value of myInt: %d\n", ret);	

	return(0);
}

