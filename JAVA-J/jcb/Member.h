// Member.h: interface for the CMember class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MEMBER_H__55BE4301_62FC_11D4_BFE2_BFBE2DD23E4D__INCLUDED_)
#define AFX_MEMBER_H__55BE4301_62FC_11D4_BFE2_BFBE2DD23E4D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "javadef.h"
#include "global.h"
class CClassFile;

class CMember  
{
public:

	friend class CClassFile;

	CMember(CClassFile *paClassFile);
	virtual ~CMember();

	cstring getName(void);
	bool isFinal(void)     { return (accessFlag & ACC_FINAL)     == ACC_FINAL; }
	bool isStatic(void)    { return (accessFlag & ACC_STATIC)    == ACC_STATIC; }
	bool isPublic(void)    { return (accessFlag & ACC_PUBLIC)    == ACC_PUBLIC; }
	bool isPrivate(void)   { return (accessFlag & ACC_PRIVATE)   == ACC_PRIVATE; }
	bool isProtected(void) { return (accessFlag & ACC_PROTECTED) == ACC_PROTECTED; }
	ACCESSFLAG getAccessFlag(void);
	virtual cstring getType(void) = 0;

protected:

	CClassFile *pClassFile;
	short accessFlag;
	short nameIndex;
	short descriptorIndex;
	bool deprecated;
	bool synthetic;

	static cstring resolveType(cstring aType);
	cstring getDescriptor(void); 
};

#endif // !defined(AFX_MEMBER_H__55BE4301_62FC_11D4_BFE2_BFBE2DD23E4D__INCLUDED_)
