// Field.cpp: implementation of the CField class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "Field.h"
#include "ClassFile.h"
#include "Member.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CField::CField(CClassFile *paClassFile) : CMember(paClassFile)
{
	this->constantValue = NULL;
}

CField::~CField()
{
}

cstring CField::getType(void)
{
	return CMember::resolveType(getDescriptor());
}
