// ClassBrowserView.h : interface of the CClassBrowserView class
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
//
// Update :	20-08-2000 Version 1.1
//					   - Added images to the list.
//					   - Added implemented interfaces.
//					   - Changed the order: now interfaces, methods, fields					
//					   - Added the superclass
//					   - remove the <init>() method from the view
//					   - Added a statusbar
//					   - Shows the name of innerclasses
//          03-09-2000 Version 1.2
//					   - Show fields, methods of the innerclasses
//					   - Shows the packagenames in the tree
//					   - Removed the fields with a $ in the name from the tree
//					   - Solved a bug in saving the mru_list 
//							(called in destructor of mainframe now instead of OnFileExit)
//					   - Added an optiondialog
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CLASSBROWSERVIEW_H__67ABDFEF_5114_11D4_BFE2_0020183A3E7E__INCLUDED_)
#define AFX_CLASSBROWSERVIEW_H__67ABDFEF_5114_11D4_BFE2_0020183A3E7E__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

#include "global.h"
#include "ClassFile.h"
#include "OptionsDlg.h"

#define IMG_CLASS				4
#define IMG_INTERFACE			5
#define IMG_PACKAGE				6
#define IMG_PUBLIC_METHOD	   13	
#define IMG_PROTECTED_METHOD   14
#define IMG_PRIVATE_METHOD     15 
#define IMG_PUBLIC_FIELD	   17
#define IMG_PROTECTED_FIELD    18
#define IMG_PRIVATE_FIELD	   19

class CClassBrowserView : public CWindowImpl<CClassBrowserView, CTreeViewCtrl>
{
public:

	CClassBrowserView() : CWindowImpl<CClassBrowserView, CTreeViewCtrl>()
	{
		this->pclassFile = NULL; 
	}

	virtual ~CClassBrowserView() { delete this->pclassFile; }
	DECLARE_WND_SUPERCLASS(NULL, CTreeViewCtrl::GetWndClassName())

	BOOL PreTranslateMessage(MSG* pMsg)
	{
		pMsg;
		return FALSE;
	}

	BEGIN_MSG_MAP(CClassBrowserView)
		MESSAGE_HANDLER(WM_CREATE, OnCreate)
	END_MSG_MAP()

	LRESULT OnCreate(UINT, WPARAM, LPARAM lParam, BOOL& bHandled)
	{
		DefWindowProc();
		ModifyStyle(0, TVS_HASBUTTONS | TVS_HASLINES | TVS_LINESATROOT, 0);
		treeImages.Create(IDB_TREE_IMAGES, 16, 1, RGB(0, 255, 0));
		SetImageList((HIMAGELIST) treeImages, TVSIL_NORMAL);
		return 0L;
	}

	void setClassFile(CClassFile *paClassFile)
	{
		if ( this->pclassFile )
		{
			delete this->pclassFile;
			this->pclassFile = NULL;
		}
		this->pclassFile = paClassFile; 
		DeleteAllItems();
		ShowClassFileInTree(NULL, this->pclassFile);
	}

	void refresh(void)
	{
		if ( this->pclassFile != NULL )
		{
			DeleteAllItems();
			ShowClassFileInTree(NULL, this->pclassFile);
		}
	}

