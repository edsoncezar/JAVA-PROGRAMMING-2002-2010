#ifndef JAVADEF_H
#define JAVADEF_H

#define CLASSFILE_MAGIC 0xCAFEBABE

#define ACC_PUBLIC        0x0001
#define ACC_PRIVATE       0x0002
#define ACC_PROTECTED     0x0004
#define ACC_STATIC        0x0008
#define ACC_FINAL         0x0010
#define ACC_SUPER         0x0020
#define ACC_SYNCHRONIZED  0x0020  
#define ACC_VOLATILE      0x0040
#define ACC_TRANSIENT     0x0080
#define ACC_NATIVE        0x0100 
#define ACC_INTERFACE     0x0200  
#define ACC_ABSTRACT      0x0400  
#define ACC_STRICT        0x0800 

#define CONSTANT_Class  7  
#define CONSTANT_Fieldref  9  
#define CONSTANT_Methodref  10  
#define CONSTANT_InterfaceMethodref  11  
#define CONSTANT_String  8  
#define CONSTANT_Integer  3  
#define CONSTANT_Float  4  
#define CONSTANT_Long  5  
#define CONSTANT_Double  6  
#define CONSTANT_NameAndType  12  
#define CONSTANT_Utf8  1  

enum ACCESSFLAG { PUBLIC, PROTECTED, PRIVATE};

#endif
