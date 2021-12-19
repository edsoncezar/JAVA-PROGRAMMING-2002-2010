#include "stdafx.h"
#include "global.h"

void replaceChar(cstring &aString, TCHAR aReplace, TCHAR aChange)
{
	for(int i  = aString.find(aReplace); i != cstring::npos; i = aString.find(aReplace, ++i))
		aString[i] = aChange;
}

void reverseByte(void *value, int size)
{
    unsigned char *p1 = (unsigned char *)value;
    unsigned char *p2 = p1 + size - 1;
    unsigned char temp;
    while (p2 > p1)
    {
        temp = *p1;
        *p1++ = *p2;
		*p2-- = temp;
    }
}
