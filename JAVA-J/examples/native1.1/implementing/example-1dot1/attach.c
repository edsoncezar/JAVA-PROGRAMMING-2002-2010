/* Note: This program only works on Win32.
*/
#include <windows.h>
#include <jni.h>

JavaVM *jvm;

void thread_fun(void *arg)
{
    jint res;
    jclass cls;
    jmethodID mid;
    jstring jstr;
    jobjectArray args;
    JNIEnv *env;
    char buf[100];
    int threadNum = (int)arg;

    /* Pass NULL as the third argument */
    res = (*jvm)->AttachCurrentThread(jvm, &env, NULL);
    if (res < 0) {
       fprintf(stderr, "Thread %d: attach failed\n", threadNum);
       return;
    }

    cls = (*env)->FindClass(env, "Prog");
    if (cls == 0) {
        fprintf(stderr, "Thread %d: Can't find Prog class\n", threadNum);
        goto detach;
    }

    mid = (*env)->GetStaticMethodID(env, cls, "main", "([Ljava/lang/String;)V");
    if (mid == 0) {
        fprintf(stderr, "Thread %d: Can't find Prog.main\n", threadNum);
	goto detach;
    }

    sprintf(buf, " from Thread %d", threadNum);
    jstr = (*env)->NewStringUTF(env, buf);
    if (jstr == 0) {
        fprintf(stderr, "Thread %d: Out of memory\n", threadNum);
        goto detach;
    }
    args = (*env)->NewObjectArray(env, 1,
                        (*env)->FindClass(env, "java/lang/String"), jstr);
    if (args == 0) {
        fprintf(stderr, "Thread %d: Out of memory\n", threadNum);
        goto detach;
    }
    (*env)->CallStaticVoidMethod(env, cls, mid, args);

  detach:
    if ((*env)->ExceptionOccurred(env)) {
        (*env)->ExceptionDescribe(env);
    }
    (*jvm)->DetachCurrentThread(jvm);
}

#ifdef _WIN32
#define PATH_SEPARATOR ';'
#else /* UNIX */
#define PATH_SEPARATOR ':'
#endif

#define USER_CLASSPATH "." /* where Prog.class is */

main() {
    JNIEnv *env;
    JDK1_1InitArgs vm_args;
    int i;
    jint res;
    char classpath[1024];

    /* IMPORTANT: specify vm_args version # if you use JDK1.1.2 and beyond */
    vm_args.version = 0x00010001;

    JNI_GetDefaultJavaVMInitArgs(&vm_args);

    /* Append USER_CLASSPATH to the end of default system class path */
    sprintf(classpath, "%s%c%s",
            vm_args.classpath, PATH_SEPARATOR, USER_CLASSPATH);
    vm_args.classpath = classpath;

    /* Create the Java VM */
    res = JNI_CreateJavaVM(&jvm, &env, &vm_args);
    if (res < 0) {
        fprintf(stderr, "Can't create Java VM\n");
        exit(1);
    }

    for (i=0; i<5; i++)
        /* We pass the thread number as the argument to every thread */
        _beginthread(thread_fun,0,(void *)i);

    Sleep(5000); /* wait for threads to finish */

    (*jvm)->DestroyJavaVM(jvm);
}

