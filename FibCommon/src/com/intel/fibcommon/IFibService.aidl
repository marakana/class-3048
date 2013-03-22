package com.intel.fibcommon;

import com.intel.fibcommon.FibRequest;
import com.intel.fibcommon.IFibListener;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	long fib( in FibRequest request);
	oneway void asyncFib( in FibRequest request, in IFibListener listener);
}