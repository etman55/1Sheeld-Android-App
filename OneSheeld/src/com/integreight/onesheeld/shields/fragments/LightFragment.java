package com.integreight.onesheeld.shields.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.integreight.onesheeld.Log;
import com.integreight.onesheeld.R;
import com.integreight.onesheeld.shields.controller.LightShield;
import com.integreight.onesheeld.shields.controller.LightShield.LightEventHandler;
import com.integreight.onesheeld.shields.controller.ProximityShield;
import com.integreight.onesheeld.utils.ShieldFragmentParent;

public class LightFragment extends ShieldFragmentParent<LightFragment> {
	TextView light_float, light_byte;
	TextView devicehasSensor;
	Button stoplistening_bt, startlistening_bt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.light_shield_fragment_layout,
				container, false);
		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onStart() {

		getApplication().getRunningShields().get(getControllerTag())
				.setHasForgroundView(true);
		super.onStart();

	}

	@Override
	public void onStop() {
		getApplication().getRunningShields().get(getControllerTag())
				.setHasForgroundView(true);

		super.onStop();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("Light Sheeld::OnActivityCreated()", "");

		light_float = (TextView) getView().findViewById(
				R.id.light_float_txt);
		light_byte = (TextView) getView().findViewById(
				R.id.light_byte_txt);

		devicehasSensor = (TextView) getView().findViewById(
				R.id.device_not_has_sensor_text);
		stoplistening_bt = (Button) getView().findViewById(
				R.id.stop_listener_bt);
		startlistening_bt = (Button) getView().findViewById(
				R.id.start_listener_bt);

		startlistening_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LightShield) getApplication().getRunningShields().get(
						getControllerTag())).registerSensorListener();
				light_float.setVisibility(View.VISIBLE);
				light_byte.setVisibility(View.VISIBLE);

			}
		});

		stoplistening_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LightShield) getApplication().getRunningShields().get(
						getControllerTag())).unegisterSensorListener();
				light_float.setVisibility(View.INVISIBLE);
				light_byte.setVisibility(View.INVISIBLE);

			}
		});

	}

	private LightEventHandler lightEventHandler = new LightEventHandler() {

		@Override
		public void onSensorValueChangedFloat(String value) {
			// TODO Auto-generated method stub

			if (canChangeUI()) {
				light_float.setVisibility(View.VISIBLE);
				light_float.setText("Light in float = " + value);
				Toast.makeText(getActivity(), "Value changed " + value,
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onSensorValueChangedByte(String value) {
			// TODO Auto-generated method stub
			light_byte.setVisibility(View.VISIBLE);
			light_byte.setText("Light in Byte = " + value);

		}

		@Override
		public void isDeviceHasSensor(Boolean hasSensor) {
			// TODO Auto-generated method stub
			if (canChangeUI()) {
				if (!hasSensor) {
					devicehasSensor.setText("Your Device not have The Sensor");
					Toast.makeText(getActivity(),
							"Device dosen't have This Sensor !",
							Toast.LENGTH_SHORT).show();
				} else {
					light_float.setVisibility(View.VISIBLE);
					light_byte.setVisibility(View.VISIBLE);
					stoplistening_bt.setVisibility(View.VISIBLE);
				}
			}
		}
	};

	private void initializeFirmata() {
		if (getApplication().getRunningShields().get(getControllerTag()) == null) {
			getApplication().getRunningShields().put(getControllerTag(),
					new LightShield(getActivity(), getControllerTag()));

		}

	}

	public void doOnServiceConnected() {
		initializeFirmata();
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((LightShield) getApplication().getRunningShields().get(
				getControllerTag())).setLightEventHandler(lightEventHandler);

	}
}
