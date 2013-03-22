package com.intel.fibclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.intel.fibcommon.FibManager;
import com.intel.fibcommon.FibRequest;
import com.intel.fibcommon.IFibListener;

public class MainActivity extends Activity {
	EditText input;
	TextView output;
	FibManager fibManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);

		fibManager = new FibManager(this);
	}
	
	private static final int WHAT = 42;
	private final Handler HANDLER = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(msg.what != WHAT) return;
			output.append(String.format("\n fib(X)=%d (X)", msg.obj));
		}
		
	};

	private final IFibListener LISTENER = new IFibListener.Stub() {

		@Override
		public void onResponse(long n, long result, long time)
				throws RemoteException {
			Log.d("LogClient",
					String.format("\n fib(%d)=%d (%d ms)", n, result, time));
			Message msg = HANDLER.obtainMessage(WHAT);
			msg.obj = result;
			HANDLER.sendMessage(msg);
		}
	};

	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		FibRequest request = new FibRequest(FibRequest.ALGORITHM_JAVA, n);
		fibManager.asyncFib(request, LISTENER);

	}
}