	void ShowClassFileInTree(HTREEITEM aItemParent, CClassFile *pClassFile)
	{
		int imageNumber = 0;
		HTREEITEM itemHandle = NULL;
		cstring item;

		if ( aItemParent == NULL )
		{
			StringVector packages = pClassFile->getPackages();
			HTREEITEM searchItem = GetRootItem();
			for(StringVector::iterator itPackage = packages.begin(); itPackage != packages.end(); itPackage++)
			{
				while(searchItem != NULL)
				{
					GetItemText(searchItem, item);
					if ( item.compare(*itPackage) == 0 )
					{
						break;
					}
					else
					{
						searchItem = GetNextSiblingItem(searchItem);
						if ( searchItem == NULL )
						{
							break;
						}
					}
				}
				if ( searchItem == NULL )
				{
					aItemParent = InsertItem(itPackage->c_str(), IMG_PACKAGE, IMG_PACKAGE, aItemParent, NULL);
				}
				else
				{
					aItemParent = searchItem;
					searchItem = GetNextItem(searchItem, TVGN_CHILD);
				}
			}
		}

		Expand(GetRootItem());
		imageNumber = ( this->pclassFile->isInterface() ) ? IMG_INTERFACE : IMG_CLASS;
		item = pClassFile->getName();
		if ( pClassFile->isSuper() )
			item += _T(" extends ") + pClassFile->getSuper();
		HTREEITEM rootItem = InsertItem(item.c_str(), imageNumber, imageNumber, aItemParent, NULL);
		Expand(aItemParent);

		// Interfaces

		StringVector &interfaces = pClassFile->getInterfaces();
		for(StringVector::iterator itString = interfaces.begin(); itString != interfaces.end(); itString++)
		{
			item = *itString;
			InsertItem(item.c_str(), 5, 5, rootItem, NULL);
		}

		// Innerclasses
		ClassFileVector &innerClasses = pClassFile->getInnerClasses();
		for(ClassFileVector::iterator itClass = innerClasses.begin(); itClass != innerClasses.end(); itClass++)
		{
			ShowClassFileInTree(rootItem, (*itClass));
		}

		// Methods
		String2MethodMap &methods = pClassFile->getMethods();
		for(String2MethodMap::iterator it2 = methods.begin(); it2 != methods.end(); it2++)
		{
			if ( it2->second->getName().at(0) == _T('<') )
				continue;

			item = _T("");
			switch(it2->second->getAccessFlag() )
			{
			case PUBLIC :
				imageNumber = IMG_PUBLIC_METHOD;
				break;
			case PROTECTED:
				if ( ! COptionsDlg::isShowProtectedMethods() )
					continue;
				imageNumber = IMG_PROTECTED_METHOD;
				break;
			case PRIVATE :
				if ( ! COptionsDlg::isShowPrivateMethods() )
					continue;
				imageNumber = IMG_PRIVATE_METHOD;
				break;
			}

			if ( it2->second->isStatic() )
				item += _T("static ");

			if ( it2->second->isFinal() )
				item += _T("final ");

			item += it2->second->getType() + _T(' ') + it2->second->getName() + _T('('); 
			StringVector &parameters = it2->second->getParameters();
			for(StringVector::iterator it3 = parameters.begin(); it3 != parameters.end(); it3++)
			{
				item += *it3;
				if ( it3 != parameters.end() - 1 )
				{
					item += _T(", ");
				}
			}
			item += _T(')');
			StringVector &exceptions = it2->second->getExceptions();
			if ( exceptions.size() > 0 )
				item += _T(" throws ");
			for(it3 = exceptions.begin(); it3 != exceptions.end(); it3++)
			{
				item += *it3;
				if ( it3 != exceptions.end() - 1 )
				{
					item += _T(", ");
				}
			}
			
			InsertItem(item.c_str(), imageNumber, imageNumber, rootItem, NULL);
		}

		// Fields
		String2FieldMap &fields = pClassFile->getFields();
		for(String2FieldMap::iterator it = fields.begin(); it != fields.end(); it++)
		{
			cstring fieldName = it->second->getName();
			if ( fieldName.find(_T('$')) != cstring::npos )
				continue; // Don't show java system variables

			item = _T("");
			switch(it->second->getAccessFlag() )
			{
			case PUBLIC :
				imageNumber = IMG_PUBLIC_FIELD;
				break;
			case PROTECTED:
				if ( ! COptionsDlg::isShowProtectedFields() )
					continue;
				imageNumber = IMG_PROTECTED_FIELD;
				break;
			case PRIVATE :
				if ( ! COptionsDlg::isShowPrivateFields() )
					continue;
				imageNumber = IMG_PRIVATE_FIELD;
				break;
			}

			if ( it->second->isStatic() )
				item += _T("static ");

			if ( it->second->isFinal() )
				item += _T("final ");

			item += it->second->getType() + _T(' ') + fieldName;

			CConstantValue *pconstant = it->second->getConstantValue();
			if ( pconstant != NULL )
			{
				item += " = " + pconstant->toString();
			}

			InsertItem(item.c_str(), imageNumber, imageNumber, rootItem, NULL);
		}

		Expand(rootItem);
	}

	BOOL GetItemText(HTREEITEM hItem, cstring &strText) const
	{
		TVITEM item;
		item.hItem = hItem;
		item.mask = TVIF_TEXT;
		item.pszText = NULL;

		strText.empty();
		BOOL bRet = FALSE;
		for(int nLen = 256; ; nLen *= 2)
		{
			if ( item.pszText == NULL )
				delete item.pszText;
			item.pszText = new TCHAR[nLen + 1];
			item.cchTextMax = nLen;
			bRet = (BOOL)::SendMessage(m_hWnd, TVM_GETITEM, 0, (LPARAM)&item);
			if(!bRet || (lstrlen(item.pszText) < nLen - 1))
				break;
		}
		strText = item.pszText;
		delete item.pszText;
		return bRet;
	}

private:
	CClassFile *pclassFile;
	CImageList treeImages;
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CLASSBROWSERVIEW_H__67ABDFEF_5114_11D4_BFE2_0020183A3E7E__INCLUDED_)
