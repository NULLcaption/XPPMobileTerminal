package com.xpp.moblie.print;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.xpp.moblie.query.OrderPrintFormat;

public class XPPBluetoothPrinter {
	private static final String PRINTER_IDENTITY = "C90-BT";
	private static final UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805f9b34fb");

	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	private BluetoothDevice device = null;
	private boolean isConnected = false;
	private static BluetoothSocket bluetoothSocket = null;

	public static final byte[] PREFIX_DATA = { 27, 64, 28, 38, 27, 51, 0, 10,
			27, 42, 33, -106, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,
			-1, 0, 7, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15,
			-1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15,
			-1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15,
			-2, 0, 15, -4, 0, 15, -8, 0, 15, -16, 0, 15, -32, 0, 15, -29, 0,
			15, -57, 0, 15, -57, 0, 15, -49, 0, 15, -33, 0, 15, -97, 0, 15,
			-97, 0, 15, -65, 0, 15, -65, 0, 15, -65, 0, 15, -1, 0, 15, -1, 0,
			15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0,
			15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0,
			15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0,
			15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0, 15, -1, 0,
			15, -1, 0, 7, -1, 0, 3, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0,
			0, 7, 0, 0, 15, 0, 0, 7, 0, 0, 7, 0, 0, 7, 0, 0, 3, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 7, 0, 0, 7, 0, 0, 7, 0, 0, 7, 0,
			0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -64, 0, 4, 32,
			0, 11, -48, 0, 4, 16, 0, 0, -128, 0, 11, 16, 0, 0, 32, 0, 3, -64,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10,
			27, 42, 33, -106, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -8, 31, -1, -64, 3, -1, 0, 0,
			-1, 0, 0, 63, 0, 0, 31, 0, 0, 15, 63, -128, 15, -1, -32, 7, -1, -8,
			3, -1, -4, 3, -1, -2, 3, -1, -2, 1, -1, -1, 1, -1, -1, 1, -1, -1,
			-127, -1, -1, -127, -1, -1, -127, -1, -1, -127, -1, -1, -127, -1,
			-1, -125, -1, -1, -125, -1, -1, 3, -1, -1, 7, -1, -2, 7, -1, -4,
			15, -1, -4, 31, -1, -16, 63, -1, 1, -1, -16, 7, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 31, -128, 60, 127, -16, 126, -1, -8, 126, -1, -4, 126, -1, -4,
			126, -1, -4, 124, -1, -8, 124, -1, -16, 124, -8, 1, 124, -16, 97,
			-4, -32, -15, -1, -26, -5, -1, -26, -5, -1, -18, -9, -1, -18, -9,
			-1, -18, -2, -1, -18, -2, 61, -18, -1, 60, -18, -1, 60, -18, -1,
			60, -18, -1, 56, -18, -1, 48, -18, -1, 48, -18, -1, 48, -18, -1,
			48, -18, -13, 56, -18, -13, 120, -50, -15, -8, -50, -15, -4, -50,
			-13, -2, -50, -9, -2, -50, -9, -2, -50, -9, -2, -50, -9, -2, -50,
			-13, 124, -60, -15, 120, -32, -16, 56, -32, 64, 56, -32, 0, 121,
			-16, 0, 121, -4, 0, 121, -1, -64, 121, -1, -4, 120, -1, -1, 120,
			-1, -1, 48, -1, -1, 0, 127, -1, 0, 3, -16, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, -128, 0, 31, -16, 1, 63,
			-8, 1, 127, -4, 3, 127, -2, 1, 127, -2, 3, 127, -1, 3, 127, -1, 3,
			127, -1, 7, 63, -1, 7, 63, -1, 7, 1, -1, 7, 0, -1, 7, 0, -1, 7, 0,
			-1, 15, 0, 127, 15, 0, 126, 15, 0, 126, 15, 0, 126, 15, 0, -2, 15,
			25, -4, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -4, 127, 1, -4, 127, 0,
			-4, 126, 0, -8, 126, 0, -8, 126, 0, -16, 124, 0, -16, 124, 0, -32,
			124, 0, -64, -4, 1, -64, -4, 1, -64, -4, 3, -64, -8, 7, -64, 120,
			7, -64, 120, 7, -64, 48, 7, -64, 0, 3, -128, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 10, 27, 42, 33, -106, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -2, 15, -1, -8, 7, -1, -8, 7, -1, -32, 7, -1, -64,
			7, -1, -64, 7, -1, -64, 12, 15, -64, 12, 63, -64, 30, 15, -64, 31,
			-113, -64, 31, 31, -64, 60, 15, -64, 60, 7, -128, 63, -1, -128, 62,
			15, -128, 124, 7, -128, 124, 7, 0, 125, -73, 0, 125, -73, 0, 127,
			-1, 0, 124, 15, 0, 124, 63, 0, 126, 15, 0, -1, 7, 0, -4, 15, 0, -1,
			-1, 0, -4, 15, 0, -4, 7, 0, -3, -121, 0, -4, 15, 0, -1, -1, -128,
			124, 15, -128, 124, 63, -128, 126, 15, -128, 124, 7, -64, 124, 15,
			-64, 60, 7, -32, 30, 15, -32, 28, 15, -16, 28, 15, -16, 12, 7, -8,
			15, -25, -4, 12, 15, -2, 7, -1, -2, 7, -1, -1, 7, -1, -1, 3, -1,
			-1, -61, -1, -1, -61, -1, -1, -31, -1, -1, -15, -1, -1, -8, -1, -1,
			-4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 51, -128, 0, 115, -128, 0, 119, -128, 0, -9, -128, -32, -17,
			1, -16, -17, 115, -16, -34, 115, -16, -36, -9, -32, -68, -25, -64,
			-71, -17, -64, 57, -49, -128, 115, -33, 0, 103, -97, 0, -17, 62, 0,
			-49, 60, 0, -34, 124, 0, -4, -8, 0, -8, -8, 0, -15, -16, 0, -15,
			-16, 0, -5, -32, 0, -1, -32, 0, -1, -32, 0, -1, -64, 0, -1, -128,
			0, -1, -128, 0, -1, 0, 0, -2, 0, 0, -16, 0, 0, -16, 0, 0, -16, 0,
			0, -4, 0, 0, -2, 0, 0, -1, -128, 0, 127, -32, 0, 63, -16, 0, 15,
			-16, 0, 7, -5, 0, 1, -1, -128, 0, 7, -64, -128, 3, -64, -128, 3,
			-64, -128, 1, -64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -4, 0, 0, -4, 0, 0, -4, 0, 0, -4,
			0, 0, -4, 0, 0, -4, 0, 0, -8, 0, 0, -8, 0, 0, -8, 0, 0, -16, 0, 0,
			-16, 0, 0, -16, 0, 0, -16, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0,
			-64, 0, 0, -64, 0, 0, -64, 0, 0, -64, 0, 0, -128, 0, 0, -128, 0,
			48, -64, 0, -8, -1, -63, -4, -1, -1, -2, -1, -1, -2, -1, -1, -2,
			-1, -61, -4, -64, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 70, 0, 0, -116, 0, 1, 63, -32, 2, 96, 0, 1, -48, 32, 0, 87, -64,
			3, -44, 0, 0, 84, 0, 0, 87, -64, 1, -48, -96, 0, 49, 64, 1, -8,
			-64, 2, -121, -128, 0, -4, -64, 0, -128, 32, 0, 100, 0, 1, -28, 0,
			3, -65, -32, 0, -92, 64, 0, -92, -128, 1, 4, 0, 1, 63, -32, 0, -87,
			0, 0, 41, 0, 3, -23, 0, 0, 41, 32, 0, -87, 32, 1, -87, 32, 1, 63,
			-32, 0, 0, 0, 0, 32, 0, 0, 64, 0, 1, -3, -32, 3, 85, 64, 1, 85, 64,
			1, 85, 64, 1, 85, 64, 3, 85, 64, 3, -3, 64, 1, 85, 64, 1, 85, 64,
			1, 85, 64, 1, 85, -32, 1, 4, 0, 0, 0, 0, 0, 0, 32, 1, 34, 96, 1,
			34, -64, 1, 102, -128, 1, 103, 0, 1, -86, 32, 1, -86, 32, 1, 51,
			-32, 1, 50, 0, 1, 50, 0, 2, 43, 0, 2, 102, -128, 2, 70, -64, 2, 2,
			64, 0, 1, 32, 0, 50, 64, 0, 94, -128, 1, -108, -128, 2, 53, 32, 0,
			37, 32, 0, -120, 64, 0, -104, -128, 0, -81, 0, 0, -24, 0, 2, -56,
			0, 1, -113, -32, 0, -88, 32, 0, -104, 32, 0, -120, 32, 0, -123,
			-64, 0, 0, 0, 0, 31, -128, 0, 96, 96, 0, -128, 16, 1, 0, 8, 1,
			-128, 0, 1, -8, 0, 0, 127, 0, 0, 3, -64, 0, 1, -64, 0, 31, 0, 0,
			96, 0, 1, -32, 0, 0, 124, 0, 0, 15, -128, 0, 1, -64, 0, 3, -64, 0,
			127, 0, 1, -8, 0, 1, -128, 0, 0, 0, 0, 0, 112, -128, 0, -112, 64,
			1, 8, 64, 1, 8, 64, 1, 4, 64, 1, 4, 64, 0, -125, -128, 0, 0, 0, 0,
			0, 0, 1, -1, -64, 1, 4, 0, 1, 4, 0, 1, 4, 0, 1, 4, 0, 0, -120, 0,
			0, -16, 0, 0, 0, 0, 1, 0, 8, 0, -128, 16, 0, 96, 96, 0, 31, -128,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 10, 27, 42, 33, -106, 1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, -128, 0, 0, -64, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0, 0, -32, 0,
			0, -32, 0, 0, -64, 0, 0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 50, 10 };

