#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

jint Java_MyTest_handleError2(JNIEnv *env, jobject obj) {
	jint ret;
	jclass myClass;
	jfieldID fld;

	myClass = (*env)->GetObjectClass(env, obj);
	if (myClass == 0) return 1;
	// get fieldID
	fld = (*env)->GetFieldID(env, myClass, "myInt","Z");
	if ((*env)->ExceptionOccurred(env)) {
		printf("Caught mismatched type error.\n");
		printf("Clear the error.\n");
		(*env)->ExceptionClear(env);
		fld = (*env)->GetFieldID(env, myClass, "myInt","I");
		if ((*env)->ExceptionOccurred(env)) {
			(*env)->Throw(env, obj);
			return 9;
		}
	}
	// get field value
	ret =  (*env)->GetIntField(env, obj, fld);
	if ((*env)->ExceptionOccurred(env)) return 3;
	return(ret);
}
