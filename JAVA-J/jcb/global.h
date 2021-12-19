#ifndef _GLOBAL_H
#define _GLOBAL_H

#include <string>
#ifdef _UNICODE
	#define cstring std::wstring
#else
	#define cstring std::string
#endif

void replaceChar(cstring &aString, TCHAR aReplace, TCHAR aChange);
void reverseByte(void *value, int size);

#endif