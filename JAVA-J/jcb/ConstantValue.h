// ConstantValue.h: interface for the CConstantValue class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CONSTANTVALUE_H__16514CC1_5592_11D4_BFE2_0020183A3E7E__INCLUDED_)
#define AFX_CONSTANTVALUE_H__16514CC1_5592_11D4_BFE2_0020183A3E7E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "global.h"
#include "javadef.h"

class CConstantValue
{
public:
	CConstantValue(long aValue);
	CConstantValue(int aValue);
	CConstantValue(float aValue);
	CConstantValue(double aValue);
	CConstantValue(cstring aValue);
	virtual ~CConstantValue();

	cstring toString(void);

private:

	short type;
	union CValue
	{
		long longValue;
		int integerValue;
		float floatValue;
		double doubleValue;
		TCHAR *stringValue;
	};

	CValue value;
};

#endif // !defined(AFX_CONSTANTVALUE_H__16514CC1_5592_11D4_BFE2_0020183A3E7E__INCLUDED_)
