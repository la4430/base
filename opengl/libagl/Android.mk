LOCAL_PATH:= $(call my-dir)

#
# Build the software OpenGL ES library
#

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	egl.cpp                     \
	state.cpp		            \
	texture.cpp		            \
    Tokenizer.cpp               \
    TokenManager.cpp            \
    TextureObjectManager.cpp    \
    BufferObjectManager.cpp     \
	array.cpp.arm		        \
	fp.cpp.arm		            \
	light.cpp.arm		        \
	matrix.cpp.arm		        \
	mipmap.cpp.arm		        \
	primitives.cpp.arm	        \
	vertex.cpp.arm

FLTO_FLAG=$(call cc-option,"-flto", )
LOCAL_CFLAGS += -DLOG_TAG=\"libagl\"
LOCAL_CFLAGS += -DGL_GLEXT_PROTOTYPES -DEGL_EGLEXT_PROTOTYPES
LOCAL_CFLAGS += -fvisibility=hidden
LOCAL_CFLAGS += -ffast-math $(FLTO_FLAG)
ifneq ($(findstring tune=cortex-a8,$(TARGET_GLOBAL_CFLAGS)),)
	# Workaround for cortex-a8 specific linaro-gcc bug 879725
	# FIXME remove once the bug is fixed
	LOCAL_CFLAGS += -fno-modulo-sched
endif

LOCAL_SHARED_LIBRARIES := libcutils libhardware libutils libpixelflinger libETC1
LOCAL_LDLIBS := $(FLTO_FLAG) -lpthread -ldl

ifeq ($(TARGET_ARCH),arm)
	LOCAL_SRC_FILES += fixed_asm.S iterators.S
	LOCAL_CFLAGS += -fstrict-aliasing
endif

ifeq ($(ARCH_ARM_HAVE_TLS_REGISTER),true)
    LOCAL_CFLAGS += -DHAVE_ARM_TLS_REGISTER
endif

# we need to access the private Bionic header <bionic_tls.h>
# on ARM platforms, we need to mirror the ARCH_ARM_HAVE_TLS_REGISTER
# behavior from the bionic Android.mk file
ifeq ($(TARGET_ARCH)-$(ARCH_ARM_HAVE_TLS_REGISTER),arm-true)
    LOCAL_CFLAGS += -DHAVE_ARM_TLS_REGISTER
endif
LOCAL_C_INCLUDES += bionic/libc/private

LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/egl
LOCAL_MODULE:= libGLES_android

include $(BUILD_SHARED_LIBRARY)
