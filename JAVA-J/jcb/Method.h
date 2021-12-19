// Method.h: interface for the CMethod class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_METHOD_H__95289401_622A_11D4_BFE2_A7910381A44F__INCLUDED_)
#define AFX_METHOD_H__95289401_622A_11D4_BFE2_A7910381A44F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <vector>
#include "global.h"
#include "javadef.h"
#include "Member.h"

class CClassFile;
typedef std::vector<cstring> StringVector;

class CMethod : public CMember
{
public:

	friend class CClassFile;

	virtual ~CMethod();

	bool isSynchronized(void) { return (accessFlag & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED; }
	bool isNative(void)       { return (accessFlag & ACC_NATIVE) == ACC_NATIVE; }
	bool isAbstract(void)     { return (accessFlag & ACC_ABSTRACT) == ACC_ABSTRACT; }
	bool isStrict(void)       { return (accessFlag & ACC_STRICT) == ACC_STRICT; }
	cstring getType(void);
	StringVector &getParameters(void);
	StringVector &getExceptions(void) { return exceptions; }

private:

	CMethod(CClassFile *paClassFile);
	StringVector exceptions;
	StringVector parameters;
	bool parametersResolved;
};

#endif // !defined(AFX_METHOD_H__95289401_622A_11D4_BFE2_A7910381A44F__INCLUDED_)
