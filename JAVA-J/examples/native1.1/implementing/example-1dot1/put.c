#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "CubbyHole.h"

JNIEXPORT void JNICALL Java_CubbyHole_put(JNIEnv *env, jobject obj,
			jint value) {

   jint contents;
   jint available;
   jint ret;
   jref objref;
   jclass cubbyclass, langclass, exceptclass;
   jmethodID notifyid, waitid, exceptid;
   jobject exceptobj;
   jfieldID availid, contentid;

   langclass = (*env)->FindClass(env, "java/lang/Object");
   notifyid = (*env)->GetMethodID(env, langclass, "notify", "()V");
   waitid = (*env)->GetMethodID(env, langclass, "wait", "()V");

   cubbyclass = (*env)->GetObjectClass(env, obj);
   availid = (*env)->GetFieldID(env, cubbyclass, "available", "I");
   objref = cubbyclass;
   available = (*env)->GetIntField(env, obj, availid);
   while (available == 1) {
      (*env)->CallVoidMethod(env, obj, waitid);
      available = (*env)->GetIntField(env, obj, availid);
   }
   (*env)->SetIntField(env, obj, availid, 1);
   contentid = (*env)->GetFieldID(env, cubbyclass, "contents", "I");
   (*env)->SetIntField(env, obj, contentid, value);
   (*env)->CallVoidMethod(env, obj, notifyid);
}

