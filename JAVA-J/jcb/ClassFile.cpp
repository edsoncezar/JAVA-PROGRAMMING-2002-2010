// ClassFile.cpp: implementation of the CClassFile class.
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "ClassFile.h"

#include <fstream>
#include <math.h>
#include <float.h>

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

union DOUBLE_CONVERT
{
	double dValue;
	struct
	{ 
		long lowBytes;
		long highBytes;
	} lValue;
};

union FLOAT_CONVERT
{
	float fValue;
	long lValue;
};

struct ConstantString
{
	short index;
	short stringIndex;
};
typedef std::vector<ConstantString *> ConstantStringVector;

CClassFile::CClassFile()
{
	ctor();
}

CClassFile::CClassFile(cstring &aFileName)
{
	setFileName(aFileName);
	ctor();
}

CClassFile::~CClassFile()
{
	for(String2FieldMap::iterator it = this->fields.begin(); it != this->fields.end(); it++)
	{
		delete it->second;
	}
	this->fields.clear();

	for(Int2ConstantValueMap::iterator it2 = this->constantValues.begin(); it2 != this->constantValues.end(); it2++)
	{
		delete it2->second;
	}
	this->constantValues.clear();

	this->interfaces.clear();

	for(ClassFileVector::iterator it3 = this->innerClasses.begin(); it3 != this->innerClasses.end(); it3++)
	{
		delete *it3;
	}
	this->innerClasses.clear();
}

void CClassFile::ctor(void)
{
	this->javaClass = false;
	this->accessFlag = 0;
	this->outerClass = NULL;
}

