#include <stdio.h>
#include <jni.h>
#include "FieldAccess.h"

JNIEXPORT void JNICALL 
Java_FieldAccess_accessFields(JNIEnv *env, jobject obj)
{
  jclass cls = (*env)->GetObjectClass(env, obj);
  jfieldID fid;
  jstring jstr;
  const char *str;
  jint si;

  printf("In C:\n");

  fid = (*env)->GetStaticFieldID(env, cls, "si", "I");
  if (fid == 0) {
    return;
  }
  si = (*env)->GetStaticIntField(env, cls, fid);
  printf("  FieldAccess.si = %d\n", si);
  (*env)->SetStaticIntField(env, cls, fid, 200);
  
  fid = (*env)->GetFieldID(env, cls, "s", "Ljava/lang/String;");
  if (fid == 0) {
    return;
  }
  jstr = (*env)->GetObjectField(env, obj, fid);
  str = (*env)->GetStringUTFChars(env, jstr, 0);
  printf("  c.s = \"%s\"\n", str);
  (*env)->ReleaseStringUTFChars(env, jstr, str);

  jstr = (*env)->NewStringUTF(env, "123");
  (*env)->SetObjectField(env, obj, fid, jstr);
}

