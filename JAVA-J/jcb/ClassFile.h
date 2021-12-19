// ClassFile.h: interface for the CClassFile class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_CLASSFILE_H__4C9299C5_5406_11D4_BFE2_0020183A3E7E__INCLUDED_)
#define AFX_CLASSFILE_H__4C9299C5_5406_11D4_BFE2_0020183A3E7E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <vector>
#include <map>
#include "global.h"

typedef std::map<short, cstring> Int2StringMap;

#include "Field.h"
#include "ConstantValue.h"
#include "Method.h"

#include "javadef.h"

typedef std::vector<cstring> StringVector;
typedef std::map<cstring, CField *> String2FieldMap;
typedef std::map<cstring, CMethod *> String2MethodMap;
class CClassFile;
typedef std::vector<CClassFile *> ClassFileVector;

class CClassFile  
{

public:

	friend class CMember;

	CClassFile();
	CClassFile(cstring &aFileName);
	inline void setFileName(cstring &aFileName) { this->fileName = aFileName; }
	inline cstring getFileName(void) { return this->fileName; }
	inline int getMinorVersion(void) { return this->minorVersion; }
	inline int getMajorVersion(void) { return this->majorVersion; }
	inline int isPublic(void) {return (this->accessFlag & ACC_PUBLIC) == ACC_PUBLIC; }
	inline int isFinal(void) { return (this->accessFlag & ACC_FINAL) == ACC_FINAL; }
	inline int isSuper(void)  {return (this->accessFlag & ACC_SUPER) == ACC_SUPER; }
	inline int isInterface(void) { return (this->accessFlag & ACC_INTERFACE) == ACC_INTERFACE; }
	inline int isAbstract(void) { return (this->accessFlag & ACC_ABSTRACT) == ACC_ABSTRACT; }
	cstring getName(void);
	cstring getFullClassName(void);
	inline cstring getSuper(void) { return this->superClassName; }
	inline StringVector &getInterfaces(void) { return this->interfaces; }
	inline ClassFileVector &getInnerClasses(void) { return this->innerClasses; }
	void read(void);
	inline isJavaClassFile(void) { return this->javaClass; }
	virtual ~CClassFile();
	inline String2FieldMap& getFields(void) { return this->fields; }
	inline String2MethodMap& getMethods(void) { return this->methods; }
	inline bool isInnerClass(void) { return this->outerClass != NULL; }
	StringVector getPackages(void);

private:

	typedef std::map<short, CConstantValue *> Int2ConstantValueMap;

	static void readByte(std::ifstream &aInputStream, char *aByte, int size);
	cstring getName(short index);
	CConstantValue *getConstantValue(short aIndex);

	void ctor(void);

	cstring fileName;
	cstring name;
	cstring superClassName;
	bool javaClass;
	CClassFile *outerClass;

	Int2ConstantValueMap constantValues;
	Int2StringMap names;

	short minorVersion;
	short majorVersion;
	short accessFlag;

	StringVector interfaces;
	String2FieldMap fields;
	String2MethodMap methods;
	ClassFileVector innerClasses;
};

#endif // !defined(AFX_CLASSFILE_H__4C9299C5_5406_11D4_BFE2_0020183A3E7E__INCLUDED_)