void CClassFile::read(void)
{
	typedef std::map<int, int> Int2IntMap;
	Int2IntMap classes;

	std::ifstream javaClassFile;
	ConstantStringVector constantStrings;

	javaClassFile.open(this->fileName.c_str(), std::ios_base::in | std::ios_base::binary);
	if ( javaClassFile.is_open() )
	{
		long magic;
		readByte(javaClassFile, (char *) &magic, sizeof(magic));
		if ( magic == CLASSFILE_MAGIC )
		{
			this->javaClass = true;

			readByte(javaClassFile, (char *) &this->minorVersion, sizeof(this->minorVersion));
			readByte(javaClassFile, (char *) &this->majorVersion, sizeof(this->majorVersion));

			short constantPoolSize = 0;
			readByte(javaClassFile, (char *) &constantPoolSize, sizeof(constantPoolSize));

			// Read the constant pool
			char constantPoolTag;

			for(short i = 1; i < constantPoolSize; i++)
			{
				short constantValueIndex = i; // Store the index, because i could change for long, double values

				javaClassFile.read(&constantPoolTag, 1);

				CConstantValue *pconstantValue = NULL;

				switch(constantPoolTag)
				{
				case CONSTANT_Class:
					{
						short classNameIndex;
						readByte(javaClassFile, (char *) &classNameIndex, sizeof(classNameIndex));
						classes.insert(Int2IntMap::value_type(i, classNameIndex));
					}
					break;
				case CONSTANT_Fieldref:
				case CONSTANT_Methodref:
				case CONSTANT_InterfaceMethodref:
					{
						short classIndex;
						short nameTypeIndex;
						readByte(javaClassFile, (char *) &classIndex, sizeof(classIndex));
						readByte(javaClassFile, (char *) &nameTypeIndex, sizeof(nameTypeIndex));
					}
					break;
				case CONSTANT_String:
					{
						ConstantString *pconstantString = new ConstantString;
						pconstantString->index = constantValueIndex;
						readByte(javaClassFile, (char *) &(pconstantString->stringIndex), sizeof(pconstantString->stringIndex));
						constantStrings.push_back(pconstantString);
					}
					break;
				case CONSTANT_Integer:
				case CONSTANT_Float:
					{
						FLOAT_CONVERT integerOrFloat;
						readByte(javaClassFile, (char *) &integerOrFloat.lValue, sizeof(integerOrFloat.lValue));

						pconstantValue = ( constantPoolTag == CONSTANT_Integer ) ? new CConstantValue((int) integerOrFloat.lValue)
							                                                     : new CConstantValue(integerOrFloat.fValue);
					}
					break;
				case CONSTANT_Long:
				case CONSTANT_Double:
					{
						long highBytes;
						long lowBytes;
						readByte(javaClassFile, (char *) &highBytes, sizeof(highBytes));
						readByte(javaClassFile, (char *) &lowBytes, sizeof(lowBytes));

						if ( constantPoolTag == CONSTANT_Long )
						{
							long constant = (highBytes << 32) + lowBytes;
							pconstantValue = new CConstantValue(constant);
						}
						else
						{
							DOUBLE_CONVERT r;
							r.lValue.highBytes = highBytes;
							r.lValue.lowBytes = lowBytes;
							pconstantValue = new CConstantValue(r.dValue);
						}
						// These take two spots in the connection pool;
						i++;
					}
					break;
				case CONSTANT_NameAndType:
					{
						short nameIndex;
						short descriptorIndex;
						readByte(javaClassFile, (char *) &nameIndex, sizeof(nameIndex));
						readByte(javaClassFile, (char *) &descriptorIndex, sizeof(descriptorIndex));
					}
					break;
				case CONSTANT_Utf8:
					{
						short length;
						readByte(javaClassFile, (char *) &length, sizeof(length));
						char *str = new char[length + 1];
						javaClassFile.read(str, length);
						str[length] = '\0';
						names.insert(Int2StringMap::value_type(i, cstring(str)));
						delete str;
					}
					break;
				}

				// When we've read a constant value, add it to the map
				if ( pconstantValue != NULL )
					constantValues.insert(Int2ConstantValueMap::value_type(constantValueIndex, pconstantValue));
			}

			// Resolve string constants
			for(ConstantStringVector::iterator itIndex = constantStrings.begin(); itIndex != constantStrings.end(); itIndex++)
			{
				cstring constantString = getName((*itIndex)->stringIndex);
				constantValues.insert(Int2ConstantValueMap::value_type((*itIndex)->index, new CConstantValue(constantString.c_str())));
				delete *itIndex;
			}
			constantStrings.clear();

			// AccessFlag
			readByte(javaClassFile, (char *)&this->accessFlag, sizeof(this->accessFlag));
			
			// ThisClass
			short thisClass;
			readByte(javaClassFile, (char *)&thisClass, sizeof(thisClass));
			Int2IntMap::iterator itClass = classes.find(thisClass);
			if ( itClass != classes.end() )
			{
				this->name = getName(itClass->second);
				replaceChar(this->name, _T('/'), _T('.'));
			}

			// SuperClass
			short superClass;
			readByte(javaClassFile, (char *)&superClass, sizeof(superClass));
			itClass = classes.find(superClass);
			if ( itClass != classes.end() )
			{
				this->superClassName = getName(itClass->second);
				replaceChar(this->superClassName, _T('/'), _T('.'));
			}

			// Interfaces
			short interfaceCount;
			readByte(javaClassFile, (char *)&interfaceCount, sizeof(interfaceCount));
			for(i = 0; i < interfaceCount; i++)
			{
				short interfaceIndex;
				readByte(javaClassFile, (char *)&interfaceIndex, sizeof(interfaceIndex));

				cstring interfaceName;
				itClass = classes.find(interfaceIndex);
				if ( itClass != classes.end() )
				{
					cstring interfaceName = getName(itClass->second);
					replaceChar(interfaceName, _T('/'), _T('.'));
					interfaces.push_back(interfaceName);
				}
			}

			// Fields
			short fieldsCount;
			readByte(javaClassFile, (char *)&fieldsCount, sizeof(fieldsCount));

			for(i = 0; i < fieldsCount; i++)
			{
				CField *field = new CField(this);

				readByte(javaClassFile, (char *)&field->accessFlag, sizeof(field->accessFlag));
				readByte(javaClassFile, (char *)&field->nameIndex, sizeof(field->nameIndex));
				readByte(javaClassFile, (char *)&field->descriptorIndex, sizeof(field->descriptorIndex));

				short attributeCount;
				readByte(javaClassFile, (char *)&attributeCount, sizeof(attributeCount));

				for(int j = 0; j < attributeCount; j++)
				{
					short attributeNameIndex;
					long attributeLength;

					readByte(javaClassFile, (char *)&attributeNameIndex, sizeof(attributeNameIndex));
					readByte(javaClassFile, (char *)&attributeLength, sizeof(attributeLength));
					cstring attributeName = getName(attributeNameIndex);
					if ( attributeName == "ConstantValue" )
					{
						short valueIndex;
						readByte(javaClassFile, (char *)&valueIndex, sizeof(valueIndex));
						Int2ConstantValueMap::iterator itConstant = this->constantValues.find(valueIndex);
						if ( itConstant != this->constantValues.end() )
							field->constantValue = itConstant->second;
					}
					else if ( attributeName == "Synthetic" )
					{
						field->synthetic = true;
					}
					else if ( attributeName == "Deprecated" )
					{
						field->deprecated = true;
					}
				}
				this->fields.insert(String2FieldMap::value_type(field->getName(), field));
			}
	
			// Methods
			short methodCount;
			readByte(javaClassFile, (char *)&methodCount, sizeof(methodCount));
			for(i = 0; i < methodCount; i++)
			{
				CMethod *method = new CMethod(this);

				readByte(javaClassFile, (char *)&method->accessFlag, sizeof(method->accessFlag));
				readByte(javaClassFile, (char *)&method->nameIndex, sizeof(method->nameIndex));
				readByte(javaClassFile, (char *)&method->descriptorIndex, sizeof(method->descriptorIndex));

				short attributeCount;
				readByte(javaClassFile, (char *)&attributeCount, sizeof(attributeCount));
				for(int j = 0; j < attributeCount; j++)
				{
					short attributeNameIndex;
					long attributeLength;

					readByte(javaClassFile, (char *)&attributeNameIndex, sizeof(attributeNameIndex));
					readByte(javaClassFile, (char *)&attributeLength, sizeof(attributeLength));
					cstring attributeName = getName(attributeNameIndex);

					if ( attributeName == "Synthetic" )
					{
						method->synthetic = true;
					}
					else if ( attributeName == "Deprecated" )
					{
						method->deprecated = true;
					}
					else if ( attributeName == "Code" )
					{
						// Skip code
						javaClassFile.seekg(attributeLength, std::ios::cur);
					}
					else if ( attributeName == "Exceptions" )
					{
						short exceptionCount;
						readByte(javaClassFile, (char *)&exceptionCount, sizeof(exceptionCount));
						for(short k = 0; k < exceptionCount; k++)
						{
							short exceptionIndex;
							readByte(javaClassFile, (char *)&exceptionIndex, sizeof(exceptionIndex));
							Int2IntMap::iterator itClasses = classes.find(exceptionIndex);
							if ( itClasses != classes.end() )
							{
								cstring exception = getName(itClasses->second);
								for(int m = exception.find(_T('/')); m != cstring::npos; m = exception.find(_T('/'), ++m))
									exception[m] = _T('.');
								method->exceptions.push_back(exception);
							}
						}
					}
				}

				this->methods.insert(String2MethodMap::value_type(method->getName(), method));
			}

			short attributeCount;
			readByte(javaClassFile, (char *)&attributeCount, sizeof(attributeCount));
			for(int j = 0; j < attributeCount; j++)
			{
				short attributeNameIndex;
				long attributeLength;

				readByte(javaClassFile, (char *)&attributeNameIndex, sizeof(attributeNameIndex));
				readByte(javaClassFile, (char *)&attributeLength, sizeof(attributeLength));
				cstring attributeName = getName(attributeNameIndex);

				if ( attributeName == "SourceFile" )
				{
					short sourceFileIndex;
					readByte(javaClassFile, (char *)&sourceFileIndex, sizeof(sourceFileIndex));

					cstring sourceFileName = getName(sourceFileIndex);
				}
				else if ( attributeName == "Deprecated" )
				{
				}
				else if ( attributeName == "Synthetic" )
				{

				}
				else if ( attributeName == "InnerClasses" )
				{
					// Innerclasses
					short innerClassCount;
					int unnamedCount = 0;
					readByte(javaClassFile, (char *)&innerClassCount, sizeof(innerClassCount));
					for(int k = 0; k < innerClassCount; k++)
					{
						short innerClassInfoIndex;
						short outerClassInfoIndex;
						short innerNameIndex;
						short innerClassAccessFlags;

						readByte(javaClassFile, (char *)&innerClassInfoIndex, sizeof(innerClassInfoIndex));
						readByte(javaClassFile, (char *)&outerClassInfoIndex, sizeof(outerClassInfoIndex));
						readByte(javaClassFile, (char *)&innerNameIndex, sizeof(innerNameIndex));
						readByte(javaClassFile, (char *)&innerClassAccessFlags, sizeof(innerClassAccessFlags));
						
						if ( innerNameIndex > 0 )
						{
							int point = fileName.find(".");
							if ( point != cstring::npos )
							{
								cstring innerClassName = getName(innerNameIndex);
								if ( this->getName().compare(innerClassName) == 0 )
									continue;
								cstring innerClassFileName = fileName.substr(0, point) + "$" + getName(innerNameIndex) + ".class";
								CClassFile *pInnerClass = new CClassFile(innerClassFileName);
								pInnerClass->read();
								if ( pInnerClass->isJavaClassFile() )
								{
									pInnerClass->outerClass = this;
									innerClasses.push_back(pInnerClass);
								}
								else
									delete pInnerClass;
							}
						}
					}
				}
			}
		}

		classes.clear();
		javaClassFile.close();
	}
}

