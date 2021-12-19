#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "CubbyHole.h"

JNIEXPORT jint JNICALL Java_CubbyHole_get(JNIEnv *env, jobject obj) {

   jint contents;
   jint ret, available;
   jref objref;
   jclass cubbyclass, langclass, exceptclass;
   jmethodID notifyid, waitid, exceptid;
   jfieldID availid, contentid;
   jobject exceptobj;

   cubbyclass = (*env)->GetObjectClass(env, obj);
   objref = cubbyclass;
   langclass = (*env)->FindClass(env, "java/lang/Object");
   notifyid = (*env)->GetMethodID(env, langclass, "notify", "()V");
   waitid = (*env)->GetMethodID(env, langclass, "wait", "()V");

   availid = (*env)->GetFieldID(env, cubbyclass, "available", "I");
   ret = (*env)->MonitorEnter(env, obj);	
   available = (*env)->GetIntField(env, obj, availid);
   while (available == 0) {
      (*env)->CallVoidMethod(env, obj, waitid);
      available = (*env)->GetIntField(env, obj, availid);
   }
   (*env)->SetIntField(env, obj, availid, 0);
   contentid = (*env)->GetFieldID(env, cubbyclass, "contents", "I");
   contents = (*env)->GetIntField(env, obj, contentid);
   (*env)->CallVoidMethod(env, obj, notifyid);
    
   ret = (*env)->MonitorExit(env, obj);
   return (contents);
}

