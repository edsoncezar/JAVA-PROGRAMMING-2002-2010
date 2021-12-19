#include <jni.h>
#include "IntArray.h"

JNIEXPORT jint JNICALL 
Java_IntArray_sumArray(JNIEnv *env, jobject obj, jintArray arr)
{
  jsize len = (*env)->GetArrayLength(env, arr);
  int i, sum = 0;
  jint *body = (*env)->GetIntArrayElements(env, arr, 0);
  for (i=0; i<len; i++) {
    sum += body[i];
  }
  (*env)->ReleaseIntArrayElements(env, arr, body, 0);
  return sum;
}
