// Method.cpp: implementation of the CMethod class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Method.h"
#include "ClassFile.h"

#include <algorithm>

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CMethod::CMethod(CClassFile *paClassFile) : CMember(paClassFile)
{
	this->parametersResolved = false;
}

CMethod::~CMethod()
{

}

cstring CMethod::getType(void)
{
	cstring descriptor = getDescriptor();
	cstring result;

	int i = descriptor.find(_T(')'));
	if ( i != cstring::npos )
	{
		cstring::iterator it = descriptor.begin();
		it += i + 1;
		result = CMember::resolveType(it);
	}

	return result;
}

StringVector &CMethod::getParameters(void)
{
	if ( ! parametersResolved )
	{
		cstring descriptor = getDescriptor();
		int i = descriptor.find(_T('(')) + 1;
		cstring::iterator it = descriptor.begin() + i;
		while (*it != _T(')'))
		{
			cstring parameter = CMember::resolveType(it);
			int dimension = std::count(parameter.begin(), parameter.end(), _T('['));
			this->parameters.push_back(parameter);
			if ( *(it + dimension) == _T('L') )
			{
				i = descriptor.find(_T(';'), i + 1);
				it = descriptor.begin() + i;
			}
			it++;
		}
	}
	return this->parameters;
}