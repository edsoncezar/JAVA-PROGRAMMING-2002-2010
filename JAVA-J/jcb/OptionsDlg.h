// OptionsDlg.h: interface for the COptionsDlg class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OPTIONSDLG_H__FD5A3441_7D2B_11D4_BFE2_D7B3D589A940__INCLUDED_)
#define AFX_OPTIONSDLG_H__FD5A3441_7D2B_11D4_BFE2_D7B3D589A940__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class COptionsDlg : public CDialogImpl<COptionsDlg>  
{
public:
	COptionsDlg() {}
	
	enum { IDD = IDD_OPTION };

	BEGIN_MSG_MAP(COptionsDlg)
		MESSAGE_HANDLER(WM_INITDIALOG, OnInitDialog)
		COMMAND_ID_HANDLER(IDOK, OnOk)
		COMMAND_ID_HANDLER(IDCANCEL, OnCancel)
	END_MSG_MAP()

	LRESULT OnOk(WORD, WORD, HWND, BOOL &)
	{
		COptionsDlg::showPrivateFields = IsDlgButtonChecked(IDC_CHECK_PRIVATE_FIELDS) == BST_CHECKED ?
										 _T('Y') : _T('N');
		COptionsDlg::showPrivateMethods = IsDlgButtonChecked(IDC_CHECK_PRIVATE_METHODS) == BST_CHECKED ?
										 _T('Y') : _T('N');
		COptionsDlg::showProtectedFields = IsDlgButtonChecked(IDC_CHECK_PROTECTED_FIELDS) == BST_CHECKED ?
										 _T('Y') : _T('N');
		COptionsDlg::showProtectedMethods = IsDlgButtonChecked(IDC_CHECK_PROTECTED_METHODS) == BST_CHECKED ?
										 _T('Y') : _T('N');

		saveRegistry("ShowPrivateFields", COptionsDlg::showPrivateFields);
		saveRegistry("ShowPrivateMethods", COptionsDlg::showPrivateMethods);
		saveRegistry("ShowProtectedFields", COptionsDlg::showProtectedFields);
		saveRegistry("ShowProtectedMethods", COptionsDlg::showProtectedMethods);
		EndDialog(IDOK);
		return 0;
	}

	LRESULT OnCancel(WORD, WORD, HWND, BOOL &)
	{
		EndDialog(IDCANCEL);
		return 0;
	}

	LRESULT OnInitDialog(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
	{
		CheckDlgButton(IDC_CHECK_PRIVATE_FIELDS, COptionsDlg::isShowPrivateFields() ? BST_CHECKED : BST_UNCHECKED);
		CheckDlgButton(IDC_CHECK_PRIVATE_METHODS, COptionsDlg::isShowPrivateMethods() ? BST_CHECKED : BST_UNCHECKED);
		CheckDlgButton(IDC_CHECK_PROTECTED_FIELDS, COptionsDlg::isShowProtectedFields() ? BST_CHECKED : BST_UNCHECKED);
		CheckDlgButton(IDC_CHECK_PROTECTED_METHODS, COptionsDlg::isShowProtectedMethods() ? BST_CHECKED : BST_UNCHECKED);
		CenterWindow(GetParent());
		return 0;
	}

	static bool isShowPrivateFields() { return COptionsDlg::showPrivateFields.compare(_T("Y")) == 0; }
	static bool isShowPrivateMethods() { return COptionsDlg::showPrivateMethods.compare(_T("Y")) == 0; }
	static bool isShowProtectedFields() { return COptionsDlg::showProtectedFields.compare(_T("Y")) == 0; }
	static bool isShowProtectedMethods() { return COptionsDlg::showProtectedMethods.compare(_T("Y")) == 0; }

private:

	static cstring showPrivateFields;
	static cstring showPrivateMethods;
	static cstring showProtectedFields;
	static cstring showProtectedMethods;

	static cstring loadRegistry(const cstring &aKey, const cstring &aDefaultValue);
	void saveRegistry(const cstring &aKey, const cstring &newValue);

};

#endif // !defined(AFX_OPTIONSDLG_H__FD5A3441_7D2B_11D4_BFE2_D7B3D589A940__INCLUDED_)
