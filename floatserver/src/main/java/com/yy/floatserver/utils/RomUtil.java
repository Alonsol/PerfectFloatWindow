package com.yy.floatserver.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by yy on 2020/6/13.
 * function: Rom类型
 */
public class RomUtil {
	private static final String TAG = "RomUtil";

	public static final String ROM_MIUI = "MIUI";
	public static final String ROM_EMUI = "EMUI";
	public static final String ROM_FLYME = "FLYME";
	public static final String ROM_OPPO = "OPPO";
	public static final String ROM_SMARTISAN = "SMARTISAN";

	public static final String ROM_VIVO = "VIVO";
	public static final String ROM_QIKU = "QIKU";

	public static final String ROM_LENOVO = "LENOVO";
	public static final String ROM_SAMSUNG = "SAMSUNG";
	public static final String ROM_EUI = "EUI";
	public static final String ROM_YULONG = "YULONG";
	public static final String ROM_AMIGO = "AmigoOS";
	private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
	private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
	private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
	private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
	private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";

	// 乐视 : eui
	private static final String KEY_EUI_VERSION = "ro.letv.release.version"; // "5.9.023S"
	private static final String KEY_EUI_VERSION_DATE = "ro.letv.release.version_date"; // "5.9.023S_03111"
	private static final String KEY_EUI_NAME = "ro.product.letv_name"; // "乐1s"
	private static final String KEY_EUI_MODEL = "ro.product.letv_model"; // "Letv X500"
	// 金立 : amigo
	private static final String KEY_AMIGO_ROM_VERSION = "ro.gn.gnromvernumber"; // "GIONEE ROM5.0.16"
	private static final String KEY_AMIGO_SYSTEM_UI_SUPPORT = "ro.gn.amigo.systemui.support"; // "yes"


	// 酷派 : yulong
	private static final String KEY_YULONG_VERSION_RELEASE = "ro.yulong.version.release"; // "5.1.046.P1.150921.8676_M01"
	private static final String KEY_YULONG_VERSION_TAG = "ro.yulong.version.tag"; // "LC"

	public static boolean isEmui() {
		return check(ROM_EMUI);
	}

	public static boolean isMiui() {
		return check(ROM_MIUI);
	}

	public static boolean isVivo() {
		return check(ROM_VIVO);
	}

	public static boolean isOppo() {
		return check(ROM_OPPO);
	}

	public static boolean isFlyme() {
		return check(ROM_FLYME);
	}

	public static boolean isQiku() {
		return check(ROM_QIKU) || check("360");
	}

	public static boolean isSmartisan() {
		return check(ROM_SMARTISAN);
	}


	private static String sName;

	public static String getName() {
		if (sName == null) {
			check("");
		}
		return sName;
	}

	private static String sVersion;

	public static String getVersion() {
		if (sVersion == null) {
			check("");
		}
		return sVersion;
	}


	public static boolean check(String rom) {
		if (sName != null) {
			return sName.equals(rom);
		}

		if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_MIUI))) {
			sName = ROM_MIUI;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_EMUI))) {
			sName = ROM_EMUI;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_OPPO))) {
			sName = ROM_OPPO;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_VIVO))) {
			sName = ROM_VIVO;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_SMARTISAN))) {
			sName = ROM_SMARTISAN;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_AMIGO_ROM_VERSION)) || !TextUtils.isEmpty(sVersion = getProp(KEY_AMIGO_SYSTEM_UI_SUPPORT))) {
			// amigo
			sName = ROM_AMIGO;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_EUI_VERSION)) || !TextUtils.isEmpty(sVersion = getProp(KEY_EUI_NAME)) || !TextUtils.isEmpty(sVersion = getProp(KEY_EUI_MODEL))) {
			sName = ROM_EUI;
		} else if (!TextUtils.isEmpty(sVersion = getProp(KEY_YULONG_VERSION_RELEASE)) || !TextUtils.isEmpty(sVersion = getProp(KEY_YULONG_VERSION_TAG))) {
			sName = ROM_YULONG;
		} else {
			sVersion = Build.DISPLAY;
			if (sVersion.toUpperCase().contains(ROM_FLYME)) {
				sName = ROM_FLYME;
			} else {
				sVersion = Build.UNKNOWN;
				sName = Build.MANUFACTURER.toUpperCase();
			}
		}
		return sName.equals(rom);
	}

	public static String getProp(String name) {
		String line = null;
		BufferedReader input = null;
		try {
			Process p = Runtime.getRuntime().exec("getprop " + name);
			input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
			line = input.readLine();
			input.close();
		} catch (IOException ex) {
			Log.e(TAG, "Unable to read prop " + name, ex);
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return line;
	}
}