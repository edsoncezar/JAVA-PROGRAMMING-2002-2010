// MainFrm.h : interface of the CMainFrame class
//
// Author : Braem Franky
// Date   : 08-2000
// (c) 2000 You're free to use this code in non-commercial programs,
//          Commercial programs should add a remark in there documentation
//          that they use a program of me.
// e-mail : Franky.Braem17@yucom.be
// web    : www.braem17.yucom.be
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_MAINFRM_H__67ABDFEC_5114_11D4_BFE2_0020183A3E7E__INCLUDED_)
#define AFX_MAINFRM_H__67ABDFEC_5114_11D4_BFE2_0020183A3E7E__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

#include "atlmisc.h"
#include "ClassFile.h"
#include "OptionsDlg.h"

#define FILE_MENU_POSITION	0
#define RECENT_MENU_POSITION	2

class CMainFrame : public CFrameWindowImpl<CMainFrame>, public CUpdateUI<CMainFrame>,
		public CMessageFilter, public CIdleHandler
{
public:
	DECLARE_FRAME_WND_CLASS(NULL, IDR_MAINFRAME)

	CClassBrowserView m_view;
	CRecentDocumentList m_mru;
	CCommandBarCtrl m_CmdBar;

	~CMainFrame()
	{
		m_mru.WriteToRegistry(_T("Software\\S.A.W.\\JCB"));
	}

	virtual BOOL PreTranslateMessage(MSG* pMsg)
	{
		if(CFrameWindowImpl<CMainFrame>::PreTranslateMessage(pMsg))
			return TRUE;

		return m_view.PreTranslateMessage(pMsg);
	}

	virtual BOOL OnIdle()
	{
		UIUpdateToolBar();
		return FALSE;
	}

	BEGIN_MSG_MAP(CMainFrame)
		MESSAGE_HANDLER(WM_CREATE, OnCreate)
		COMMAND_ID_HANDLER(ID_APP_EXIT, OnFileExit)
		COMMAND_ID_HANDLER(ID_FILE_OPEN, OnFileOpen)
		COMMAND_ID_HANDLER(ID_VIEW_TOOLBAR, OnViewToolBar)
		COMMAND_ID_HANDLER(ID_VIEW_STATUS_BAR, OnViewStatusBar)
		COMMAND_ID_HANDLER(ID_VIEW_OPTIONS, OnViewOptions)
		COMMAND_ID_HANDLER(ID_APP_ABOUT, OnAppAbout)
		COMMAND_RANGE_HANDLER(ID_FILE_MRU_FIRST, ID_FILE_MRU_LAST, OnOpenUsingMRU)
		CHAIN_MSG_MAP(CUpdateUI<CMainFrame>)
		CHAIN_MSG_MAP(CFrameWindowImpl<CMainFrame>)
	END_MSG_MAP()

	BEGIN_UPDATE_UI_MAP(CMainFrame)
		UPDATE_ELEMENT(ID_VIEW_TOOLBAR, UPDUI_MENUPOPUP)
		UPDATE_ELEMENT(ID_VIEW_STATUS_BAR, UPDUI_MENUPOPUP)
	END_UPDATE_UI_MAP()

	LRESULT OnCreate(UINT /*uMsg*/, WPARAM /*wParam*/, LPARAM /*lParam*/, BOOL& /*bHandled*/)
	{
		HWND hWndCmdBar = m_CmdBar.Create(m_hWnd, rcDefault, NULL, ATL_SIMPLE_CMDBAR_PANE_STYLE);
		// atach menu
		m_CmdBar.AttachMenu(GetMenu());
		// load command bar images
		m_CmdBar.LoadImages(IDR_MAINFRAME);
		// remove old menu
		SetMenu(NULL);
		
		HWND hWndToolBar = CreateSimpleToolBarCtrl(m_hWnd, IDR_MAINFRAME, FALSE, ATL_SIMPLE_TOOLBAR_PANE_STYLE);

		CreateSimpleReBar(ATL_SIMPLE_REBAR_NOBORDER_STYLE);
		AddSimpleReBarBand(hWndCmdBar);
		AddSimpleReBarBand(hWndToolBar, NULL, TRUE);
		m_hWndClient = m_view.Create(m_hWnd, rcDefault, NULL, WS_CHILD | WS_VISIBLE | WS_CLIPSIBLINGS | WS_CLIPCHILDREN | TVS_HASLINES | TVS_LINESATROOT | TVS_SHOWSELALWAYS, WS_EX_CLIENTEDGE);

		UIAddToolBar(hWndToolBar);
		UISetCheck(ID_VIEW_TOOLBAR, 1);
		UISetCheck(ID_VIEW_STATUS_BAR, 1);

		CMenuHandle menu = m_CmdBar.GetMenu();
		CMenuHandle menuFile = menu.GetSubMenu(FILE_MENU_POSITION);
		CMenuHandle menuMru = menuFile.GetSubMenu(RECENT_MENU_POSITION);
		m_mru.SetMenuHandle(menuMru);
		m_mru.ReadFromRegistry(_T("Software\\S.A.W.\\JCB"));

		CreateSimpleStatusBar();

		CMessageLoop* pLoop = _Module.GetMessageLoop();
		pLoop->AddMessageFilter(this);
		pLoop->AddIdleHandler(this);

		return 0;
	}

	LRESULT OnFileExit(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
	{
		PostMessage(WM_CLOSE);
		return 0;
	}

	LRESULT OnFileOpen(WORD, WORD, HWND, BOOL &)
	{
		CFileDialog dlg(TRUE, _T("class"), NULL, OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,
					    _T("Java class files (*.class)\0*.class\0"), m_hWnd);
		if ( dlg.DoModal() == IDOK )
		{
			if ( openFile(cstring(dlg.m_szFileName)) )
			{
				m_mru.AddToList(dlg.m_szFileName);
			}
			else
			{
				MessageBox(_T("The selected file is not a java-classfile"));
			}
		}
		return 0;
	}

	LRESULT OnOpenUsingMRU(WORD, WORD wID, HWND, BOOL&) 
	{
		TCHAR document[MAX_PATH];
		m_mru.GetFromList(wID, document);
		
		if ( openFile(cstring(document)) )
		{
			m_mru.MoveToTop(wID);
		}
		else
		{
			m_mru.RemoveFromList(wID);
		}
		return 0;
	}

	LRESULT OnViewToolBar(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
	{
		static BOOL bNew = TRUE;	// initially visible
		bNew = !bNew;
		::SendMessage(m_hWndToolBar, RB_SHOWBAND, 0, bNew);	// toolbar is band #0
		UISetCheck(ID_VIEW_TOOLBAR, bNew);
		UpdateLayout();
		return 0;
	}

	LRESULT OnViewStatusBar(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
	{
		BOOL bNew = !::IsWindowVisible(m_hWndStatusBar);
		::ShowWindow(m_hWndStatusBar, bNew ? SW_SHOWNOACTIVATE : SW_HIDE);
		UISetCheck(ID_VIEW_STATUS_BAR, bNew);
		UpdateLayout();
		return 0;
	}

	LRESULT OnViewOptions(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
	{
		COptionsDlg dlg;

		if ( dlg.DoModal(this->m_hWnd) == IDOK )
		{
			m_view.refresh();
		}

		return 0;
	}

	LRESULT OnAppAbout(WORD /*wNotifyCode*/, WORD /*wID*/, HWND /*hWndCtl*/, BOOL& /*bHandled*/)
	{
		CAboutDlg dlg;
		dlg.DoModal();
		return 0;
	}

	bool openFile(cstring &aFileName)
	{
		CClassFile *pclassFile = new CClassFile();
		pclassFile->setFileName(cstring(aFileName));
		pclassFile->read();
	
		if ( pclassFile->isJavaClassFile() )
		{
			m_view.setClassFile(pclassFile);
			return true;
		}
		else
		{
			MessageBox(_T("The selected file is not a java-classfile"));
			return false;
		}
	}
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MAINFRM_H__67ABDFEC_5114_11D4_BFE2_0020183A3E7E__INCLUDED_)
