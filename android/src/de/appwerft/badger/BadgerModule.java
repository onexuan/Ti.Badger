/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.badger;

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.titanium.TiApplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

@Kroll.module(name = "Badger", id = "de.appwerft.badger")
public class BadgerModule extends KrollModule {

	public BadgerModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
	}

	@Kroll.method
	public void setBadge(@Kroll.argument(optional = true) final Integer a) {
		if (a == null || a == 0) {
			removeBadge();
			return;
		}
		if (TiApplication.isUIThread()) {
			handleSetBadge(a);
		} else {
			TiMessenger.postOnMain(new Runnable() {
				@Override
				public void run() {
					handleSetBadge(a);
				}
			});
		}
	}

	private boolean handleSetBadge(int a) {
		try {
			ShortcutBadger.applyCountOrThrow(TiApplication.getInstance()
					.getApplicationContext(), a);
			return true;
		} catch (ShortcutBadgeException e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean handleRemoveBadge() {
		try {
			ShortcutBadger.removeCountOrThrow(TiApplication.getInstance()
					.getApplicationContext());
			return true;
		} catch (ShortcutBadgeException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Kroll.method
	public void removeBadge() {
		if (TiApplication.isUIThread()) {
			handleRemoveBadge();
		} else {
			TiMessenger.postOnMain(new Runnable() {
				@Override
				public void run() {
					handleRemoveBadge();
				}
			});
		}
	}

	@Kroll.method
	public KrollDict getLauncher() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		ResolveInfo resolveInfo = TiApplication.getAppRootOrCurrentActivity()
				.getPackageManager()
				.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
		KrollDict kd = new KrollDict();

		kd.put("packageName", resolveInfo.activityInfo.packageName);
		kd.put("processName", resolveInfo.activityInfo.processName);
		kd.put("name", resolveInfo.activityInfo.applicationInfo.name);
		return kd;
	}
}
