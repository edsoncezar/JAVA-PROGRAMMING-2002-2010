<%
    #####################################################################
    # Microsoft ADO 
    #
    # ADO constants include file for PerlScript
    #
    # These are the ADO Constants that were converted from Microsoft's
    # ADOVBS Include file to work with PerlScript.
    #
    #####################################################################
    	
    #######################################	
    ## CursorTypeEnum Values 
    #######################################
    my $adOpenForwardOnly = 0;
    my $adOpenKeySet = 1;
    my $adOpenDynamic = 2;
    my $adOpenStatic = 3;
    	
    #######################################	
    ## CursorOptionEnum Values 
    #######################################
    my $adHoldRecords = '0x00000100';
    my $adMovePrevious = '0x00000200';
    my $adAddNew = '0x01000400';
    my $adDelete = '0x01000800';
    my $adUpdate = '0x01008000';
    my $adBookmark = '0x00002000';
    my $adApproxPosition = '0x00004000';
    my $adUpdateBatch = '0x00010000';
    my $adResync = '0x00020000';
    my $adNotify = '0x00040000';
    my $adFind = '0x00080000';
    my $adSeek = '0x00400000';
    my $adIndex = '0x00800000';
    	
    #######################################	
    ## LockTypeEnum Values 
    #######################################
    my $adLockReadOnly = 1;
    my $adLockPessimistic = 2;
    my $adLockOptimistic = 3;
    my $adLockBatchOptimistic = 4;
    	
    #######################################	
    ## ExecuteOptionEnum Values
    #######################################
    my $adRunAsync = '0x00000010';
    my $adAsyncExecute = '0x00000010';
    my $adAsyncFetch = '0x00000020';
    my $adAsyncFetchNonBlocking = '0x00000040';
    my $adExecuteNoRecords = '0x00000080';
    	
    #######################################	
    ## ConnectOptionEnum Values
    #######################################
    my $adAsyncConnect = '0x00000010';
    	
    #######################################	
    ## ObjectStateEnum Values 
    #######################################
    my $adStateClosed = '0x00000000';
    my $adStateOpen = '0x00000001';
    my $adStateConnecting = '0x00000002';
    my $adStateExecuting = '0x00000004';
    my $adStateFetching = '0x00000008';
    	
    #######################################	
    ## CursorLocationEnum Values
    #######################################
    my $adUseServer = 2;
    my $adUseClient = 3;
    	
    #######################################	
    ## DataTypeEnum Values
    #######################################
    my $adEmpty = 0;
    my $adTinyInt = 16;
    my $adSmallInt = 2;
    my $adInteger = 3;
    my $adBigInt = 20;
    my $adUnsignedTinyInt = 17;
    my $adUnsignedSmallInt = 18;
    my $adUnsignedInt = 19;
    my $adUnsignedBigInt = 21;
    my $adSingle = 4;
    my $adDouble = 5;
    my $adCurrency = 6;
    my $adDecimal = 14;
    my $adNumeric = 131;
    my $adBoolean = 11;
    my $adError = 10;
    my $adUserDefined = 132;
    my $adVariant = 12;
    my $adIDispatch = 9;
    my $adIUnknown = 13;
    my $adGUID = 72;
    my $adDate = 7;
    my $adDBDate = 133;
    my $adDBTime = 134;
    my $adDBTimeStamp = 135;
    my $adBSTR = 8;
    my $adChar = 129;
    my $adVarChar = 200;
    my $adLongVarChar = 201;
    my $adWChar = 130;
    my $adVarWChar = 202;
    my $adLongVarWChar = 203;
    my $adBinary = 128;
    my $adVarBinary = 204;
    my $adLongVarBinary = 205;
    my $adChapter = 136;
    my $adFileTime = 64;
    my $adDBFileTime = 137;
    my $adPropVariant = 138;
    my $adVarNumeric = 139;	
    	
    #######################################	
    ## FieldAttributeEnum Values
    #######################################
    my $adFldMayDefer = '0x00000002';
    my $adFldUpdatable = '0x00000004';
    my $adFldUnknownUpdatable = '0x00000008';
    my $adFldFixed = '0x00000010';
    my $adFldIsNullable = '0x00000020';
    my $adFldMayBeNull = '0x00000040';
    my $adFldLong = '0x00000080';
    my $adFldRowID = '0x00000100';
    my $adFldRowVersion = '0x00000200';
    my $adFldCacheDeferred = '0x00001000';
    my $adFldKeyColumn = '0x00008000';
    	
    #######################################	
    ## EditModeEnum Values
    #######################################
    my $adEditNone = '0x0000';
    my $adEditInProgress = '0x0001';
    my $adEditAdd = '0x0002';
    my $adEditDelete = '0x0004';
    	
    #######################################	
    ## RecordStatusEnum Values
    #######################################
    my $adRecOK = '0x0000000';
    my $adRecNew = '0x0000001';
    my $adRecModified = '0x0000002';
    my $adRecDeleted = '0x0000004';
    my $adRecUnmodified = '0x0000008';
    my $adRecInvalid = '0x0000010';
    my $adRecMultipleChanges = '0x0000040';
    my $adRecPendingChanges = '0x0000080';
    my $adRecCanceled = '0x0000100';
    my $adRecCantRelease = '0x0000400';
    my $adRecConcurrencyViolation = '0x0000800';
    my $adRecIntegrityViolation = '0x0001000';
    my $adRecMaxChangesExceeded = '0x0002000';
    my $adRecObjectOpen = '0x0004000';
    my $adRecOutOfMemory = '0x0008000';
    my $adRecPermissionDenied = '0x0010000';
    my $adRecSchemaViolation = '0x0020000';
    my $adRecDBDeleted = '0x0040000';
    	
    #######################################	
    ## GetRowsOptionEnum Values
    #######################################
    my $adGetRowsRest = -1;
    	
    #######################################	
    ## PositionEnum Values
    #######################################
    my $adPosUnknown = -1;
    my $adPosBOF = -2;
    my $adPosEOF = -3;
    	
    #######################################	
    ## enum Values
    #######################################
    my $adBookmarkCurrent = 0;
    my $adBookmarkFirst = 1;
    my $adBookmarkLast = 2;
    	
    #######################################	
    ## MarshalOptionsEnum Values
    #######################################
    my $adMarshalAll = 0;
    my $adMarshalModifiedOnly = 1;
    	
    #######################################	
    ## AffectEnum Values 
    #######################################
    my $adAffectCurrent = 1;
    my $adAffectGroup = 2;
    my $adAffectAll = 3;
    my $adAffectAllChapters = 4;
    	
    
    #######################################	
    ## ResyncEnum Values
    #######################################
    my $adResyncUnderlyingValues = 1;
    my $adResyncAllValues = 2;
    	
    #######################################	
    ## CompareEnum Values
    #######################################
    my $adCompareLessThan = 0;
    my $adCompareEqual = 1;
    my $adCompareGreaterThan = 2;
    my $adCompareNotEqual = 3;
    my $adCompareNotComparable = 4;
    	
    #######################################	
    ## FilterGroupEnum Values
    #######################################
    my $adFilterNone = 0;
    my $adFilterPendingRecords = 1;
    my $adFilterAffectedRecords = 2;
    my $adFilterFetchedRecords = 3;
    my $adFilterPredicate = 4;
    my $adFilterConflictingRecords = 5;
    	
    #######################################	
    ## SearchDirectionEnum Values
    #######################################
    my $adSearchForward = 1;
    my $adSearchBackward = -1;
    	
    #######################################	
    ## PersistFormatEnum Values
    #######################################
    my $adPersistADTG = 0;
    my $adPersistXML = 1;
    	
    #######################################	
    ## StringFormatEnum Values
    #######################################
    my $adStringXML = 0;
    my $adStringHTML = 1;
    my $adClipString = 2;
    	
    #######################################	
    ## ConnectPromptEnum Values
    #######################################
    my $adPromptAlways = 1;
    my $adPromptComplete = 2;
    my $adPromptCompleteRequired = 3;
    my $adPromptNever = 4;
    	
    #######################################	
    ## ConnectModeEnum Values
    #######################################
    my $adModeUnknown = 0;
    my $adModeRead = 1;
    my $adModeWrite = 2;
    my $adModeReadWrite = 3;
    my $adModeShareDenyRead = 4;
    my $adModeShareDenyWrite = 8;
    my $adModeShareExclusive = '0xc';
    my $adModeShareDenyNone = '0x10';
    	
    #######################################	
    ## IsolationLevelEnum Values
    #######################################
    my $adXactUnspecified = '0xffffffff';
    my $adXactChaos = '0x00000010';
    my $adXactReadUncommitted = '0x00000100';
    my $adXactBrowse = '0x00000100';
    my $adXactCursorStability = '0x00001000';
    my $adXactReadCommitted = '0x00001000';
    my $adXactRepeatableRead = '0x00010000';
    my $adXactSerializable = '0x00100000';
    my $adXactIsolated = '0x00100000';
    	
    #######################################	
    ## XactAttributeEnum Values
    #######################################
    my $adXactCommitRetaining = '0x00020000';
    my $adXactAbortRetaining = '0x00040000';
    	
    #######################################	
    ## PropertyAttributesEnum Values
    #######################################
    my $adPropNotSupported = '0x0000';
    my $adPropRequired = '0x0001';
    my $adPropOptional = '0x0002';
    my $adPropRead = '0x0200';
    my $adPropWrite = '0x0400';
    	
    #######################################	
    ## ErrorValueEnum Values
    #######################################
    my $adErrInvalidArgument = '0xbb9';
    my $adErrNoCurrentRecord = '0xbcd';
    my $adErrIllegalOperation = '0xc93';
    my $adErrInTransaction = '0xcae';
    my $adErrFeatureNotAvailable = '0xcb3';
    my $adErrItemNotFound = '0xcc1';
    my $adErrObjectInCollection = '0xd27';
    my $adErrObjectNotSet = '0xd5c';
    my $adErrDataConversion = '0xd5d';
    my $adErrObjectClosed = '0xe78';
    my $adErrObjectOpen = '0xe79';
    my $adErrProviderNotFound = '0xe7a';
    my $adErrBoundToCommand = '0xe7b';
    my $adErrInvalidParamInfo = '0xe7c';
    my $adErrInvalidConnection = '0xe7d';
    my $adErrNotReentrant = '0xe7e';
    my $adErrStillExecuting = '0xe7f';
    my $adErrOperationCancelled = '0xe80';
    my $adErrStillConnecting = '0xe81';
    my $adErrNotExecuting = '0xe83';
    my $adErrUnsafeOperation = '0xe84';
    	
    #######################################	
    ## ParameterAttributesEnum Values
    #######################################
    my $adParamSigned = '0x0010';
    my $adParamNullable = '0x0040';
    my $adParamLong = '0x0080';
    	
    #######################################	
    ## ParameterDirectionEnum Values
    #######################################
    my $adParamUnknown = '0x0000';
    my $adParamInput = '0x0001';
    my $adParamOutput = '0x0002';
    my $adParamInputOutput = '0x0003';
    my $adParamReturnValue = '0x0004';
    	
    #######################################	
    ## CommandTypeEnum Values
    #######################################
    my $adCmdUnknown = '0x0008';
    my $adCmdText = '0x0001';
    my $adCmdTable = '0x0002';
    my $adCmdStoredProc = '0x0004';
    my $adCmdFile = '0x0100';
    my $adCmdTableDirect = '0x0200';
    	
    #######################################	
    ## EventStatusEnum Values
    #######################################
    my $adStatusOK = '0x0000001';
    my $adStatusErrorsOccurred = '0x0000002';
    my $adStatusCantDeny = '0x0000003';
    my $adStatusCancel = '0x0000004';
    my $adStatusUnwantedEvent = '0x0000005';
    	
    #######################################	
    ## EventReasonEnum Values
    #######################################
    my $adRsnAddNew = 1;
    my $adRsnDelete = 2;
    my $adRsnUpdate = 3;
    my $adRsnUndoUpdate = 4;
    my $adRsnUndoAddNew = 5;
    my $adRsnUndoDelete = 6;
    my $adRsnRequery = 7;
    my $adRsnResynch = 8;
    my $adRsnClose = 9;
    my $adRsnMove = 10;
    my $adRsnFirstChange = 11;
    my $adRsnMoveFirst = 12;
    my $adRsnMoveNext = 13;
    my $adRsnMovePrevious = 14;
    my $adRsnMoveLast = 15;
    	
    #######################################	
    ## SchemaEnum Values
    #######################################
    my $adSchemaProviderSpecific = -1;
    my $adSchemaAsserts = 0;
    my $adSchemaCatalogs = 1;
    my $adSchemaCharacterSets = 2;
    my $adSchemaCollations = 3;
    my $adSchemaColumns = 4;
    my $adSchemaCheckmyraints = 5;
    my $adSchemamyraintColumnUsage = 6;
    my $adSchemamyraintTableUsage = 7;
    my $adSchemaKeyColumnUsage = 8;
    my $adSchemaReferentialmyraints = 9;
    my $adSchemaTablemyraints = 10;
    my $adSchemaColumnsDomainUsage = 11;
    my $adSchemaIndexes = 12;
    my $adSchemaColumnPrivileges = 13;
    my $adSchemaTablePrivileges = 14;
    my $adSchemaUsagePrivileges = 15;
    my $adSchemaProcedures = 16;
    my $adSchemaSchemata = 17;
    my $adSchemaSQLLanguages = 18;
    my $adSchemaStatistics = 19;
    my $adSchemaTables = 20;
    my $adSchemaTranslations = 21;
    my $adSchemaProviderTypes = 22;
    my $adSchemaViews = 23;
    my $adSchemaViewColumnUsage = 24;
    my $adSchemaViewTableUsage = 25;
    my $adSchemaProcedureParameters = 26;
    my $adSchemaForeignKeys = 27;
    my $adSchemaPrimaryKeys = 28;
    my $adSchemaProcedureColumns = 29;
    my $adSchemaDBInfoKeywords = 30;
    my $adSchemaDBInfoLiterals = 31;
    my $adSchemaCubes = 32;
    my $adSchemaDimensions = 33;
    my $adSchemaHierarchies = 34;
    my $adSchemaLevels = 35;
    my $adSchemaMeasures = 36;
    my $adSchemaProperties = 37;
    my $adSchemaMembers = 38;
    	
    #######################################	
    ## SeekEnum Values
    #######################################
    my $adSeekFirstEQ = '0x1';
    my $adSeekLastEQ = '0x2';
    my $adSeekAfterEQ = '0x4';
    my $adSeekAfter = '0x8';
    my $adSeekBeforeEQ = '0x10';
    my $adSeekBefore = '0x20';
    	
    #######################################	
    ## ADCPROP_UPDATECRITERIA_ENUM Values
    #######################################
    my $adCriteriaKey = 0;
    my $adCriteriaAllCols = 1;
    my $adCriteriaUpdCols = 2;
    my $adCriteriaTimeStamp = 3;
    	
    #######################################	
    ## ADCPROP_ASYNCTHREADPRIORITY_ENUM Values
    #######################################
    my $adPriorityLowest = 1;
    my $adPriorityBelowNormal = 2;
    my $adPriorityNormal = 3;
    my $adPriorityAboveNormal = 4;
    my $adPriorityHighest = 5;
    	
    #######################################	
    ## CEResyncEnum Values
    #######################################
    my $adResyncNone = 0;
    my $adResyncAutoIncrement = 1;
    my $adResyncConflicts = 2;
    my $adResyncUpdates = 4;
    my $adResyncInserts = 8;
    my $adResyncAll = 15;
    	
    #######################################	
    ## ADCPROP_AUTORECALC_ENUM Values
    #######################################
    my $adRecalcUpFront = 0;
    my $adRecalcAlways = 1;
    %>
    	
    	
    	
    	
    	
    	

