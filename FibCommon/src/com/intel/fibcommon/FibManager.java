package com.intel.fibcommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class FibManager {
	private static final Intent FIB_SERVICE = new Intent("com.intel.fibcommon.IFibService");
	private Context context;
	private IFibService fibService;

	public FibManager(Context context) {
		super();
		this.context = context;
		context.bindService(FIB_SERVICE, FIB_CONNECTION, Context.BIND_AUTO_CREATE);
	}
	
	public void finalize() {
		context.unbindService(FIB_CONNECTION);
	}
	
	private final ServiceConnection FIB_CONNECTION = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			fibService = IFibService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			fibService = null;
		}
	};
	
	// --- Proxy calls ---
	
	public long fibJ(long n) {
		if(fibService==null) return -1;
		try {
			return fibService.fibJ(n);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public long fibN(long n) {
		if(fibService==null) return -1;
		try {
			return fibService.fibN(n);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
