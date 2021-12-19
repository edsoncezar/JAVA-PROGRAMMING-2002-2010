#include <StubPreamble.h>
#include <javaString.h>

#include "CubbyHole.h"

long CubbyHole_get(struct HCubbyHole *this) {
    while (unhand(this)->available == 0) {
	monitorWait(obj_monitor(this));
    }
    unhand(this)->available = 0;
    monitorNotify(obj_monitor(this));
    return unhand(this)->seq;
}

void CubbyHole_put(struct HCubbyHole *this, long value) {
    while (unhand(this)->available > 0) {
        monitorWait(obj_monitor(this));
    }
    unhand(this)->seq = value;
    unhand(this)->available = 1;
    monitorNotify(obj_monitor(this));
}
