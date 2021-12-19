#include <native.h>
#include "NativeExample.h"

static char *quotes[] = {
    "The truth is out there -- X-Files",
    "I suppose it will all make sense when we grow up -- Calvin & Hobbes",
    "Who died and made you king? -- my dad"
};

struct Hjava_lang_String *
NativeExample_quote(struct HNativeExample *unused, long index)
{
    char *quotation;

    if (index < 1 || index > 3) {
	SignalError(0, "java/lang/IllegalArgumentException", 0);
	return NULL;
    }

    quotation = quotes[index - 1];
    return makeJavaString(quotation, strlen(quotation));
}

long 
NativeExample_twoTimes(struct HNativeExample *hInst)
{
    return unhand(hInst)->myValue * 2;
}

struct HNativeExample *
NativeExample_doubleUp(struct HNativeExample *hInst)
{
    HNativeExample *hNewInst;
    long twoX;

    hNewInst = (HNativeExample *)execute_java_constructor(
	0, "NativeExample", 0, "(I)", unhand(hInst)->myValue);

    twoX = (long)execute_java_dynamic_method(
	0, (HObject *)hNewInst, "twoTimes", "()I");

    unhand(hNewInst)->myValue = twoX;
    return hNewInst;
}