cstring CClassFile::getName(short aIndex)
{
	Int2StringMap::iterator it = names.find(aIndex);
	if ( it != names.end() )
		return it->second;
	else
		return "";
}

CConstantValue *CClassFile::getConstantValue(short aIndex)
{
	Int2ConstantValueMap::iterator it = constantValues.find(aIndex);
	if ( it != constantValues.end() )
		return it->second;
	else
		return NULL;
}

void CClassFile::readByte(std::ifstream &aInputStream, char *aByte, int size)
{
	aInputStream.read(aByte, size);
	reverseByte(aByte, size);
}

cstring CClassFile::getName(void)
{
	int pos = this->name.find_last_of(_T('$'));
	if ( pos == cstring::npos )
		pos = this->name.find_last_of(_T('.'));
	
	return ( pos != cstring::npos ) ? this->name.substr(pos + 1) : this->name;
}

cstring CClassFile::getFullClassName(void)
{
	return this->name;
}

StringVector CClassFile::getPackages(void)
{
	StringVector packages;
	if ( this->outerClass != NULL )
		return packages;
	else
	{
		int pos = this->name.find(_T("."));
		int prevPos = 0;
		while( pos != cstring::npos )
		{
			packages.push_back(this->name.substr(prevPos, pos - prevPos));
			prevPos = pos + 1;
			pos = this->name.find(_T("."), prevPos);
		}
	}
	return packages;
}
