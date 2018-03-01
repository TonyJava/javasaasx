package com.choudou5.javasaasx.service.impl.util;

import com.choudou5.base.annotation.ControllerDesc;
import com.choudou5.base.util.CollUtil;
import com.choudou5.base.util.IpUtil;
import com.choudou5.base.util.MapUtil;
import com.choudou5.base.util.StrUtil;
import com.choudou5.javasaasx.common.util.SpringContextHolder;
import com.choudou5.javasaasx.dao.sys.SysOperationLogDao;
import com.choudou5.javasaasx.dao.sys.po.SysOperationLogPo;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Name：日志工具类
 * @Author：xuhaowen
 * @Date：2018-02-28
 * @Site：http://solrhome.com
 * @License：MIT
 * @Copyright：xuhaowende@sina.cn (@Copyright 2018-2020)
 */
public class LogUtil {

    private static SysOperationLogDao logDao = SpringContextHolder.getBean(SysOperationLogDao.class);
    private static Queue<SysOperationLogPo> logQueue = new ArrayBlockingQueue<SysOperationLogPo>(500);
    private static volatile long lastAccessTime = System.currentTimeMillis();

    private static final int BATCH = 10;
    private static final long INTERVAL = 5000L;


    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String desc){
        saveLog(request, null, desc);
    }

    /**
     * 保存日志
     */
    public static void saveHandler(HttpServletRequest request, Object handler){
        saveLog(request, handler, null);
    }

    /**
     * 保存日志
     */
    private static void saveLog(HttpServletRequest request, Object handler, String desc){
        SysOperationLogPo log = new SysOperationLogPo();
        log.setDesc(desc);
        // 获取Controller信息
        if (handler != null){
            if (handler instanceof HandlerMethod){
                Method m = ((HandlerMethod)handler).getMethod();
                ControllerDesc annotation = m.getAnnotation(ControllerDesc.class);
                if(annotation != null){
                    log.setOptType(annotation.optType());
                    log.setDesc(annotation.desc());
                }
            }
        }
        if(StrUtil.isBlank(log.getOptType()))
            log.setOptType("view");
        if(StrUtil.isBlank(log.getDesc()))
            log.setDesc("查看");
        log.setIp(IpUtil.getIpAddr(request));
        log.setUserAgent(request.getHeader("user-agent"));
        log.setRequestUri(request.getRequestURI());
        log.setParams(MapUtil.join(request.getParameterMap(), ";", "="));
        log.setMethod(request.getMethod());
        try {
            log.preInsert();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //日志 保存到队列（缓和--延迟插入）
        logQueue.add(log);

        long now = System.currentTimeMillis();
        if((now-lastAccessTime) > INTERVAL){//间隔5秒 批量保存一次
            lastAccessTime = now;
//				异步保存日志
            TaskUtil.EXECUTOR.execute(new SaveLogThread());
        }

    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread implements Runnable{

        @Override
        public void run() {
            List<SysOperationLogPo> entitys = new ArrayList<SysOperationLogPo>();
            for (int i = 0; i < BATCH; i++) {
                SysOperationLogPo log = logQueue.poll();
                if(log == null)
                    break;
                entitys.add(log);
            }
            //批量保存日志
            if(CollUtil.isNotEmpty(entitys)){
                logDao.batchInsert(entitys);
            }
        }
    }

}