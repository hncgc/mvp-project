/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pccb.newapp.global;

import android.os.Environment;

import java.io.File;

/**
 * 全局常量
 * @author cgc
 * @created 2017-11-02
 */
public class Constant {
	
	/*
	 * 系统常量标示
	 */
	public static final String APP_TYPE = "0";
	
	/**
	 * 文件保存路径
	 */
	// 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "PccbDir"
            + File.separator + "pccb_img" + File.separator;

	/**
	 * 数据缓存路径
	 */
	public static String ACACHE_DIR_NAME = "PccbCache";

	/**
	 * 手机息屏
	 */
	public static final String IS_LOCK_VIEW_STATES = "is_lock_view_states";

	/**
	 * UI设计的基准宽度.
	 */
	public static int uiWidth = 720;

	/**
	 * UI设计的基准高度.
	 */
	public static int uiHeight = 1080;

}
