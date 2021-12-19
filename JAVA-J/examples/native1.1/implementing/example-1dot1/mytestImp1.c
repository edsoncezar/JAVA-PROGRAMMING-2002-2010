#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MyTest.h"

JNIEXPORT jint JNICALL Java_MyTest_createString(JNIEnv *env, jobject obj, jstring string, jint alen) {
  jstring jstr;
  jint ret;
  jsize slen;
  char *str;

  ret = 0;
  printf("Starting string create \n");

  jstr = (*env)->NewStringUTF(env, "newstring");
  if ((*env)->ExceptionOccurred(env)) return;

  str = (char *)(*env)->GetStringUTFChars(env, jstr, 0);
  (*env)->ReleaseStringUTFChars(env, jstr, str);

  return(ret);
}

int myCalc(int i, int j) {
  int k;

  for (k=1; k<13;k++) {
       i = i + (j*k);
  }
  return(i);
}