	public static final byte[] APPENDIX_DATA = { 27, 100, 3 };

	public static final byte[] APPENDIX_DEV_COMPANY = new String(
			"\n\n\n\n- - - - - - - - - - - - - - - -").getBytes();

	public static final byte[] SEPERATOR_LINE = new String(
			"\n- - - - - - - - - - - - - - - -").getBytes();
	private static volatile XPPBluetoothPrinter bluetoothPrinter;

	public XPPBluetoothPrinter() {
		initialize();
	}

	public static XPPBluetoothPrinter getInstance() {
		if (bluetoothPrinter == null) {
			synchronized (XPPBluetoothPrinter.class) {
				if (bluetoothPrinter == null) {
					bluetoothPrinter = new XPPBluetoothPrinter();
				}
			}
		}

		return bluetoothPrinter;
	}

	public void print(byte[] printData) throws IOException {
		System.out.println("isConnected:==========" + isConnected);
		OutputStream outputStream = bluetoothSocket.getOutputStream();
		try {
			if (outputStream != null) {
				outputStream.write(printData);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		} finally {
//			if (outputStream != null) {
//				try {
//					outputStream.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	/**
	 * 根据设定的格式打印
	 */
	public boolean printOrder(Order order, List<OrderPrintFormat> formats)
			throws IOException {
		// 使用自定义打印格式
		if (isConnected) {// 是否连接打印机
			if (null != formats && formats.size() > 0) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				for (OrderPrintFormat format : formats) {
					if ("T".equals(format.getType())) {// 主题
						if ("title".equals(format.getPropertiesCode())) {// 主题-门店名称
							outputStream.write(new byte[] { 27, 97, 1 });
							outputStream.write((order.getCustName() + "\n")
									.getBytes("GBK"));
							outputStream.write(new byte[] { 27, 97, 0 });
						}
					}
					if ("H".equals(format.getType())) {// 抬头
						if ("header_custName"
								.equals(format.getPropertiesCode())) {// 门店名称
							String khname = "\n" + format.getPropertiesTxt()
									+ ":" + order.getCustName();
							outputStream.write(khname.getBytes("GBK"));
						}
						if ("header_orderDate".equals(format
								.getPropertiesCode())) {// 下单日期
							String dateStr = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").format(order
									.getOrderDate());
							String dateStr1 = "\n" + format.getPropertiesTxt()
									+ "：" + dateStr;
							outputStream.write((dateStr1.toString())
									.getBytes("GBK"));
						}
						if ("header_orderId".equals(format.getPropertiesCode())) {// 订单编号
							String skuNo = "\n" + format.getPropertiesTxt()
									+ ":" + order.getOrderId();
							outputStream.write(skuNo.getBytes("GBK"));
						}
						if ("header_orderUser".equals(format
								.getPropertiesCode())) {// 下单人
							String user = "\n" + format.getPropertiesTxt()
									+ "：" + order.getOrderUser();
							outputStream.write(user.getBytes("GBK"));
						}
						if ("header_orderUnit".equals(format
								.getPropertiesCode())) {// 开票单位
							String operator = "\n" + format.getPropertiesTxt()
									+ "：" + order.getOperator();
							outputStream.write(operator.getBytes("GBK"));
						}
						if ("header_orderPhone".equals(format
								.getPropertiesCode())) {// 下单人公务号
							String tabHead = "\n" + format.getPropertiesTxt()
									+ "：" + order.getCustPhone();
							outputStream.write(tabHead.getBytes("GBK"));
						}
						if ("header_orderStatus".equals(format
								.getPropertiesCode())) {// 订单状态
							String status1 = "\n" + format.getPropertiesTxt()
									+ "：" + order.getOrderStatus();
							outputStream.write(status1.getBytes("GBK"));
						}
						if ("header_orderPayment".equals(format
								.getPropertiesCode())) {// 款项状态
							String status2 = "\n" + format.getPropertiesTxt()
									+ "：" + order.getOrderFundStatus();
							outputStream.write(status2.getBytes("GBK"));
						}
						if ("header_orderMemo".equals(format
								.getPropertiesCode())) {// 订单备注
							String remark = "\n" + format.getPropertiesTxt()
									+ "：" + order.getRemark();
							outputStream.write(remark.getBytes("GBK"));
						}
					}
				}// 抬头结束
				Map<String, List<OrderDetail>> map = new HashMap<String, List<OrderDetail>>();
				for (int i = 0; i < order.getDetails().size(); i++) {
					OrderDetail detail = order.getDetails().get(i);
					if (i == 0) {
						outputStream.write(SEPERATOR_LINE);// 画虚线的
					}
					String skuName = detail.getSku();
					double qty = detail.getQuantity();
					double price = detail.getPrice();
					String column1 = "";
					String column2 = "";
					String qty_value = "";
					String price_value = "";
					String total = "";
					for (OrderPrintFormat format : formats) {
						if ("L".equals(format.getType())) {
							if ("list_skuName".equals(format
									.getPropertiesCode())) {// sku名称
								column1 = "\n" + detail.getOrderTypeTxt()
										+ skuName;
							}
							if ("list_quantity".equals(format
									.getPropertiesCode())) {// 数量
								qty_value = qty + detail.getUnitDesc();
							}
							if ("list_skuPrice".equals(format
									.getPropertiesCode()) && 0 != price) {// 单价
								price_value = price + detail.getPriceUnitDesc();
							}
							if ("list_countPrice".equals(format
									.getPropertiesCode())) {// sku金额合计
								total = "\n小计："
										+ String.valueOf(detail.getTotalPrice())
										+ "元";
							}
						}
					}
					if (!"".equals(column1)) {
						outputStream.write(column1.getBytes("GBK"));
					}
					if (!"".equals(qty_value) && !"".equals(price_value)) {
						column2 = "\n" + qty_value + " × " + price_value + "/"
								+ detail.getUnitDesc();
					} else if ("".equals(qty_value) && !"".equals(price_value)) {
						column2 = "\n" + price_value;
					} else if (!"".equals(qty_value) && "".equals(price_value)) {
						column2 = "\n" + qty_value;
					}
					outputStream.write(column2.getBytes("GBK"));
					if (null != detail.getChildSkus()) {// 本品搭赠品
						if (detail.getChildSkus().size() > 0) {
							if (null == map.get(detail.getSkuId())) {
								map.put(detail.getSkuId(),
										detail.getChildSkus());
								for (OrderDetail d : detail.getChildSkus()) {
									outputStream.write("\n".getBytes("GBK"));
									String txt = "赠:" + d.getSku();
									outputStream.write(txt.getBytes("GBK"));
									String txt_num = "\n" + d.getQuantity()
											+ " " + d.getUnitDesc();
									outputStream.write(txt_num.getBytes("GBK"));
								}
							}
						}
					}
					if (!"".equals(total)) {
						outputStream.write(total.getBytes("GBK"));
					}
					outputStream.write(SEPERATOR_LINE);
				}
				for (OrderPrintFormat format : formats) {
					// 合计
					if ("B".equals(format.getType())) {
						if ("bottom_countQuantity".equals(format
								.getPropertiesCode())) {// 订单sku数量合计
//							String end = format.getPropertiesTxt() + "："
//									+ "(本)" + order.getTotalQuantity() + "箱"
//									+ "  (赠)" + order.getTotalZQuantity()
//									+ "箱\n";
							String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//							System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));
							System.out.println("".equals(strb)+":"+(stra.lastIndexOf('\n')==(stra.length()-1)));
							if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
								end= format.getPropertiesTxt() + "：\n" + order.getOrdertypea()+ order.getOrdertypeb();
								
							}
							else {
								end= format.getPropertiesTxt() + "：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
							}
							outputStream.write(end.getBytes("GBK"));
						}
						if ("bottom_countPrice".equals(format
								.getPropertiesCode())) {// 订单金额合计
							String totalPrice = format.getPropertiesTxt() + "："
									+ String.valueOf(order.getTotalPrice())
									+ "元\n";
							outputStream.write(totalPrice.getBytes("GBK"));
						}
					}
					if ("F".equals(format.getType())) {
						if ("foot".equals(format.getPropertiesCode())) {// 页脚自定义备注
							if (null != format.getMemo()
									&& !"".equals(format.getMemo())) {
								String memo = "\n"+format.getMemo() + "\n";
								outputStream.write(memo.getBytes("GBK"));
							}
						}
					}
				}
				outputStream.write(SEPERATOR_LINE);
				outputStream.write("\n".getBytes("GBK"));
				print(outputStream.toByteArray());
			} else {
				// 如没有自定义打印格式则使用默认格式
				printOrder(order);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 默认打印格式
	 */
	public void printOrder(Order order) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(new byte[] { 27, 97, 1 });
		outputStream.write((order.getCustName() + "\n").getBytes("GBK"));
		outputStream.write(new byte[] { 27, 97, 0 });

		String khname = "客户名称:" + order.getCustName();
		outputStream.write(khname.getBytes("GBK"));
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(order.getOrderDate()) + "\n";
		String dateStr1 = "\n下单日期：" + dateStr;
		outputStream.write((dateStr1.toString()).getBytes("GBK"));

		String skuNo = "单号：" + order.getOrderId();
		outputStream.write(skuNo.getBytes("GBK"));
		String user = "\n下单人：" + order.getOrderUser();
		outputStream.write(user.getBytes("GBK"));
		String operator = "\n开票单位：" + order.getOperator() + "\n";
		outputStream.write(operator.getBytes("GBK"));
		String tabHead = "下单人公务号码：" + order.getCustPhone() + "\n";
		outputStream.write(tabHead.getBytes("GBK"));
		String status1 = "订单状态:" + order.getOrderStatus();
		outputStream.write(status1.getBytes("GBK"));
		String status2 = "\n订单款项:" + order.getOrderFundStatus();
		outputStream.write(status2.getBytes("GBK"));
		String remark = "\n订单描述:" + order.getRemark();
		outputStream.write(remark.getBytes("GBK"));
		outputStream.write(SEPERATOR_LINE);// 画虚线的

		for (OrderDetail detail : order.getDetails()) {
			outputStream.write("\n".getBytes("GBK"));
			String skuName = detail.getSku();
			double qty = detail.getQuantity();
			double price = detail.getPrice();
			String column1 = detail.getOrderTypeTxt() + skuName + "\n";
			if (0 < price) {
				String column2 = MessageFormat.format(
						"{0}" + detail.getUnitDesc() + " × {1}"
								+ detail.getPriceUnitDesc() + "/"
								+ detail.getUnitDesc(),
						new Object[] { Double.valueOf(qty),
								Double.valueOf(price) });
				outputStream.write(MessageFormat.format("{0}{1}",
						new Object[] { column1, column2 }).getBytes("GBK"));
			} else {
				outputStream.write(column1.getBytes("GBK"));
				String column2 = qty + detail.getUnitDesc();
				outputStream.write(column2.getBytes("GBK"));
			}
			outputStream.write("\n".getBytes("GBK"));
			if (null != detail.getChildSkus()) {// 本品搭赠品
				if (detail.getChildSkus().size() > 0) {
					for (OrderDetail d : detail.getChildSkus()) {
						String txt = "赠:" + d.getSku();
						outputStream.write(txt.getBytes("GBK"));
						outputStream.write("\n".getBytes("GBK"));
						String txt_num = d.getQuantity() + " "
								+ d.getUnitDesc();
						outputStream.write(txt_num.getBytes("GBK"));
						outputStream.write("\n".getBytes("GBK"));
					}
				}
			}
			String total = "小计：" + String.valueOf(detail.getTotalPrice()) + "元";
			outputStream.write(total.getBytes("GBK"));
			outputStream.write(SEPERATOR_LINE);
		}

//		String end = "合计数量：" + "(本)" + order.getTotalQuantity() + "箱" + "  (赠)"
//				+ order.getTotalZQuantity() + "箱\n";

String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));
System.out.println("".equals(strb)+":"+(stra.lastIndexOf('\n')==(stra.length()-1)));
if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
	end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb();
	
}
else {
	end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
}
		outputStream.write(end.getBytes("GBK"));
		String totalPrice = "合计金额：" + String.valueOf(order.getTotalPrice())
				+ "元\n";
		outputStream.write(totalPrice.getBytes("GBK"));
		outputStream.write(SEPERATOR_LINE);
		outputStream.write("\n".getBytes("GBK"));
		print(outputStream.toByteArray());
	}

	protected void finalize() throws Throwable {
		disconnect();
		super.finalize();
	}

	private void disconnect() {
		try {
			bluetoothSocket.getOutputStream().close();
		} catch (IOException e) {
			throw new IllegalAccessError(e.getMessage());
		} finally {
			try {
				bluetoothSocket.close();
			} catch (IOException e) {
				throw new IllegalAccessError(e.getMessage());
			}
		}
	}

	private void initialize() {
		bindPrinter();
		connectPrinter();
	}

	private void connectPrinter() {
		bluetoothAdapter.cancelDiscovery();
		if (!this.isConnected)
			try {
				bluetoothSocket = this.device
						.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				this.isConnected = true;
			} catch (Exception e) {
				cancelSocket();
			}

	}

	private void bindPrinter() {
		// 强制开启蓝牙
		if (!bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.enable();
		}
		Set<BluetoothDevice> devices = this.bluetoothAdapter.getBondedDevices();

		for (BluetoothDevice device : devices) {
			if (device.getName().equals(PRINTER_IDENTITY)) {
				setDevice(device);
				return;
			}
		}

		throw new IllegalAccessError("PRINTER NOT FOUND!");
	}

	private static void cancelSocket() {
		try {
			bluetoothSocket.close();
		} catch (IOException localIOException) {
		}
	}
	/**
	 * 应用软件退出
	 */
	public void exitBluePrint(){
		bluetoothPrinter=null;
	}
	public BluetoothDevice getDevice() {
		return this.device;
	}

	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}
}