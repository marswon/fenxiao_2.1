/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p>
 * 项目名称：fyd-cms
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/

package com.kedang.fenxiao.common.transcoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * @author zhangqi@fuyidai.me
 * @version V1.0.0
 * @date 2015/12/29
 */
public abstract class SerializeTranscoder {
    protected static Logger logger = LoggerFactory.getLogger(SerializeTranscoder.class);

    public abstract byte[] serialize(Object value);

    public abstract Object deserialize(byte[] in);

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.info("Unable to close " + closeable, e);
            }
        }
    }
}
