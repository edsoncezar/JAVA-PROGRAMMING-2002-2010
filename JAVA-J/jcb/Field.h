// Field.h: interface for the CField class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_FIELD_H__67783106_541A_11D4_BFE2_0020183A3E7E__INCLUDED_)
#define AFX_FIELD_H__67783106_541A_11D4_BFE2_0020183A3E7E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "javadef.h"
#include "Member.h"

class CClassFile;
class CConstantValue;

class CField : public CMember
{
public:

	friend class CClassFile;

	virtual ~CField();

	bool isVolatile(void)  { return (this->accessFlag & ACC_VOLATILE)  == ACC_VOLATILE; }
	bool isTransient(void) { return (this->accessFlag & ACC_TRANSIENT) == ACC_TRANSIENT; }
	CConstantValue *getConstantValue() { return this->constantValue; }
	cstring getType(void);

private:

	CField(CClassFile *paClassFile);
	CConstantValue *constantValue; // Pointer to a constantValue, kept by CClassFile
};

#endif // !defined(AFX_FIELD_H__67783106_541A_11D4_BFE2_0020183A3E7E__INCLUDED_)
