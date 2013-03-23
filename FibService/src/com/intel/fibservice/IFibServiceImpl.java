package com.intel.fibservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;

import com.intel.fibcommon.FibRequest;
import com.intel.fibcommon.IFibListener;
import com.intel.fibcommon.IFibService;

public class IFibServiceImpl extends IFibService.Stub {
	private Context context;

	public IFibServiceImpl(Context context) {
		super();
		this.context = context;
	}

	@Override
	public long fibJ(long n) throws RemoteException {
		// Check for com.intel.fib.permission.FIB_SERVICE_SLOW
		if (context
				.checkCallingOrSelfPermission("com.intel.fib.permission.FIB_SERVICE_SLOW") != PackageManager.PERMISSION_GRANTED) {
			throw new SecurityException("Unauthorized for com.intel.fib.permission.FIB_SERVICE_SLOW");
		}
		return FibLib.fibJ(n);
	}

	@Override
	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	@Override
	public long fib(FibRequest request) throws RemoteException {
		switch (request.getAlgorithm()) {
		case FibRequest.ALGORITHM_JAVA:
			return FibLib.fibJ(request.getN());
		case FibRequest.ALGORITHM_NATIVE:
			return FibLib.fibN(request.getN());
		default:
			throw new IllegalArgumentException("Unknown algorithm");
		}
	}

	@Override
	public void asyncFib(FibRequest request, IFibListener listener)
			throws RemoteException {
		// Long running task
		long start = System.currentTimeMillis();
		long result = fib(request);
		long time = System.currentTimeMillis() - start;

		listener.onResponse(request.getN(), result, time);
	}
}
