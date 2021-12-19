// Member.cpp: implementation of the CMember class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Member.h"
#include "ClassFile.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CMember::CMember(CClassFile *paClassFile)
{
	this->pClassFile = paClassFile;
	this->accessFlag = 0;
	this->descriptorIndex = 0;
	this->nameIndex = 0;
	this->deprecated = false;
	this->synthetic = false;
}

CMember::~CMember()
{
}

cstring CMember::getName(void)
{
	return this->pClassFile->getName(this->nameIndex);
}

ACCESSFLAG CMember::getAccessFlag(void)
{
	if ( (this->accessFlag & ACC_PUBLIC) == ACC_PUBLIC )
		return PUBLIC;
	else if ( (this->accessFlag & ACC_PROTECTED) == ACC_PROTECTED )
		return PROTECTED;
	else
		return PRIVATE;
}

cstring CMember::resolveType(cstring aType)
{
	cstring result;

	int dimension = 0;
	int i = 0;

	cstring::iterator it = aType.begin();

	for(; it != aType.end() && *it == _T('['); it++)
		dimension++;

	switch(*it)
	{
	case _T('B'):
		result = _T("byte");
		break;
	case _T('C'):
		result = _T("char");
		break;
	case _T('D'):
		result = _T("double");
		break;
	case _T('F'):
		result = _T("float");
		break;
	case _T('I'):
		result = _T("int");
		break;
	case _T('J'):
		result = _T("long");
		break;
	case _T('S'):
		result = _T("short");
		break;
	case _T('Z'):
		result = _T("boolean");
		break;
	case _T('L'):
		{
			int pos = aType.find(_T(';'));
			result = aType.substr(dimension + 1, pos - 1 - dimension);
			replaceChar(result, _T('/'), _T('.'));
			break;
		}
	case _T('V'):
		result = _T("void");
		break;
	}

	for(i = 0; i < dimension; i++)
		result += _T("[]");

	return result;
}

cstring CMember::getDescriptor(void)
{
	return this->pClassFile->getName(this->descriptorIndex); 
}