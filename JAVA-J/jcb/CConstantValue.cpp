#include "stdafx.h"
#include "ConstantValue.h"

CConstantValue::CConstantValue(long aValue)
{
	this->type = CONSTANT_Long;
	this->value.longValue = aValue;
}
CConstantValue::CConstantValue(int aValue)
{
	this->type = CONSTANT_Integer;
	this->value.integerValue = aValue;
}
CConstantValue::CConstantValue(float aValue)
{
	this->type = CONSTANT_Float;
	this->value.floatValue = aValue;
}
CConstantValue::CConstantValue(double aValue)
{
	this->type = CONSTANT_Double;
	this->value.doubleValue = aValue;
}
CConstantValue::CConstantValue(cstring aValue)
{
	this->type = CONSTANT_String;
	this->value.stringValue = new TCHAR[aValue.length() + 1];
	_tcscpy(this->value.stringValue, aValue.c_str());
}
CConstantValue::~CConstantValue()
{
	if ( this->type == CONSTANT_String )
		delete this->value.stringValue;
}

cstring CConstantValue::toString(void)
{
	cstring result;
	switch(this->type)
	{
	case CONSTANT_Integer:
		{
			TCHAR *s = new TCHAR[10];
			_itot(this->value.integerValue, s, 10);
			result = s;
			delete s;
			break;
		}
	case CONSTANT_Long:
		{
			TCHAR *s = new TCHAR[10];
			_ltot(this->value.longValue, s, 10);
			result = s;
			delete s;
			break;
		}
	case CONSTANT_Float:
		{
			TCHAR *s = new TCHAR[25];
			_stprintf(s, "%f", this->value.floatValue);
			result = s;
			delete s;
			break;
		}
	case CONSTANT_Double:
		{
			TCHAR *s = new TCHAR[25];
			_stprintf(s, "%e", this->value.doubleValue);
			result = s;
			delete s;
			break;
		}
	case CONSTANT_String:
		result = _T('"');
		result += this->value.stringValue;
		result += _T('"');
		break;
	}
	return result;
}