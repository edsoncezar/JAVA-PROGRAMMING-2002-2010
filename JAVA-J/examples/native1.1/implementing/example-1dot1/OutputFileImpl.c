#include <StubPreamble.h>
#include <javaString.h>

#include "OutputFile.h"
#include "InputFile.h"

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

long OutputFile_open(struct HOutputFile *this)
{
    int                fd;
    char        buf[MAXPATHLEN];

    javaString2CString(unhand(this)->path, buf, sizeof(buf));
    convertPath(buf);
    fd = open(buf, O_RDWR|O_CREAT|O_TRUNC, 0644);
    if (fd < 0)
        return(FALSE);
    unhand(this)->fd = fd;
    return(TRUE);
}

void OutputFile_close(struct HOutputFile *this)
{
    close(unhand(this)->fd);
    unhand(this)->fd = -1;
    return;
}

long OutputFile_write(struct HOutputFile *this, 
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
    actual = write(unhand(this)->fd, data, actual);
    return(actual);
}
