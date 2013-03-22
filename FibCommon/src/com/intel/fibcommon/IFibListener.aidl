package com.intel.fibcommon;

oneway interface IFibListener {
	void onResponse( long n, long result, long time );
}