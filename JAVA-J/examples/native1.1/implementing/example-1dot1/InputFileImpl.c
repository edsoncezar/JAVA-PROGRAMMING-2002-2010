#include <StubPreamble.h>
#include <javaString.h>

#include "InputFile.h"
#include "OutputFile.h"

#include <sys/types.h>
#include <sys/param.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>

#define        LOCAL_PATH_SEPARATOR        '/'

static void
convertPath(char *path)
{
    while (*path != '\0') {
        if ((*path == InputFile_separatorChar) ||
            (*path == OutputFile_separatorChar)) {
            *path = LOCAL_PATH_SEPARATOR;
        }
        path++;
    }
    return;
}

long InputFile_open(struct HInputFile *this)
{
    int                fd;
    char        buf[MAXPATHLEN];

    javaString2CString(unhand(this)->path, buf, sizeof(buf));
    convertPath(buf);

    fd = open(buf, O_RDONLY);
    if (fd < 0)
        return(FALSE);

    unhand(this)->fd = fd;
    return(TRUE);
}

void InputFile_close(struct HInputFile *this)
{
     close(unhand(this)->fd);
     unhand(this)->fd = -1;
     return;
}

long InputFile_read(struct HInputFile *this, 
                    HArrayOfByte *b, 
                    long len)
{
    char *data        = unhand(b)->body;
    int  count        = obj_length(b);
    int  actual;

    if (count < len) {
        actual = count;
    }
    else {
        actual = len;
    }
    actual = read(unhand(this)->fd, data, actual);
    if (actual == 0)
        return(-1);
    return(actual);
}
