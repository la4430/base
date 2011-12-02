LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_ASSET_FILES := $(call find-subdir-assets assets)

LOCAL_JAVA_LIBRARIES := android.test.runner

LOCAL_STATIC_JAVA_LIBRARIES := easymocklib

LOCAL_PACKAGE_NAME := mediaframeworktest
LOCAL_CERTIFICATE := shared

include $(BUILD_PACKAGE)
