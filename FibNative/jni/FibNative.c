/*
 * FibNative.c
 *
 *  Created on: Mar 22, 2013
 *      Author: marko
 */
#include "com_intel_fibnative_FibLib.h"

/* Pure C Implementation */
long fib(long n) {
	if(n==0) return 0;
	if(n==1) return 1;
	return fib(n-1)+fib(n-2);
}

/* JNI C */
JNIEXPORT jlong JNICALL Java_com_intel_fibnative_FibLib_fibN
  (JNIEnv *env, jclass clazz, jlong n) {
	return fib(n);
}




