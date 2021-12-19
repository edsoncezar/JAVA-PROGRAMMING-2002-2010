#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

jint Java_MyTest_getInfo(JNIEnv *env, jobject obj) {
	int retvalue;
	int i, j, num;
	jint ret;
	jclass myClass;
	jmethodID methID;
	jfieldID fld, fld2, fld3;
	jvalue args[2];
	jdouble retDbl;
	jfloat retFlt;

	ret = 0;
	i = 0;
	retvalue = (*env)->GetVersion(env);
	printf ("Version #: %d\n", retvalue);

	myClass = (*env)->GetObjectClass(env, obj);
	if (myClass == 0) return 1;
	
	// get fieldID
	fld = (*env)->GetFieldID(env, myClass, "myInt","I");
	if ((*env)->ExceptionOccurred(env)) return 2;
	fld2 = (*env)->GetFieldID(env, myClass, "myDbl", "D");
	if ((*env)->ExceptionOccurred(env)) return 2;
	fld3 = (*env)->GetFieldID(env, myClass, "myFlt", "F");
	if ((*env)->ExceptionOccurred(env)) return 2;
	// get field value
	ret =  (*env)->GetIntField(env, obj, fld);
	if ((*env)->ExceptionOccurred(env)) return 3;
	retDbl =  (*env)->GetDoubleField(env, obj, fld2);
	if ((*env)->ExceptionOccurred(env)) return 3;
	retFlt =  (*env)->GetFloatField(env, obj, fld3);
	if ((*env)->ExceptionOccurred(env)) return 3;

	methID = (*env)->GetMethodID(env, myClass, "displayHello", "()V");
	if (methID==0) return 4;

	// call displayHello
	(*env)->CallVoidMethod(env, obj, methID);

	// do some processing with myInt
	methID = (*env)->GetMethodID(env, myClass, "doCalc","(II)I");
	if (methID==0) return 5;
	// call doCalc Java routine
	num = 2;
	ret = (*env)->CallIntMethod(env, obj, methID, ret, num);

	// call doCalc return using callmethoda
	args[0].i = ret;
	args[1].i = num;
	ret = (*env)->CallIntMethodA(env, obj, methID, &args[0]);

	// call c function for further calculations
	ret = myCalc(ret, num);

	// set a new value in myInt
	(*env)->SetIntField(env, obj, fld, ret);
	if ((*env)->ExceptionOccurred(env)) return 6;
	retDbl = 5.689e9;
	(*env)->SetDoubleField(env, obj, fld2, retDbl);
	if ((*env)->ExceptionOccurred(env)) return 6;

	retFlt = 1.123;
	(*env)->SetFloatField(env, obj, fld3, retFlt);
	if ((*env)->ExceptionOccurred(env)) return 6;
	return(i);
}
