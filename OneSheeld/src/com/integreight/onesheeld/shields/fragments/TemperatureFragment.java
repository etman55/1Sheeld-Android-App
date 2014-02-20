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
import com.integreight.onesheeld.shields.controller.PressureShield;
import com.integreight.onesheeld.shields.controller.PressureShield.PressureEventHandler;
import com.integreight.onesheeld.shields.controller.TemperatureShield.TemperatureEventHandler;
import com.integreight.onesheeld.shields.controller.TemperatureShield;
import com.integreight.onesheeld.utils.ShieldFragmentParent;

public class TemperatureFragment extends ShieldFragmentParent<TemperatureFragment> {
	TextView temperature_float, temperature_byte;
	TextView devicehasSensor;
	Button stoplistening_bt, startlistening_bt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.temperature_shield_fragment_layout,
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
		Log.d("Temperature Sheeld::OnActivityCreated()", "");

		temperature_float = (TextView) getView().findViewById(
				R.id.temperature_float_txt);
		temperature_byte = (TextView) getView().findViewById(
				R.id.temperature_byte_txt);

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
				((TemperatureShield) getApplication().getRunningShields().get(
						getControllerTag())).registerSensorListener();
				temperature_float.setVisibility(View.VISIBLE);
				temperature_byte.setVisibility(View.VISIBLE);

			}
		});

		stoplistening_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TemperatureShield) getApplication().getRunningShields().get(
						getControllerTag())).unegisterSensorListener();
				temperature_float.setVisibility(View.INVISIBLE);
				temperature_byte.setVisibility(View.INVISIBLE);

			}
		});

	}

	private TemperatureEventHandler temperatureEventHandler = new TemperatureEventHandler() {

		@Override
		public void onSensorValueChangedFloat(String value) {
			// TODO Auto-generated method stub
			if (canChangeUI()) {
				temperature_float.setVisibility(View.VISIBLE);
				temperature_float.setText("temperature in float = " + value);
			}

		}

		@Override
		public void onSensorValueChangedByte(String value) {
			// TODO Auto-generated method stub
			temperature_byte.setVisibility(View.VISIBLE);
			temperature_byte.setText("temperature in Byte = " + value);

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
					temperature_float.setVisibility(View.VISIBLE);
					temperature_byte.setVisibility(View.VISIBLE);
					stoplistening_bt.setVisibility(View.VISIBLE);
				}
			}
		}
	};

	private void initializeFirmata() {
		if (getApplication().getRunningShields().get(getControllerTag()) == null) {
			getApplication().getRunningShields().put(getControllerTag(),
					new TemperatureShield(getActivity(), getControllerTag()));

		}

	}

	public void doOnServiceConnected() {
		initializeFirmata();
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((TemperatureShield) getApplication().getRunningShields().get(
				getControllerTag()))
				.setTemperatureEventHandler(temperatureEventHandler);

	}
}