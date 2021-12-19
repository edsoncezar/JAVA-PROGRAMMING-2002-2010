#include <jni.h>
#include "CatchThrow.h"

JNIEXPORT void JNICALL 
Java_CatchThrow_catchThrow(JNIEnv *env, jobject obj)
{
  jclass cls = (*env)->GetObjectClass(env, obj);
  jmethodID mid = (*env)->GetMethodID(env, cls, "callback", "()V");
  jthrowable exc;
  if (mid == 0) {
    return;
  }
  (*env)->CallVoidMethod(env, obj, mid);
  exc = (*env)->ExceptionOccurred(env);
  if (exc) {
    /* We don't do much with the exception, except that we print a
       debug message using ExceptionDescribe, clear it, and throw
       a new exception. */
    jclass newExcCls;

    (*env)->ExceptionDescribe(env);
    (*env)->ExceptionClear(env);

    newExcCls = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
    if (newExcCls == 0) { /* Unable to find the new exception class, give up. */
      return;
    }
    (*env)->ThrowNew(env, newExcCls, "thrown from C code");
  }
}
