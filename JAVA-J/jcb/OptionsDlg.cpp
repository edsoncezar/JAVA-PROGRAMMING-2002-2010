// OptionsDlg.cpp: implementation of the COptionsDlg class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "resource.h"
#include "global.h"
#include "classbrowser.h"
#include "OptionsDlg.h"
#include "Registry.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
cstring COptionsDlg::showPrivateFields = COptionsDlg::loadRegistry(_T("ShowPrivateFields"), _T("Y"));
cstring COptionsDlg::showPrivateMethods = COptionsDlg::loadRegistry(_T("ShowPrivateMethods"), _T("Y"));
cstring COptionsDlg::showProtectedFields = COptionsDlg::loadRegistry(_T("ShowProtectedFields"), _T("Y"));
cstring COptionsDlg::showProtectedMethods = COptionsDlg::loadRegistry(_T("ShowProtectedMethods"), _T("Y"));

cstring COptionsDlg::loadRegistry(const cstring &aKey, const cstring &aDefaultValue)
{
	CRegistryKey key(HKEY_CURRENT_USER, _T("Software\\S.A.W.\\JCB\\Options"), true);

	CRegistryKeyValue<cstring> keyValue(key, aKey);
	keyValue.read();

	cstring value = keyValue.getValue();
	if ( value.length() == 0 )
		value = aDefaultValue;

	return value;
}

void COptionsDlg::saveRegistry(const cstring &aKey, const cstring &newValue)
{
	CRegistryKey key(HKEY_CURRENT_USER, _T("Software\\S.A.W.\\JCB\\Options"), true);
	CRegistryKeyValue<cstring> keyValue(key, aKey);

	keyValue = newValue;
	keyValue.save();
}
