Imports Microsoft.Office.Core
Imports Extensibility
Imports System.Runtime.InteropServices
Imports EnvDTE
Imports System.IO

#Region " Read me for Add-in installation and setup information. "
' When run, the Add-in wizard prepared the registry for the Add-in.
' At a later time, if the Add-in becomes unavailable for reasons such as:
'   1) You moved this project to a computer other than which is was originally created on.
'   2) You chose 'Yes' when presented with a message asking if you wish to remove the Add-in.
'   3) Registry corruption.
' you will need to re-register the Add-in by building the VbNetHowTo_ExampleAddInSetup project 
' by right clicking the project in the Solution Explorer, then choosing install.
#End Region

<GuidAttribute("F48605ED-0D87-4B56-8900-3667735CC071"), ProgIdAttribute("VbNetHowTo_ExampleAddIn.Connect")> _
Public Class Connect

    Implements Extensibility.IDTExtensibility2
    Implements IDTCommandTarget

    Dim applicationObject As EnvDTE.DTE
    Dim addInInstance As EnvDTE.AddIn

    ' Declare variables for How-To.
    Private m_Events As EnvDTE.Events
    Private WithEvents m_SolutionEvents As EnvDTE.SolutionEvents
    Private WithEvents m_BuildEvents As EnvDTE.BuildEvents

    ' This subroutine is fired when a project or solution is built or rebuilt.
    Private Sub m_BuildEvents_OnBuildBegin(ByVal Scope As EnvDTE.vsBuildScope, ByVal Action As EnvDTE.vsBuildAction) Handles m_BuildEvents.OnBuildBegin
        WriteEvent("Build Beginning. Action: " + Action.ToString())
    End Sub

    ' This subroutine is fired when a solution is closing. It simply makes a
    '   log entry of the event.
    Private Sub m_SolutionEvents_AfterClosing() Handles m_SolutionEvents.AfterClosing
        WriteEvent("Solution Closed: " + applicationObject.Solution.FullName)
    End Sub

    ' This subroutine is fired when a solution is opened. It simply makes a
    '   log entry of the event.
    Private Sub m_SolutionEvents_Opened() Handles m_SolutionEvents.Opened
        WriteEvent("Solution Opened: " + applicationObject.Solution.FullName)
    End Sub

    ' This subroutine is fired when a project is added to a solution. 
    '   It simply makes a log entry of the event.
    Private Sub m_SolutionEvents_ProjectAdded(ByVal Project As EnvDTE.Project) Handles m_SolutionEvents.ProjectAdded
        WriteEvent("Project Added: " + Project.FullName)
    End Sub

    ' This subroutine is fired when a project is removed from a solution. 
    '   It simply makes a log entry of the event.
    Private Sub m_SolutionEvents_ProjectRemoved(ByVal Project As EnvDTE.Project) Handles m_SolutionEvents.ProjectRemoved
        WriteEvent("Project Removed: " + Project.FullName)
    End Sub

    ' This subroutine is fired when a project in a solution is renamed. 
    '   It simply makes a log entry of the event.
    Private Sub m_SolutionEvents_ProjectRenamed(ByVal Project As EnvDTE.Project, ByVal OldName As String) Handles m_SolutionEvents.ProjectRenamed
        WriteEvent("Project Renamed: " + Project.FullName + " from " + OldName)
    End Sub


    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub OnAddInsUpdate(ByRef custom As System.Array) Implements Extensibility.IDTExtensibility2.OnAddInsUpdate
    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub OnBeginShutdown(ByRef custom As System.Array) Implements Extensibility.IDTExtensibility2.OnBeginShutdown
    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub OnConnection(ByVal application As Object, ByVal connectMode As Extensibility.ext_ConnectMode, ByVal addInInst As Object, ByRef custom As System.Array) Implements Extensibility.IDTExtensibility2.OnConnection

        applicationObject = CType(application, EnvDTE.DTE)
        addInInstance = CType(addInInst, EnvDTE.AddIn)
        If connectMode = Extensibility.ext_ConnectMode.ext_cm_UISetup Then
            Dim objAddIn As AddIn = CType(addInInst, AddIn)
            Dim CommandObj As Command

            ' When run, the Add-in wizard prepared the registry for the Add-in.
            ' At a later time, the Add-in or its commands may become unavailable for reasons such as:
            '   1) You moved this project to a computer other than which is was originally created on.
            '   2) You chose 'Yes' when presented with a message asking if you wish to remove the Add-in.
            '   3) You add new commands or modify commands already defined.
            ' You will need to re-register the Add-in by building the VbNetHowTo_ExampleAddInSetup project,
            ' right-clicking the project in the Solution Explorer, and then choosing install.
            ' Alternatively, you could execute the ReCreateCommands.reg file the Add-in Wizard generated in
            ' the project directory, or run 'devenv /setup' from a command prompt.
            Try
                CommandObj = applicationObject.Commands.AddNamedCommand(objAddIn, "VbNetHowTo_ExampleAddIn", "VbNetHowTo_ExampleAddIn", "Executes the command for VbNetHowTo_ExampleAddIn", True, 59, Nothing, 1 + 2)  '1+2 == vsCommandStatusSupported+vsCommandStatusEnabled
                CommandObj.AddControl(applicationObject.CommandBars.Item("Tools"))
            Catch e As System.Exception
            End Try
        End If

        ' Add code for How-To.
        ' Initialize all the variables that contain the events we're looking for.
        ' Get the events object that contains information about all of the 
        '   extensibility events.
        m_Events = CType(application, _DTE).Events
        ' Initialize a variable to track SolutionEvents
        m_SolutionEvents = m_Events.SolutionEvents
        ' Initialize a variable to track BuildEvents
        m_BuildEvents = m_Events.BuildEvents

    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub OnDisconnection(ByVal RemoveMode As Extensibility.ext_DisconnectMode, ByRef custom As System.Array) Implements Extensibility.IDTExtensibility2.OnDisconnection
    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub OnStartupComplete(ByRef custom As System.Array) Implements Extensibility.IDTExtensibility2.OnStartupComplete
    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub Exec(ByVal cmdName As String, ByVal executeOption As vsCommandExecOption, ByRef varIn As Object, ByRef varOut As Object, ByRef handled As Boolean) Implements IDTCommandTarget.Exec
        handled = False
        If (executeOption = vsCommandExecOption.vsCommandExecOptionDoDefault) Then
            If cmdName = "VbNetHowTo_ExampleAddIn.Connect.VbNetHowTo_ExampleAddIn" Then
                handled = True
                Exit Sub
            End If
        End If
    End Sub

    ' Automatically generated by the Wizard. This subroutine is needed to 
    '   implement the Extensibility.IDTExtensibility2 interface.
    Public Sub QueryStatus(ByVal cmdName As String, ByVal neededText As vsCommandStatusTextWanted, ByRef statusOption As vsCommandStatus, ByRef commandText As Object) Implements IDTCommandTarget.QueryStatus
        If neededText = EnvDTE.vsCommandStatusTextWanted.vsCommandStatusTextWantedNone Then
            If cmdName = "VbNetHowTo_ExampleAddIn.Connect.VbNetHowTo_ExampleAddIn" Then
                statusOption = CType(vsCommandStatus.vsCommandStatusEnabled + vsCommandStatus.vsCommandStatusSupported, vsCommandStatus)
            Else
                statusOption = vsCommandStatus.vsCommandStatusUnsupported
            End If
        End If
    End Sub


    ' Code for How-To
    ' This subroutine writes events to a File in the System directory.  
    '   It connects and disconnects to the file as rapidly as possible, since       
    '   the file may be shared by many running instances of VS.NET
    Private Sub WriteEvent(ByVal message As String)
        Try
            ' Create a StramWriter.
            Dim mySW = New StreamWriter(Environment.SystemDirectory + _
                "\IdeLog.txt", True)
            ' Write the time to the output file.
            mySW.WriteLine(Now().ToShortDateString() + " - " + _
                Now().ToShortTimeString())
            ' Write the message and a carriage return to the file.
            mySW.WriteLine(message)
            mySW.writeline(vbCrLf)
            ' Flush the file and close it.
            mySW.Flush()
            mySW.Close()
        Catch ex As Exception
            ' Report the error to the user.
            MsgBox("The log could not be written to.", MsgBoxStyle.OKOnly, _
                "VB.NET How-To Create a VS.NET Add-in")
        End Try
    End Sub

End Class
