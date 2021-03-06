#include <stdio.h>
#include <jni.h>
#include "Prompt.h"

JNIEXPORT jstring JNICALL 
Java_Prompt_getLine(JNIEnv *env, jobject obj, jstring prompt)
{
  char buf[128];
  const char *str = (*env)->GetStringUTFChars(env, prompt, 0);
  printf("%s", str);
  (*env)->ReleaseStringUTFChars(env, prompt, str);
  scanf("%s", buf);
  return (*env)->NewStringUTF(env, buf);
}
